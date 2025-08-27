# config.py
from pathlib import Path
import logging

class Config:
    def __init__(self):
        # === Resource Lists ===
        self.COLORS = [
            'black', 'blue', 'brown', 'cyan', 'gray', 'green',
            'light_blue', 'light_gray', 'lime', 'magenta', 'orange',
            'pink', 'purple', 'red', 'white', 'yellow'
        ]
        self.FLOWERS = [
            'allium', 'azure_bluet', 'blue_orchid', 'cornflower', 'dandelion',
            'lily_of_the_valley', 'orange_tulip', 'oxeye_daisy', 'pink_tulip',
            'poppy', 'red_tulip', 'white_tulip', 'wither_rose'
        ]

        # === File and Directory Paths ===
        self.ACHIEVEMENTS = Path("resources/data/butterflies/advancements/butterfly/")
        self.BIOME_MODIFIERS = Path("resources/data/butterflies/neoforge/biome_modifier/")
        self.BUTTERFLY_DATA = Path("resources/data/butterflies/butterfly_data/")
        self.CODE_GENERATION = Path("java/com/bokmcdok/butterflies/world/ButterflyInfo.java")
        self.FROG_FOOD = Path("resources/data/minecraft/tags/entity_types/frog_food.json")
        self.LOCALISATION = Path("resources/assets/butterflies/lang/en_us.json")
        self.BUTTERFLY_ACHIEVEMENT_TEMPLATES = Path("resources/data/butterflies/templates/advancements/butterfly/")
        self.VARIANT_BUTTERFLY_ACHIEVEMENT_TEMPLATES = Path("resources/data/butterflies/templates/advancements/butterfly_variant/")
        self.MOTH_ACHIEVEMENT_TEMPLATES = Path("resources/data/butterflies/templates/advancements/moth/")
        self.VARIANT_MOTH_ACHIEVEMENT_TEMPLATES = Path("resources/data/butterflies/templates/advancements/moth_variant/")
        self.BOTH_ACHIEVEMENT_TEMPLATES = Path("resources/data/butterflies/templates/advancements/both/")
        self.BIOME_MODIFIER_TEMPLATES = Path("resources/data/butterflies/templates/biome_modifiers/")

        self.BUTTERFLIES_FOLDER = "butterflies"
        self.VARIANT_BUTTERFLIES_FOLDER = "butterflies/variant"
        self.MOTHS_FOLDER = "moths"
        self.VARIANT_MOTHS_FOLDER = "moths/variant"
        self.SPECIAL_FOLDER = "special"

        # Folders for butterfly classes
        self.FOLDERS = [
            self.BUTTERFLIES_FOLDER,
            self.VARIANT_BUTTERFLIES_FOLDER,
            self.MOTHS_FOLDER,
            self.VARIANT_MOTHS_FOLDER,
            self.SPECIAL_FOLDER
        ]

        # === Index State (if not managed elsewhere) ===
        self.BUTTERFLY_INDEX = 0

        # === Logger Setup ===
        logging.basicConfig(
            level=logging.INFO,
            format="%(asctime)s | %(levelname)8s | %(message)s",
            datefmt="%Y-%m-%d %H:%M:%S"
        )
        self.logger = logging.getLogger("butterflies_pipeline")

    # Optional: a convenience method for resetting the index if reused
    def reset_index(self):
        self.BUTTERFLY_INDEX = 0
