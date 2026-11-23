import os

from PIL import Image

from pipeline.config import Config
from pipeline.images.image_file_handler import ImageFileHandler

class SpawnEggTextureGenerator:
    """
    Generator for Spawn Egg Item Textures.
    """

    def __init__(self, config: Config, image_file_handler: ImageFileHandler):
        self.config = config
        self.logger = config.logger
        self.image_file_handler = image_file_handler

    def generate(self, butterfly_entity_textures: [Image.Image]) -> None:
        self.logger.info(f"Generating spawn egg item textures")

        egg_image = self.image_file_handler.load_image(self.config.SPAWN_EGG_BASE_TEXTURE_PATH)
        self._generate_egg_spawn_eggs(egg_image)
        self._generate_caterpillar_spawn_eggs(egg_image)
        self._generate_chrysalis_spawn_eggs(egg_image)
        self._generate_butterfly_spawn_eggs(egg_image, butterfly_entity_textures)

    def _generate_egg_spawn_eggs(self, egg_image: Image.Image) -> None:
        """
        Generate egg spawn egg textures.
        """
        self.logger.info(f"Generating egg spawn egg textures")

        butterfly_entity_textures = [
            f for f in self.config.EGG_ITEM_TEXTURE_PATH.iterdir()
            if os.path.basename(f).endswith("_egg.png")
        ]

        for texture in butterfly_entity_textures:
            overlay_image = self.image_file_handler.load_image(texture)
            overlay_image = self.image_file_handler.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.image_file_handler.combine_images(egg_image, overlay_image)
            self.image_file_handler.save_image(self.config.EGG_SPAWN_EGG_TEXTURE_PATH / (os.path.basename(texture)[:-8] + '.png'), overlay_image)


    def _generate_caterpillar_spawn_eggs(self, egg_image: Image.Image) -> None:
        """
        Generate caterpillar spawn egg textures.
        """
        self.logger.info(f"Generating caterpillar spawn egg textures")

        caterpillar_item_textures = [
            f for f in self.config.CATERPILLAR_ITEM_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("caterpillar_")
        ]

        for texture in caterpillar_item_textures:
            overlay_image = self.image_file_handler.load_image(texture)
            overlay_image = self.image_file_handler.combine_images(egg_image, overlay_image)
            self.image_file_handler.save_image(self.config.CATERPILLAR_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[12:], overlay_image)

    def _generate_chrysalis_spawn_eggs(self, egg_image: Image.Image) -> None:
        """
        Generate chrysalis spawn egg textures.
        """
        self.logger.info(f"Generating chrysalis spawn egg textures")

        butterfly_entity_textures = [
            f for f in self.config.CHRYSALIS_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("chrysalis_")
        ]

        for texture in butterfly_entity_textures:
            overlay_image = self.image_file_handler.load_image(texture)
            overlay_image = self.image_file_handler.crop_image(overlay_image, (0, 0), (16, 16))
            overlay_image = self.image_file_handler.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.image_file_handler.combine_images(egg_image, overlay_image, (3, 1))
            self.image_file_handler.save_image(self.config.CHRYSALIS_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)

    def _generate_butterfly_spawn_eggs(self, egg_image: Image.Image, butterfly_entity_textures: [Image.Image]) -> None:
        """
        Generate butterfly spawn egg textures.
        """
        self.logger.info(f"Generating butterfly spawn egg textures")

        for texture_info in butterfly_entity_textures:
            overlay_image = self.image_file_handler.rotate_image(texture_info[1], 90)
            overlay_image = self.image_file_handler.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.image_file_handler.combine_images(egg_image, overlay_image, (0, 1))
            self.image_file_handler.save_image(self.config.BUTTERFLY_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture_info[0])[10:], overlay_image)