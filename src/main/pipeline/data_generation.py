# data_generation.py
import json
import shutil
from pathlib import Path
from typing import List
from .config import Config  # Make sure your config class is accessible
import logging


class DataGenerator:
    def __init__(self, config: Config):
        self.config = config
        self.butterfly_index = self.config.BUTTERFLY_INDEX
        self.logger = self.config.logger

    def generate_butterfly_list(self, folder: str) -> List[str]:
        target_path = self.config.BUTTERFLY_DATA / folder
        self.logger.info(f"Generating species list for folder [{target_path}]")
        result = [f.stem for f in target_path.glob("*.json")]
        self.logger.info(f"Species found: {result!r}")
        return result

    def generate_data_files(self, entries: List[str]) -> None:
        self.logger.info("Generating data files...")
        base_entry = entries[0]

        files: List[Path] = []
        cwd = Path.cwd()
        for path in cwd.rglob("*.json"):
            if base_entry in path.name and "loot_table" not in str(path.parent):
                files.append(path)

        for entry in entries:
            for src_file in files:
                dst_file = src_file.with_name(src_file.name.replace(base_entry, entry))
                if entry != base_entry:
                    if not dst_file.is_file():
                        self.logger.debug(f"Copying and modifying file {src_file} -> {dst_file}")
                        shutil.copy(src_file, dst_file)
                        content = dst_file.read_text(encoding="utf8").replace(base_entry, entry)
                        dst_file.write_text(content, encoding="utf8")

                json_data = json.loads(dst_file.read_text(encoding="utf8"))

                if "index" in json_data:
                    json_data["index"] = self.butterfly_index
                    self.butterfly_index += 1

                if "entityId" in json_data:
                    json_data["entityId"] = entry

                dst_file.write_text(
                    json.dumps(json_data, default=lambda o: o.__dict__, sort_keys=True, indent=2),
                    encoding="utf8"
                )

        # Update global index (optional, if Config is to track this)
        self.config.BUTTERFLY_INDEX = self.butterfly_index

    def generate_frog_food(self, species: List[str]) -> None:
        self.logger.info("Generating frog food...")
        values = []
        for butterfly in species:
            for folder in self.config.FOLDERS:
                json_path = self.config.BUTTERFLY_DATA / folder / f"{butterfly}.json"
                if not json_path.exists():
                    continue
                with open(json_path, encoding="utf8") as f:
                    data = json.load(f)
                if "inedible" not in data.get("traits", []):
                    values.append(f"butterflies:{butterfly}")
                break  # Found the species, move to the next

        frog_food = {
            "replace": False,
            "values": values
        }

        with open(self.config.FROG_FOOD, "w", encoding="utf8") as f:
            json.dump(frog_food, f, sort_keys=True, indent=2)
        self.logger.info(f"Written frog food JSON with {len(values)} entries.")

    def generate_textures(self, entries: List[str], base: str) -> None:
        self.logger.info("Generating textures...")
        cwd = Path.cwd()
        files = [f for f in cwd.rglob("*.png") if base in f.name]
        for entry in entries:
            if entry == base:
                continue
            for file in files:
                new_name = file.name.replace(base, entry)
                new_file = file.with_name(new_name)
                if not new_file.exists():
                    shutil.copy(file, new_file)
                    self.logger.debug(f"Copied texture: {file.name} -> {new_file.name}")
