import json
import shutil
import tempfile
from pathlib import Path
from typing import List
from .config import Config

class BiomeModifierManager:
    HABITAT_TO_TAGS = {
        "forests":   ["cherry_grove", "dense", "forest", "lush", "taiga"],
        "hills":     ["hill"],
        "ice":       ["snowy", "taiga"],
        "jungles":   ["jungle"],
        "nether":    ["nether"],
        "plains":    ["plains"],
        "plateaus":  ["lush", "plateau"],
        "savannas":  ["savanna"],
        "villages":  [
            "village_desert", "village_plains", "village_savanna",
            "village_snowy", "village_taiga",
        ],
        "wetlands":  ["mushroom", "river", "swamp"],
    }
    RARITY_MAPPING = {
        "common": (100, 4),
        "uncommon": (50, 3),
        "rare": (20, 2)
    }

    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    def reset_biome_modifiers(self) -> None:
        """Reset biome modifier files from templates."""
        self.logger.info("Resetting biome modifiers...")
        for src_file in self.config.BIOME_MODIFIER_TEMPLATES.iterdir():
            if src_file.is_file():
                dst_file = self.config.BIOME_MODIFIERS / src_file.name
                try:
                    if dst_file.exists() and src_file.samefile(dst_file):
                        continue
                    shutil.copy2(src_file, dst_file)
                    self.logger.debug(f"Copied biome modifier: {src_file.name}")
                except Exception as e:
                    self.logger.error(f"Error copying {src_file} to {dst_file}: {e}")

    def generate_biome_modifiers(self, species_list: List[str], folder: str, is_variant: bool) -> None:
        """Generate biome modifiers for a list of species."""
        self.logger.info(f"Generating biome modifiers for folder {folder} (is_variant={is_variant})")
        for species in species_list:
            self.add_spawns(Path(folder), species, is_variant)

    def add_spawns(self, folder: Path, species: str, is_variant: bool) -> None:
        """Add spawning information for a species to appropriate biome modifiers."""
        json_path = self.config.BUTTERFLY_DATA / folder / f"{species}.json"
        try:
            with json_path.open(encoding="utf8") as file:
                json_data = json.load(file)
        except FileNotFoundError:
            self.logger.warning(f"Data file not found for species '{species}' in folder '{folder}'")
            return

        rarity = json_data.get("rarity", "uncommon")
        habitats = json_data.get("habitats", [])
        weight, maximum = self.RARITY_MAPPING.get(rarity, (50, 3))

        for habitat, tags in self.HABITAT_TO_TAGS.items():
            if habitat in habitats:
                for tag in tags:
                    spawn_is_variant = is_variant if habitat != "nether" else True
                    self._add_single_spawn(tag, species, weight, maximum, spawn_is_variant)

    def _add_single_spawn(self, tag: str, species: str, weight: int, maximum: int, is_variant: bool) -> None:
        """Safely add spawn data for a single species/tag combination without duplicates."""
        target_file = self.config.BIOME_MODIFIERS / f"{tag}_butterflies.json"
        try:
            with target_file.open(encoding="utf8") as file:
                json_data = json.load(file)
        except FileNotFoundError:
            self.logger.warning(f"Biome modifier file '{target_file}' not found.")
            return

        spawners = json_data.setdefault("spawners", [])

        # Build entries to add
        entries = [{
            "type": f"butterflies:{species}",
            "weight": weight,
            "minCount": 1,
            "maxCount": maximum
        }]
        if not is_variant:
            entries.extend({
                "type": f"butterflies:{species}{suffix}",
                "weight": weight,
                "minCount": 1,
                "maxCount": maximum
            } for suffix in ["_caterpillar", "_chrysalis", "_egg"])

        # Remove duplicates before writing
        existing_types = set(entry["type"] for entry in spawners)
        spawners.extend(entry for entry in entries if entry["type"] not in existing_types)

        # Robust & atomic file write
        try:
            with tempfile.NamedTemporaryFile("w", encoding="utf8", delete=False, dir=str(target_file.parent)) as tf:
                json.dump(json_data, tf, sort_keys=True, indent=2)
                temp_name = tf.name
            shutil.move(temp_name, target_file)
            self.logger.debug(f"Updated biome modifier {target_file.name} with species {species}")
        except Exception as e:
            self.logger.error(f"Failed to write biome modifier for {tag}: {e}")
