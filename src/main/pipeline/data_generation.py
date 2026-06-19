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