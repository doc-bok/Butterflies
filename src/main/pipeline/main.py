import json
from typing import Optional

from .image_generation import ImageGenerator
from .config import Config
from .data_generation import DataGenerator
from .localisation import LocalisationManager
from .advancements import AdvancementGenerator
from .biome_modifiers import BiomeModifierManager
from .code_generation import CodeGenerator

def load_species_data(config, logger, species: str) -> Optional[dict]:
    """
    Attempts to load the JSON data for this species across known folders.
    Returns dict if found, None otherwise.
    """
    for folder in config.FOLDERS:
        json_path = config.BUTTERFLY_DATA / folder / f"{species}.json"
        if json_path.exists():
            try:
                return json.loads(json_path.read_text(encoding="utf8"))
            except (OSError, json.JSONDecodeError) as e:
                logger.error(f"Failed to read {json_path}: {e}")
                return None
    return None


def main():
    # Setup config and logger
    config = Config()
    logger = config.logger

    logger.info("Starting butterflies/moths data pipeline...")

    # Instantiate managers
    data_gen = DataGenerator(config)
    localisation = LocalisationManager(config)
    adv_gen = AdvancementGenerator(config)
    biome_mod_mgr = BiomeModifierManager(config)
    code_gen = CodeGenerator(config)
    image_gen = ImageGenerator(config)

    # Gather species lists
    butterflies = data_gen.generate_butterfly_list(config.BUTTERFLIES_FOLDER)
    variant_butterflies = data_gen.generate_butterfly_list(config.VARIANT_BUTTERFLIES_FOLDER)
    moths = data_gen.generate_butterfly_list(config.MOTHS_FOLDER)
    variant_moths = data_gen.generate_butterfly_list(config.VARIANT_MOTHS_FOLDER)
    special = data_gen.generate_butterfly_list(config.SPECIAL_FOLDER)

    all_species = butterflies + variant_butterflies + moths + variant_moths + special
    all_butterflies = butterflies + variant_butterflies
    all_moths = moths + variant_moths

    # Preload butterfly data.
    species_data = {s: load_species_data(config, logger, s) or {} for s in all_species}

    logger.info(f"Total species count: {len(all_species)}")

    data_gen.update_data_files( "butterflies", butterflies)
    data_gen.update_data_files( "butterflies/variant", variant_butterflies)
    data_gen.update_data_files( "moths", moths)
    data_gen.update_data_files( "moths/variant", variant_moths)
    data_gen.update_data_files( "special", special)

    # Generate localisation strings
    localisation.generate_localisation_strings(all_butterflies + special, all_moths)

    # Generate advancements JSON files for various groups
    adv_gen.generate_advancements(butterflies, config.BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(all_butterflies, config.VARIANT_BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(moths, config.MOTH_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(all_moths, config.VARIANT_MOTH_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(butterflies + moths, config.BOTH_ACHIEVEMENT_TEMPLATES)

    # Generate Java code with species and traits
    code_gen.generate_code(all_species, species_data)

    # Reset and generate biome modifier files
    biome_mod_mgr.reset_biome_modifiers()

    biome_groups = [
        (butterflies, config.BUTTERFLIES_FOLDER, False),
        (variant_butterflies, config.VARIANT_BUTTERFLIES_FOLDER, True),
        (moths, config.MOTHS_FOLDER, False),
        (variant_moths, config.VARIANT_MOTHS_FOLDER, True),
        (special, config.SPECIAL_FOLDER, False),
    ]

    for species_group, folder, is_variant in biome_groups:
        logger.info(f"Generating biome modifiers for folder '{folder}' with is_variant={is_variant}")
        biome_mod_mgr.generate_biome_modifiers(species_group, folder, is_variant)

    # Generate images
    image_gen.generate_textures()

    logger.info("Butterflies/moths data pipeline completed successfully.")


if __name__ == "__main__":
    main()
