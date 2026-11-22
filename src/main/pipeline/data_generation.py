import json
import shutil
from collections.abc import Iterable
from pathlib import Path
from .config import Config
from .io_utils import write_json_atomically


class DataGenerationError(RuntimeError):
    """Raised when butterfly data generation cannot produce valid output."""


class DataGenerator:
    """
    A utility class to generate butterfly data files for the butterflies mod.
    Reads and writes JSON and image files, maintaining indices and entity IDs
    as needed.
    """

    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger
        self.butterfly_index = self.config.BUTTERFLY_INDEX
        self.folders = self.config.FOLDERS

    @property
    def butterfly_data_path(self) -> Path:
        return self.config.BUTTERFLY_DATA


    def generate_butterfly_list(self, folder: str) -> list[str]:
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
        species = [f.stem for f in sorted(target_path.glob("*.json")) if f.is_file()]
        self.logger.debug(f"Species found: {species!r}")
        return species


    def update_data_files(self, species_type: str, species_list: Iterable[str]) -> None:
        """
        Ensures that butterfly data files have the correct indexes and entity
        IDs.
        :param species_type: The subfolder for the species type.
        :param species_list: The list of species to generate.
        """
        self.logger.info("Updating data files...")

        if species_type not in self.config.FOLDERS:
            raise ValueError(
                f"Unknown species type {species_type!r}; "
                f"expected one of {sorted(self.config.FOLDERS)!r}"
            )

        for species in species_list:
            src_file = self.butterfly_data_path / species_type / (species + ".json")
            try:
                original = json.loads(src_file.read_text(encoding="utf8"))
            except (json.JSONDecodeError, OSError, UnicodeDecodeError) as exc:
                raise DataGenerationError(
                    f"Failed to read butterfly data file: {src_file}"
                ) from exc

            updated = {**original}

            changed = False

            # Update butterfly index and entityId if needed.
            if "index" in updated:
                if updated["index"] != self.butterfly_index:
                    updated["index"] = self.butterfly_index
                    changed = True
                self.butterfly_index += 1

            if "entityId" in updated and updated["entityId"] != src_file.stem:
                updated["entityId"] = src_file.stem
                changed = True

            # If data has changed then write a new JSON file.
            if changed:
                try:
                    write_json_atomically(src_file, updated)
                except OSError as exc:
                    raise DataGenerationError(
                        f"Failed to write butterfly data file: {src_file}"
                    ) from exc

        # Update config index after processing.
        self.config.BUTTERFLY_INDEX = self.butterfly_index