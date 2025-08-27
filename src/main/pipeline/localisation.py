# localisation.py
import json
from typing import Dict, List
from .config import Config

class LocalisationManager:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    @staticmethod
    def try_add_localisation_string(json_data: Dict[str, str], key: str, value: str) -> None:
        if key not in json_data:
            json_data[key] = value

    def generate_localisation_strings(self, all_butterflies: List[str], all_moths: List[str]) -> None:
        self.logger.info("Generating localisation strings...")
        with self.config.LOCALISATION.open('r', encoding='utf8') as inp:
            json_data = json.load(inp)

        def format_name(identifier: str) -> str:
            return identifier.replace('_', ' ').replace('-', ' ').title()

        for species in all_butterflies:
            name = format_name(species)
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}", f"{name} Butterfly")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_caterpillar", f"{name} Caterpillar")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_chrysalis", f"{name} Chrysalis")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_egg", f"{name} Butterfly Egg")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}", f"{name} Butterfly")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}_egg", f"{name} Butterfly Egg")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}_caterpillar", f"{name} Caterpillar")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_caterpillar_{species}", f"{name} Caterpillar")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_chrysalis_{species}", f"{name} Chrysalis")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_butterfly_{species}", f"{name} Butterfly")
        for species in all_moths:
            name = format_name(species)
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}", f"{name} Moth")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_caterpillar", "Larva")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_chrysalis", "Cocoon")
            self.try_add_localisation_string(json_data, f"entity.butterflies.{species}_egg", f"{name} Moth Egg")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}", f"{name} Moth")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}_egg", f"{name} Moth Egg")
            self.try_add_localisation_string(json_data, f"item.butterflies.{species}_caterpillar", "Larva")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_caterpillar_{species}", f"{name} Larva")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_chrysalis_{species}", f"{name} Larva")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_butterfly_{species}", f"{name} Moth")
        for species in all_butterflies + all_moths:
            name = format_name(species)
            self.try_add_localisation_string(json_data, f"gui.butterflies.fact.{species}", "")
            self.try_add_localisation_string(json_data, f"item.butterflies.spawn_egg_egg_{species}", f"{name} Egg")

        with self.config.LOCALISATION.open('w', encoding='utf8') as outp:
            json.dump(json_data, outp, ensure_ascii=False, sort_keys=True, indent=2)
        self.logger.info("Localization file updated.")
