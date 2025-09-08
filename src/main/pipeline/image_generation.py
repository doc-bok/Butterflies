import os

from .config import Config

from pathlib import Path
from PIL import Image

class ImageGenerator:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    @staticmethod
    def load_image(image_path: Path) -> Image.Image:
        return Image.open(image_path)

    @staticmethod
    def crop_image(
            image: Image.Image,
            crop_start: tuple[int, int],
            crop_size: tuple[int, int]
    ) -> Image.Image:
        return image.crop((
            crop_start[0],
            crop_start[1],
            crop_start[0] + crop_size[0],
            crop_start[1] + crop_size[1]
        ))

    @staticmethod
    def rotate_image(image: Image.Image, rotate: int) -> Image.Image:
        return image.rotate(rotate, expand=True)

    @staticmethod
    def resize_image(image: Image.Image, new_width: int, new_height: int) -> Image.Image:
        return image.resize((new_width, new_height), Image.Resampling.LANCZOS)

    @staticmethod
    def combine_images(
            base_image: Image.Image,
            overlay_image: Image.Image,
            offset: tuple[int, int] = (0, 0)
    ) -> Image.Image:

        # Ensure the overlay fits
        if overlay_image.size > base_image.size:
            overlay_resized = overlay_image.resize(base_image.size, Image.Resampling.LANCZOS)
        else:
            overlay_resized = overlay_image

        position =\
            (((base_image.width - overlay_resized.width) // 2) + offset[0],
             ((base_image.height - overlay_resized.height) // 2) + offset[1])

        # Offset the image
        overlay_final = Image.new('RGBA', base_image.size, (0, 0, 0, 0))
        overlay_final.paste(overlay_resized, position, overlay_resized)

        return Image.alpha_composite(base_image, overlay_final)

    def save_image(self, image_path: Path, image: Image.Image) -> None:
        image.save(image_path)

    def generate_egg_spawn_eggs(self, egg_image: Image.Image) -> None:
        self.logger.info(f"Generating egg spawn eggs")

        butterfly_entity_textures = [
            f for f in self.config.EGG_ITEM_TEXTURE_PATH.iterdir()
            if os.path.basename(f).endswith("_egg.png")
        ]

        for texture in butterfly_entity_textures:
            overlay_image = self.load_image(texture)
            overlay_image = self.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.combine_images(egg_image, overlay_image)
            self.save_image(self.config.EGG_SPAWN_EGG_TEXTURE_PATH / (os.path.basename(texture)[:-8] + '.png'), overlay_image)


    def generate_caterpillar_spawn_eggs(self, egg_image: Image.Image) -> None:
        self.logger.info(f"Generating caterpillar spawn eggs")

        caterpillar_item_textures = [
            f for f in self.config.CATERPILLAR_ITEM_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("caterpillar_")
        ]

        for texture in caterpillar_item_textures:
            overlay_image = self.load_image(texture)
            overlay_image = self.combine_images(egg_image, overlay_image)
            self.save_image(self.config.CATERPILLAR_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[12:], overlay_image)

    def generate_chrysalis_spawn_eggs(self, egg_image: Image.Image) -> None:
        self.logger.info(f"Generating chrysalis spawn eggs")
        
        butterfly_entity_textures = [
            f for f in self.config.CHRYSALIS_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("chrysalis_")
        ]

        for texture in butterfly_entity_textures:
            overlay_image = self.load_image(texture)
            overlay_image = self.crop_image(overlay_image, (0, 0), (16, 16))
            overlay_image = self.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.combine_images(egg_image, overlay_image, (3, 1))
            self.save_image(self.config.CHRYSALIS_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)

    def generate_butterfly_spawn_eggs(self, egg_image: Image.Image) -> None:
        self.logger.info(f"Generating butterfly spawn eggs")

        butterfly_entity_textures = [
            f for f in self.config.BUTTERFLY_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("butterfly_")
        ]

        for texture in butterfly_entity_textures:
            overlay_image = self.load_image(texture)
            overlay_image = self.crop_image(overlay_image, (10, 0), (17, 20))
            overlay_image = self.rotate_image(overlay_image, 90)
            overlay_image = self.resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self.combine_images(egg_image, overlay_image, (0, 1))
            self.save_image(self.config.BUTTERFLY_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)

    def generate_spawn_eggs(self) -> None:
        egg_image = self.load_image(self.config.SPAWN_EGG_BASE_TEXTURE_PATH)
        self.generate_egg_spawn_eggs(egg_image)
        self.generate_caterpillar_spawn_eggs(egg_image)
        self.generate_chrysalis_spawn_eggs(egg_image)
        self.generate_butterfly_spawn_eggs(egg_image)