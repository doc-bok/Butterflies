import os

from .config import Config
from .images.image_file_handler import ImageFileHandler
from .texture_generators.caterpillar_texture_generator import CaterpillarTextureGenerator
from .texture_generators.spawn_egg_texture_generator import SpawnEggTextureGenerator
from .texture_generators.bottled_texture_generator import BottledTextureGenerator
from .texture_generators.scroll_texture_generator import ScrollTextureGenerator

from PIL import Image

class ImageGenerator:

    def __init__(self, config: Config):
        """
        Set up the logger and the configuration
        """
        self.config = config
        self.logger = config.logger
        self.image_file_handler = ImageFileHandler()

        # TODO: Implement system based on butterfly model types.
        self.HUMMINGBIRD_MOTHS = [
            'clearwing-hummingbird',
            'hummingbird',
            'white-lined-sphinx'
        ]

    def _load_butterfly_entity_texture(self, f):
        image = self.image_file_handler.load_image(f)

        # Is this a hummingbird moth?
        if any(moth in str(f) for moth in self.HUMMINGBIRD_MOTHS):

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
        else:

            # Not a hummingbird, just extract the wings
            return self.image_file_handler.crop_image(image, (10, 0), (17, 20))

    def generate_textures(self) -> None:
        """
        Generate all derivative textures.
        """
        butterfly_entity_textures = [
            [f, self._load_butterfly_entity_texture(f)]
            for f in self.config.BUTTERFLY_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("butterfly_")
        ]

        bottle_image = self.image_file_handler.load_image(self.config.GLASS_BOTTLE_TEXTURE_PATH)

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