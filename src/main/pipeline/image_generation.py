import os

from pathlib import Path
from typing import Any

from .config import Config
from .images.image_file_handler import ImageFileHandler
from .texture_generators.caterpillar_texture_generator import CaterpillarTextureGenerator
from .texture_generators.spawn_egg_texture_generator import SpawnEggTextureGenerator
from .texture_generators.bottled_texture_generator import BottledTextureGenerator
from .texture_generators.scroll_texture_generator import ScrollTextureGenerator
from .texture_generators.texture_source import ModelType
from .texture_generators.texture_source import TextureSource

from PIL import Image

class ImageGenerator:

    def __init__(self, config: Config):
        """
        Set up the logger and the configuration
        """
        self.config = config
        self.logger = config.logger
        self.image_file_handler = ImageFileHandler()

    def _get_model_type(self, path: Path, species_data: dict[str, dict | None | dict[Any, Any]]) -> ModelType:
        species = TextureSource.strip_prefix(os.path.basename(path), TextureSource.BUTTERFLY_PREFIX)
        species = TextureSource.strip_suffix(species, ".png")

        self.logger.info(f"Species = %s", species)
        if species in species_data:
            model_type = species_data[species].get("model") if species_data[species] else "none"
            model_type = model_type.lower()

            self.logger.info(f"Model Type = %s", model_type)

            if model_type == "hummingbird":
                return ModelType.HUMMINGBIRD

            if model_type == "glow":
                return ModelType.GLOW

            if model_type == "detailed":
                return ModelType.DETAILED

        return ModelType.BASE

    def _load_butterfly_entity_texture(self, path: Path, species_data: dict[str, dict | None | dict[Any, Any]]):
        image = self.image_file_handler.load_image(path)

        model_type = self._get_model_type(path, species_data)

        self.logger.info(f"Model Type on load = %s", model_type)

        # Is this a hummingbird moth?
        match model_type:
            case ModelType.HUMMINGBIRD:

                # Get the wing texture
                image = self.image_file_handler.crop_image(image, (0, 9), (5, 6))

                # Create a mirror image of the texture
                reflected_image = image.transpose(Image.FLIP_LEFT_RIGHT)

                # Create a new image with both wings
                new_image = Image.new('RGBA', (image.width * 2, image.height))
                new_image.paste(image, (0, 0))
                new_image.paste(reflected_image, (image.width, 0))

                # Rotate the image so it is facing the same way as other butterfly
                # images
                return self.image_file_handler.rotate_image(new_image, -90)

            case ModelType.DETAILED:
                return self.image_file_handler.crop_image(image, (0, 0), (34, 40))

            case _:
                return self.image_file_handler.crop_image(image, (10, 0), (17, 20))

    def generate_textures(self, species_data: dict[str, dict | None | dict[Any, Any]]) -> None:
        """
        Generate all derivative textures.
        """
        butterfly_entity_textures = [
            TextureSource(
                path=texture_path,
                image=self._load_butterfly_entity_texture(texture_path, species_data),
                model_type=self._get_model_type(texture_path, species_data)
            )
            for texture_path in TextureSource.find_textures_with_prefix(
                self.config.BUTTERFLY_ENTITY_TEXTURE_PATH,
                prefix=TextureSource.BUTTERFLY_PREFIX,
            )
        ]

        # Create generators.
        caterpillar_generator = CaterpillarTextureGenerator(self.config, self.image_file_handler)
        spawn_egg_generator = SpawnEggTextureGenerator(self.config, self.image_file_handler)
        bottled_generator = BottledTextureGenerator(self.config, self.image_file_handler)
        scroll_generator = ScrollTextureGenerator(self.config, self.image_file_handler)

        # Now generate!
        caterpillar_generator.generate()
        spawn_egg_generator.generate(butterfly_entity_textures)
        bottled_generator.generate(butterfly_entity_textures)
        scroll_generator.generate(butterfly_entity_textures)