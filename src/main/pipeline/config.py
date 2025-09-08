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
        self.TEMPLATES_FOLDER = Path("pipeline/templates")
        self.RESOURCES_FOLDER = Path("resources")
        self.DATA_FOLDER = self.RESOURCES_FOLDER / "data"
        self.MOD_DATA_FOLDER = self.DATA_FOLDER / "butterflies"

        self.CODE_GENERATION = Path("java/com/bokmcdok/butterflies/world/ButterflyInfo.java")
        self.LOCALISATION = self.RESOURCES_FOLDER / "assets/butterflies/lang/en_us.json"
        self.FROG_FOOD = self.DATA_FOLDER / "minecraft/tags/entity_types/frog_food.json"

        self.ACHIEVEMENTS = self.MOD_DATA_FOLDER / "advancements/butterfly/"
        self.BIOME_MODIFIERS = self.MOD_DATA_FOLDER / "forge/biome_modifier/"
        self.BUTTERFLY_DATA = self.MOD_DATA_FOLDER / "butterfly_data/"

        self.BUTTERFLY_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/butterfly/"
        self.VARIANT_BUTTERFLY_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/butterfly_variant/"
        self.MOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/moth/"
        self.VARIANT_MOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/moth_variant/"
        self.BOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/both/"
        self.BIOME_MODIFIER_TEMPLATES = self.TEMPLATES_FOLDER / "biome_modifiers/"

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
