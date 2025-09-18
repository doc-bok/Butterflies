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
        self.PIPELINE_FOLDER = Path('pipeline')
        self.TEMPLATES_FOLDER = self.PIPELINE_FOLDER / "templates"
        self.IMAGES_FOLDER = self.PIPELINE_FOLDER / "images"

        # === Base Textures for image generation
        self.GLASS_BOTTLE_TEXTURE_PATH = self.IMAGES_FOLDER / "glass_bottle.png"
        self.PAPER_TEXTURE_PATH = self.IMAGES_FOLDER / "paper.png"
        self.SPAWN_EGG_BASE_TEXTURE_PATH = self.IMAGES_FOLDER / "spawn_egg.png"
        self.SCROLL_TEXTURE_PATH = self.IMAGES_FOLDER / "scroll.png"
        self.NAIL_TEXTURE_PATH = self.IMAGES_FOLDER / "nail.png"

        self.RESOURCES_FOLDER = Path("resources")

        self.ASSETS_FOLDER = self.RESOURCES_FOLDER / "assets"
        self.MOD_ASSETS_FOLDER = self.ASSETS_FOLDER / "butterflies"
        self.MOD_TEXTURES_FOLDER = self.MOD_ASSETS_FOLDER / "textures"
        self.MOD_ENTITY_TEXTURES_FOLDER = self.MOD_TEXTURES_FOLDER / "entity"
        self.MOD_ITEM_TEXTURES_FOLDER = self.MOD_TEXTURES_FOLDER / "item"
        self.MOD_SPAWN_EGG_TEXTURES_FOLDER = self.MOD_ITEM_TEXTURES_FOLDER / "spawn_egg"

        self.DATA_FOLDER = self.RESOURCES_FOLDER / "data"
        self.MOD_DATA_FOLDER = self.DATA_FOLDER / "butterflies"

        self.CODE_GENERATION = Path("java/com/bokmcdok/butterflies/world/ButterflyInfo.java")
        self.LOCALISATION = self.MOD_ASSETS_FOLDER / "lang/en_us.json"
        self.FROG_FOOD = self.DATA_FOLDER / "minecraft/tags/entity_type/frog_food.json"

        self.ACHIEVEMENTS = self.MOD_DATA_FOLDER / "advancement/butterfly/"
        self.BIOME_MODIFIERS = self.MOD_DATA_FOLDER / "neoforge/biome_modifier/"
        self.BUTTERFLY_DATA = self.MOD_DATA_FOLDER / "butterfly_data/"

        self.BUTTERFLY_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/butterfly/"
        self.VARIANT_BUTTERFLY_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/butterfly_variant/"
        self.MOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/moth/"
        self.VARIANT_MOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/moth_variant/"
        self.BOTH_ACHIEVEMENT_TEMPLATES = self.TEMPLATES_FOLDER / "advancements/both/"
        self.BIOME_MODIFIER_TEMPLATES = self.TEMPLATES_FOLDER / "biome_modifiers/"

        self.BUTTERFLY_ENTITY_TEXTURE_PATH = self.MOD_ENTITY_TEXTURES_FOLDER / "butterfly"
        self.CATERPILLAR_ENTITY_TEXTURE_PATH = self.MOD_ENTITY_TEXTURES_FOLDER / "caterpillar"
        self.CATERPILLAR_ITEM_TEXTURE_PATH = self.MOD_ITEM_TEXTURES_FOLDER / "caterpillar"
        self.CHRYSALIS_ENTITY_TEXTURE_PATH = self.MOD_ENTITY_TEXTURES_FOLDER / "chrysalis"
        self.EGG_ITEM_TEXTURE_PATH = self.MOD_ITEM_TEXTURES_FOLDER / "butterfly_egg"

        self.BUTTERFLY_SPAWN_EGG_TEXTURE_PATH = self.MOD_SPAWN_EGG_TEXTURES_FOLDER / "butterfly"
        self.CATERPILLAR_SPAWN_EGG_TEXTURE_PATH = self.MOD_SPAWN_EGG_TEXTURES_FOLDER / "caterpillar"
        self.CHRYSALIS_SPAWN_EGG_TEXTURE_PATH = self.MOD_SPAWN_EGG_TEXTURES_FOLDER / "chrysalis"
        self.EGG_SPAWN_EGG_TEXTURE_PATH = self.MOD_SPAWN_EGG_TEXTURES_FOLDER / "egg"

        self.BOTTLED_BUTTERFLY_TEXTURE_PATH = self.MOD_ITEM_TEXTURES_FOLDER / "bottled_butterfly"
        self.BOTTLED_CATERPILLAR_TEXTURE_PATH = self.MOD_ITEM_TEXTURES_FOLDER / "bottled_caterpillar"
        self.BUTTERFLY_SCROLL_TEXTURE_PATH = self.MOD_ITEM_TEXTURES_FOLDER / "butterfly_scroll"
        self.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH = self.MOD_TEXTURES_FOLDER / 'gui' / 'butterfly_scroll'

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
