import os

from PIL import Image

from pipeline.config import Config
from pipeline.images.image_file_handler import ImageFileHandler

from .texture_source import TextureSource, ModelType


class BottledTextureGenerator:
    """
    Generator for Bottled Item Textures.
    """

    def __init__(self, config: Config, image_file_handler: ImageFileHandler):
        self.config = config
        self.logger = config.logger
        self.image_file_handler = image_file_handler

    def generate(self, butterfly_entity_textures: list[TextureSource]) -> None:
        self.logger.info(f"Generating bottled item textures")

        bottle_image = self.image_file_handler.load_image(self.config.GLASS_BOTTLE_TEXTURE_PATH)

        caterpillar_item_textures = [
            TextureSource(
                path=texture_path,
                image=self.image_file_handler.load_image(texture_path),
                model_type=ModelType.BASE
            )
            for texture_path in TextureSource.find_textures_with_prefix(
                self.config.CATERPILLAR_ITEM_TEXTURE_PATH,
                prefix=TextureSource.CATERPILLAR_PREFIX,
            )
        ]

        self._generate_bottled_butterfly(bottle_image, butterfly_entity_textures)
        self._generate_bottled_caterpillar(bottle_image, caterpillar_item_textures)

    def _generate_bottled_butterfly(self, bottle_image: Image.Image, butterfly_entity_textures: list[TextureSource]) -> None:
        """
        Generate bottled butterfly textures.
        """
        self.logger.info(f"Generating bottled butterfly textures")

        for texture_info in butterfly_entity_textures:
            overlay_image = self.image_file_handler.rotate_image(texture_info.image, 90)
            overlay_image = self.image_file_handler.resize_image(overlay_image, int(bottle_image.width * 0.45), int(bottle_image.height * 0.45), Image.Resampling.LANCZOS)
            overlay_image = self.image_file_handler.combine_images(bottle_image, overlay_image, (1, 4))
            overlay_image = self.image_file_handler.combine_images(overlay_image, bottle_image)
            self.image_file_handler.save_image(self.config.BOTTLED_BUTTERFLY_TEXTURE_PATH / ('bottled_' + os.path.basename(texture_info.path)[10:]), overlay_image)

    def _generate_bottled_caterpillar(self, bottle_image: Image.Image, caterpillar_item_textures: list[TextureSource]) -> None:
        """
        Generate bottled butterfly textures.
        """
        self.logger.info(f"Generating bottled butterfly textures")

        for texture_info in caterpillar_item_textures:
            overlay_image = self.image_file_handler.resize_image(texture_info.image, int(bottle_image.width * 0.58), int(bottle_image.height * 0.58), Image.Resampling.LANCZOS)
            overlay_image = self.image_file_handler.combine_images(bottle_image, overlay_image, (1, 3))
            overlay_image = self.image_file_handler.combine_images(overlay_image, bottle_image)
            self.image_file_handler.save_image(self.config.BOTTLED_CATERPILLAR_TEXTURE_PATH / ('bottled_caterpillar_' + os.path.basename(texture_info.path)[12:]), overlay_image)