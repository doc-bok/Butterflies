import os

from pathlib import Path
from PIL import Image

from pipeline.config import Config
from pipeline.images.image_file_handler import ImageFileHandler

from .texture_source import ModelType
from .texture_source import TextureSource

class ScrollTextureGenerator:
    """
    Generator for Scroll Item/GUI Textures.
    """

    def __init__(self, config: Config, image_file_handler: ImageFileHandler):
        self.config = config
        self.logger = config.logger
        self.image_file_handler = image_file_handler

    def generate(self, butterfly_entity_textures: list[TextureSource]) -> None:
        self.logger.info(f"Generating scroll textures")

        self._generate_butterfly_scroll(butterfly_entity_textures)
        self._generate_scroll_gui(butterfly_entity_textures)

    def _generate_butterfly_scroll(self, butterfly_entity_textures: list[TextureSource]) -> None:
        """
        Generate butterfly scroll textures.
        """
        self.logger.info(f"Generating butterfly scroll textures")
        paper_image = self.image_file_handler.load_image(self.config.PAPER_TEXTURE_PATH)

        for texture_info in butterfly_entity_textures:
            overlay_image = self.image_file_handler.rotate_image(texture_info.image, 45)
            overlay_image = self.image_file_handler.resize_image(overlay_image, int(paper_image.width * 0.65), int(paper_image.height * 0.65), Image.Resampling.LANCZOS)
            overlay_image = self.image_file_handler.combine_images(paper_image, overlay_image, (1, 0))
            self.image_file_handler.save_image(self.config.BUTTERFLY_SCROLL_TEXTURE_PATH / ('butterfly_scroll_' + os.path.basename(texture_info.path)[10:]), overlay_image)

    def _generate_scroll_gui(self, butterfly_entity_textures: list[TextureSource]):
        """
        Generate butterfly scroll gui textures.
        """
        self.logger.info(f"Generating butterfly scroll GUI textures")

        scroll_image = self.image_file_handler.load_image(self.config.SCROLL_TEXTURE_PATH)
        nail_image = self.image_file_handler.load_image(self.config.NAIL_TEXTURE_PATH)

        for texture in butterfly_entity_textures:
            match texture.model_type:
                case ModelType.HUMMINGBIRD:
                    self._generate_hummingbird_scroll_gui(scroll_image, nail_image, texture.path)
                case ModelType.DETAILED:
                    self._generate_detailed_butterfly_scroll_gui(scroll_image, nail_image, texture.path)
                case _:
                    self._generate_butterfly_scroll_gui(scroll_image, nail_image, texture.path)

    def _generate_butterfly_scroll_gui(self,
                                       scroll_image: Image.Image,
                                       nail_image: Image.Image,
                                       texture: Path) -> None:
        # Base butterfly texture
        butterfly_image = self.image_file_handler.load_image(texture)

        # Antennae
        antenna_image = self.image_file_handler.crop_image(butterfly_image, (0, 0), (3, 2))
        antenna_image = self.image_file_handler.rotate_image(antenna_image, 90)
        antenna_image = self.image_file_handler.resize_image(
            antenna_image,
            int(antenna_image.width * 5),
            int(antenna_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(scroll_image, antenna_image, (-32, -80))
        antenna_image = antenna_image.transpose(Image.FLIP_LEFT_RIGHT)
        overlay_image = self.image_file_handler.combine_images(overlay_image, antenna_image, (-48, -80))

        # Body
        body_image = self.image_file_handler.crop_image(butterfly_image, (9, 22), (15, 2))
        body_image = self.image_file_handler.rotate_image(body_image, 90)
        body_image = self.image_file_handler.resize_image(
            body_image,
            int(body_image.width * 5),
            int(body_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, body_image, (-40, -36))

        # Wings
        wing_image = self.image_file_handler.crop_image(butterfly_image, (10, 0), (17, 10))
        wing_image = self.image_file_handler.rotate_image(wing_image, 90)
        wing_image = self.image_file_handler.resize_image(
            wing_image,
            int(wing_image.width * 5),
            int(wing_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-68, -35))

        wing_image = self.image_file_handler.crop_image(butterfly_image, (10, 10), (17, 10))
        wing_image = self.image_file_handler.rotate_image(wing_image, 90)
        wing_image = self.image_file_handler.resize_image(
            wing_image,
            int(wing_image.width * 5),
            int(wing_image.height * 5),
            Image.Resampling.NEAREST
        )
        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-11, -35))

        # Nail
        overlay_image = self.image_file_handler.combine_images(overlay_image, nail_image)
        self.image_file_handler.save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / TextureSource.strip_prefix(os.path.basename(texture), TextureSource.BUTTERFLY_PREFIX), overlay_image)

    def _generate_detailed_butterfly_scroll_gui(self,
                                       scroll_image: Image.Image,
                                       nail_image: Image.Image,
                                       texture: Path) -> None:
        # Base butterfly texture
        butterfly_image = self.image_file_handler.load_image(texture)

        # Antennae
        antenna_image = self.image_file_handler.crop_image(butterfly_image, (0, 48), (6, 4))
        antenna_image = self.image_file_handler.rotate_image(antenna_image, 90)
        antenna_image = self.image_file_handler.resize_image(
            antenna_image,
            int(antenna_image.width * 2.5),
            int(antenna_image.height * 2.5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(scroll_image, antenna_image, (-32, -80))
        antenna_image = antenna_image.transpose(Image.FLIP_LEFT_RIGHT)
        overlay_image = self.image_file_handler.combine_images(overlay_image, antenna_image, (-48, -80))

        # Body
        body_image = self.image_file_handler.crop_image(butterfly_image, (18, 44), (30, 4))
        body_image = self.image_file_handler.rotate_image(body_image, 90)
        body_image = self.image_file_handler.resize_image(
            body_image,
            int(body_image.width * 2.5),
            int(body_image.height * 2.5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, body_image, (-40, -36))

        # Wings
        wing_image = self.image_file_handler.crop_image(butterfly_image, (0, 0), (34, 20))
        wing_image = self.image_file_handler.rotate_image(wing_image, 90)
        wing_image = self.image_file_handler.resize_image(
            wing_image,
            int(wing_image.width * 2.5),
            int(wing_image.height * 2.5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-68, -35))

        wing_image = self.image_file_handler.crop_image(butterfly_image, (0, 20), (34, 20))
        wing_image = self.image_file_handler.rotate_image(wing_image, 90)
        wing_image = self.image_file_handler.resize_image(
            wing_image,
            int(wing_image.width * 2.5),
            int(wing_image.height * 2.5),
            Image.Resampling.NEAREST
        )
        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-11, -35))

        # Nail
        overlay_image = self.image_file_handler.combine_images(overlay_image, nail_image)
        self.image_file_handler.save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / TextureSource.strip_prefix(os.path.basename(texture), TextureSource.BUTTERFLY_PREFIX), overlay_image)


    def _generate_hummingbird_scroll_gui(self,
                                         scroll_image: Image.Image,
                                         nail_image: Image.Image,
                                         texture: Path) -> None:
        # Base butterfly texture
        butterfly_image = self.image_file_handler.load_image(texture)

        # Antennae
        antannae_image = self.image_file_handler.crop_image(butterfly_image, (0, 15), (6, 4))
        antannae_image = self.image_file_handler.resize_image(
            antannae_image,
            int(antannae_image.width * 5),
            int(antannae_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(scroll_image, antannae_image, (-40, -75))

        # Head
        head_image = self.image_file_handler.crop_image(butterfly_image, (13, 12), (4, 3))
        head_image = self.image_file_handler.resize_image(
            head_image,
            int(head_image.width * 5),
            int(head_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, head_image, (-40, -59))

        # Proboscis
        proboscis_image = self.image_file_handler.crop_image(butterfly_image, (0, 8), (9, 1))
        proboscis_image = self.image_file_handler.rotate_image(proboscis_image, 90)
        proboscis_image = self.image_file_handler.resize_image(
            proboscis_image,
            int(proboscis_image.width * 5),
            int(proboscis_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, proboscis_image, (-40, -80))

        # Thorax
        thorax_image = self.image_file_handler.crop_image(butterfly_image, (3, 3), (4, 5))
        thorax_image = self.image_file_handler.resize_image(
            thorax_image,
            int(thorax_image.width * 5),
            int(thorax_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, thorax_image, (-40, -40))

        # Abdomen
        abdomen_image = self.image_file_handler.crop_image(butterfly_image, (16, 2), (4, 4))
        abdomen_image = self.image_file_handler.resize_image(
            abdomen_image,
            int(abdomen_image.width * 5),
            int(abdomen_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, abdomen_image, (-40, -20))

        # Tail
        tail_image = self.image_file_handler.crop_image(butterfly_image, (14, 6), (6, 2))
        tail_image = self.image_file_handler.resize_image(
            tail_image,
            int(tail_image.width * 5),
            int(tail_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, tail_image, (-40, -5))

        # Wings
        wing_image = self.image_file_handler.crop_image(butterfly_image, (0, 9), (5, 6))
        wing_image = self.image_file_handler.resize_image(
            wing_image,
            int(wing_image.width * 5),
            int(wing_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-62, -40))
        wing_image = wing_image.transpose(Image.FLIP_LEFT_RIGHT)
        overlay_image = self.image_file_handler.combine_images(overlay_image, wing_image, (-17, -40))

        # Nail
        overlay_image = self.image_file_handler.combine_images(overlay_image, nail_image)
        self.image_file_handler.save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)