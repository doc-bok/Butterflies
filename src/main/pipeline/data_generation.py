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

    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger
        self.butterfly_index = self.config.BUTTERFLY_INDEX
        self.folders = self.config.FOLDERS

    @property
    def butterfly_data_path(self) -> Path:
        return self.config.BUTTERFLY_DATA


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


    def update_data_files(self, species_type, species_list) -> None:
        """
        Ensures that butterfly data files have the correct indexes and entity
        IDs.
        :param entries: List of species to generate data files for.
        """
        self.logger.info("Updating data files...")

        for species in species_list:
            src_file = self.butterfly_data_path / species_type / (species + ".json")
            try:
                json_data = json.loads(src_file.read_text(encoding="utf8"))
            except (json.JSONDecodeError, OSError) as e:
                self.logger.error(f"Failed to read JSON from {src_file}: {e}")
                continue

            # Update butterfly index and entityId
            if "index" in json_data:
                json_data["index"] = self.butterfly_index
                self.butterfly_index += 1

            if "entityId" in json_data:
                json_data["entityId"] = src_file.stem

            try:
                # Write updated JSON back to file maintaining formatting
                src_file.write_text(
                    json.dumps(json_data, default=lambda o: o.__dict__, sort_keys=True, indent=2),
                    encoding="utf8"
                )
            except OSError as e:
                self.logger.error(f"Failed to write JSON to {src_file}: {e}")

        # Update config index after processing
        self.config.BUTTERFLY_INDEX = self.butterfly_index