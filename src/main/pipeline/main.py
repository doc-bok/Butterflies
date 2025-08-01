# main.py
import logging

from .config import Config
from .data_generation import DataGenerator
from .localisation import LocalisationManager
from .advancements import AdvancementGenerator
from .biome_modifiers import BiomeModifierManager
from .code_generation import CodeGenerator


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

    # Step 1: Gather species lists
    butterflies = data_gen.generate_butterfly_list(config.BUTTERFLIES_FOLDER)
    male_butterflies = data_gen.generate_butterfly_list(config.MALE_BUTTERFLIES_FOLDER)
    moths = data_gen.generate_butterfly_list(config.MOTHS_FOLDER)
    male_moths = data_gen.generate_butterfly_list(config.MALE_MOTHS_FOLDER)
    special = data_gen.generate_butterfly_list(config.SPECIAL_FOLDER)

    all_species = butterflies + male_butterflies + moths + male_moths + special
    all_butterflies = butterflies + male_butterflies
    all_moths = moths + male_moths

    logger.info(f"Total species count: {len(all_species)}")

    # Step 2: Generate JSON data files for all groups
    for group_name, group_list in [
        ("butterflies", butterflies),
        ("male butterflies", male_butterflies),
        ("moths", moths),
        ("male moths", male_moths),
        ("special", special),
    ]:
        logger.info(f"Generating data files for {group_name} ({len(group_list)})")
        data_gen.generate_data_files(group_list)

    # Optional Step: Uncomment if texture generation is needed
    # logger.info("Generating placeholder textures...")
    # data_gen.generate_textures(butterflies, "clipper")

    # Step 3: Generate frog food file
    data_gen.generate_frog_food(all_species)

    # Step 4: Generate localisation strings
    localisation.generate_localisation_strings(all_butterflies + special, all_moths)

    # Step 5: Generate advancements JSON files for various groups
    adv_gen.generate_advancements(butterflies, config.BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(all_butterflies, config.MALE_BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(moths, config.MOTH_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(all_moths, config.MALE_MOTH_ACHIEVEMENT_TEMPLATES)
    adv_gen.generate_advancements(butterflies + moths, config.BOTH_ACHIEVEMENT_TEMPLATES)

    # Step 6: Generate Java code with species and traits
    code_gen.generate_code(all_species)

    # Step 7: Reset and generate biome modifier files
    biome_mod_mgr.reset_biome_modifiers()

    biome_groups = [
        (butterflies, config.BUTTERFLIES_FOLDER, False),
        (male_butterflies, config.MALE_BUTTERFLIES_FOLDER, True),
        (moths, config.MOTHS_FOLDER, False),
        (male_moths, config.MALE_MOTHS_FOLDER, True),
        (special, config.SPECIAL_FOLDER, False),
    ]

    for species_group, folder, is_male in biome_groups:
        logger.info(f"Generating biome modifiers for folder '{folder}' with is_male={is_male}")
        biome_mod_mgr.generate_biome_modifiers(species_group, folder, is_male)

    logger.info("Butterflies/moths data pipeline completed successfully.")


if __name__ == "__main__":
    main()
