# advancements.py
import json
from pathlib import Path
from typing import List, Union
from .config import Config

class AdvancementGenerator:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    def generate_advancements(self, entities: List[str], templates_dir: Union[str, Path]) -> None:
        templates_path = Path(templates_dir)
        self.logger.info(f"Generating advancements using templates from {templates_path}")
        template_files = [f for f in templates_path.iterdir() if f.is_file() and f.suffix == ".json"]
        for template_file in template_files:
            with template_file.open("r", encoding="utf8") as input_file:
                json_data = json.load(input_file)
            for entity in entities:
                json_data["criteria"][entity] = {
                    "trigger": "minecraft:inventory_changed",
                    "conditions": {
                        "items": [
                            {
                                "items": [
                                    f"{json_data['base_item']}{entity}{json_data['item_ext']}"
                                ]
                            }
                        ]
                    }
                }
                if json_data.get("requires_all", False):
                    json_data.setdefault("requirements", []).append([entity])
            if not json_data.get("requires_all", False):
                json_data["requirements"] = [entities]
            for key in ["base_item", "requires_all", "item_ext"]:
                json_data.pop(key, None)
            output_file_path = self.config.ACHIEVEMENTS / template_file.name
            with output_file_path.open("w", encoding="utf8") as output_file:
                json.dump(json_data, output_file, sort_keys=True, indent=2)
            self.logger.info(f"Wrote advancement file: {output_file_path.name}")
