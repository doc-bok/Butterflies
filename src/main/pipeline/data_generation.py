import json
import shutil
from pathlib import Path
from typing import List
from .config import Config  # Make sure your config class is accessible


class DataGenerator:
    """
    A utility class to generate butterfly data files, textures, frog food JSON, and item models
    for the butterflies mod. Reads and writes JSON and image files, maintaining indices and
    replicating templates as needed.
    """
    TRAIT_INEDIBLE = "inedible"
    DATA_LOOT_TABLE = "loot_tables\entities"

    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger
        self.butterfly_index = self.config.BUTTERFLY_INDEX
        self.folders = self.config.FOLDERS

    @property
    def butterfly_data_path(self) -> Path:
        return self.config.BUTTERFLY_DATA

    @property
    def butterfly_items_path(self) -> Path:
        return Path("resources/assets/butterflies/items/")

    @property
    def butterfly_item_models_path(self) -> Path:
        return Path("resources/assets/butterflies/models/item")

    def generate_butterfly_list(self, folder: str) -> List[str]:
        """
        Generates a list of butterfly species found as JSON files within a folder.
        :param folder: The folder to search inside butterfly_data_path.
        :return: List of species names (file stems).
        """
        target_path = self.butterfly_data_path / folder
        self.logger.info(f"Generating species list for folder [{target_path}]")
        if not target_path.exists():
            self.logger.warning(f"Folder {target_path} does not exist.")
            return []
        species = [f.stem for f in target_path.glob("*.json") if f.is_file()]
        self.logger.debug(f"Species found: {species!r}")
        return species

    def generate_data_files(self, entries: List[str]) -> None:
        """
        Generates missing data files for each butterfly species based on a base entry.
        Updates indices and entity IDs within JSON files.
        :param entries: List of species to generate data files for.
        """
        if not entries:
            self.logger.warning("No entries provided to generate_data_files.")
            return

        self.logger.info("Generating data files...")
        base_entry = entries[0]
        cwd = Path.cwd()
        src_files = [
            p for p in cwd.rglob("*.json")
            if base_entry in p.name and self.DATA_LOOT_TABLE not in str(p.parent)
        ]

        for entry in entries:
            for src_file in src_files:
                dst_file = src_file.with_name(src_file.name.replace(base_entry, entry))

                try:
                    if entry != base_entry:
                        if not dst_file.exists():
                            self.logger.debug(f"Copying file {src_file} to {dst_file}")
                            shutil.copy(src_file, dst_file)

                        # Read, replace base_entry with entry inside file content string
                        json_str = dst_file.read_text(encoding="utf8").replace(base_entry, entry)
                        dst_file.write_text(json_str, encoding="utf8")

                        json_data = json.loads(json_str)
                    else:
                        json_data = json.loads(src_file.read_text(encoding="utf8"))
                except (json.JSONDecodeError, OSError) as e:
                    self.logger.error(f"Failed to read/modify JSON from {src_file if entry == base_entry else dst_file}: {e}")
                    continue

                # Update butterfly index and entityId
                if "index" in json_data:
                    json_data["index"] = self.butterfly_index
                    self.butterfly_index += 1

                if "entityId" in json_data:
                    json_data["entityId"] = entry

                target_file = dst_file if entry != base_entry else src_file
                try:
                    # Write updated JSON back to file maintaining formatting
                    target_file.write_text(
                        json.dumps(json_data, default=lambda o: o.__dict__, sort_keys=True, indent=2),
                        encoding="utf8"
                    )
                except OSError as e:
                    self.logger.error(f"Failed to write JSON to {target_file}: {e}")

        # Update config index after processing
        self.config.BUTTERFLY_INDEX = self.butterfly_index

    def generate_frog_food(self, species: List[str]) -> None:
        """
        Generates a frog food JSON specifying edible butterflies for the mod.
        :param species: List of butterfly species.
        """
        self.logger.info("Generating frog food...")
        values = []

        for butterfly in species:
            found = False
            for folder in self.folders:
                json_path = self.butterfly_data_path / folder / f"{butterfly}.json"
                if not json_path.exists():
                    continue
                try:
                    with json_path.open(encoding="utf8") as f:
                        data = json.load(f)
                except (json.JSONDecodeError, OSError) as e:
                    self.logger.error(f"Error reading JSON for {butterfly} at {json_path}: {e}")
                    break
                if self.TRAIT_INEDIBLE not in data.get("traits", []):
                    values.append(f"butterflies:{butterfly}")
                found = True
                break  # species found, stop searching folders
            if not found:
                self.logger.warning(f"Species file for {butterfly} not found in any folder")

        frog_food = {
            "replace": False,
            "values": values
        }

        try:
            with self.config.FROG_FOOD.open("w", encoding="utf8") as f:
                json.dump(frog_food, f, sort_keys=True, indent=2)
            self.logger.info(f"Wrote frog food JSON with {len(values)} entries.")
        except OSError as e:
            self.logger.error(f"Failed to write frog food JSON to {self.config.FROG_FOOD}: {e}")

    def generate_textures(self, entries: List[str], base: str) -> None:
        """
        Copies base texture files for new species entries, ensuring no overwrite.
        :param entries: List of species entries.
        :param base: The base species texture to copy from.
        """
        self.logger.info("Generating textures...")
        cwd = Path.cwd()
        base_files = [f for f in cwd.rglob("*.png") if base in f.name]

        for entry in entries:
            if entry == base:
                continue
            for file in base_files:
                new_name = file.name.replace(base, entry)
                new_file = file.with_name(new_name)
                if not new_file.exists():
                    try:
                        shutil.copy(file, new_file)
                        self.logger.debug(f"Copied texture: {file.name} -> {new_file.name}")
                    except OSError as e:
                        self.logger.error(f"Failed to copy texture from {file} to {new_file}: {e}")