import json
import shutil
from pathlib import Path
from typing import List
from .config import Config

class BiomeModifierManager:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    def reset_biome_modifiers(self) -> None:
        self.logger.info("Resetting biome modifiers...")
        for src_file in self.config.BIOME_MODIFIER_TEMPLATES.iterdir():
            if src_file.is_file():
                dst_file = self.config.BIOME_MODIFIERS / src_file.name
                if dst_file.exists():
                    try:
                        if src_file.samefile(dst_file):
                            continue
                        dst_file.unlink()
                    except FileNotFoundError:
                        pass
                shutil.copy(src_file, dst_file)
                self.logger.debug(f"Copied biome modifier: {src_file.name}")

    def generate_biome_modifiers(self, species_list: List[str], folder: str, is_variant: bool) -> None:
        self.logger.info(f"Generating biome modifiers for folder {folder} (is_variant={is_variant})")
        for species in species_list:
            self.add_spawns(folder, species, is_variant)

    def add_spawns(self, folder: str, species: str, is_variant: bool) -> None:
        json_path = self.config.BUTTERFLY_DATA / folder / f"{species}.json"
        try:
            with json_path.open(encoding="utf8") as file:
                json_data = json.load(file)
        except FileNotFoundError:
            self.logger.warning(f"Data file not found for species '{species}' in folder '{folder}'")
            return

        rarity = json_data.get("rarity", "uncommon")
        habitats = json_data.get("habitats", [])
        weight, maximum = {
            "common": (100, 4),
            "uncommon": (50, 3),
            "rare": (20, 2)
        }.get(rarity, (50, 3))
        # habitat mapping as before
        habitat_to_tags = {
            "forests":   ["dense", "forest", "lush", "taiga"],
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
        for habitat, tags in habitat_to_tags.items():
            if habitat in habitats:
                for tag in tags:
                    spawn_is_variant = is_variant if habitat != "nether" else True
                    self.add_single_spawn(tag, species, weight, maximum, spawn_is_variant)

    def add_single_spawn(self, tag: str, species: str, weight: int, maximum: int, is_variant: bool) -> None:
        target_file = self.config.BIOME_MODIFIERS / f"{tag}_butterflies.json"
        try:
            with target_file.open(encoding="utf8") as file:
                json_data = json.load(file)
        except FileNotFoundError:
            self.logger.warning(f"Biome modifier file '{target_file}' not found.")
            return

        spawners = json_data.setdefault("spawners", [])
        spawners.append({
            "type": f"butterflies:{species}",
            "weight": weight,
            "minCount": 1,
            "maxCount": maximum
        })
        if not is_variant:
            for suffix in ["_caterpillar", "_chrysalis", "_egg"]:
                spawners.append({
                    "type": f"butterflies:{species}{suffix}",
                    "weight": weight,
                    "minCount": 1,
                    "maxCount": maximum
                })
        with target_file.open("w", encoding="utf8") as file:
            json.dump(json_data, file, sort_keys=True, indent=2)
        self.logger.debug(f"Updated biome modifier {target_file.name} with species {species}")

