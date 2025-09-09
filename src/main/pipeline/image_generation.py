import os

from .config import Config

from pathlib import Path
from PIL import Image

class ImageGenerator:
    def __init__(self, config: Config):
        """
        Set up the logger and the configuration
        """
        self.config = config
        self.logger = config.logger

    @staticmethod
    def _load_image(image_path: Path) -> Image.Image:
        """
        Load an image from the given path
        """
        return Image.open(image_path)

    @staticmethod
    def _crop_image(
            image: Image.Image,
            crop_start: tuple[int, int],
            crop_size: tuple[int, int]
    ) -> Image.Image:
        """
        Crop the given image and return the cropped image.
        """
        return image.crop((
            crop_start[0],
            crop_start[1],
            crop_start[0] + crop_size[0],
            crop_start[1] + crop_size[1]
        ))

    @staticmethod
    def _rotate_image(image: Image.Image, rotate: int) -> Image.Image:
        """
        Rotate the given image and return the rotated image.
        """
        return image.rotate(rotate, expand=True)

    @staticmethod
    def _resize_image(
            image: Image.Image,
            new_width: int,
            new_height: int,
            resampling: Image.Resampling = Image.Resampling.LANCZOS) -> Image.Image:
        """
        Resize the given image and return the resized image.
        """
        return image.resize((new_width, new_height), resampling)

    @staticmethod
    def _combine_images(
            base_image: Image.Image,
            overlay_image: Image.Image,
            offset: tuple[int, int] = (0, 0)
    ) -> Image.Image:
        """
        Combine the given images and return the combined image.
        """

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

    @staticmethod
    def _save_image(image_path: Path, image: Image.Image) -> None:
        """
        Save the given image to the given path.
        """
        image.save(image_path)

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
            overlay_image = self._load_image(texture)
            overlay_image = self._resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self._combine_images(egg_image, overlay_image)
            self._save_image(self.config.EGG_SPAWN_EGG_TEXTURE_PATH / (os.path.basename(texture)[:-8] + '.png'), overlay_image)


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
            overlay_image = self._load_image(texture)
            overlay_image = self._combine_images(egg_image, overlay_image)
            self._save_image(self.config.CATERPILLAR_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[12:], overlay_image)

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
            overlay_image = self._load_image(texture)
            overlay_image = self._crop_image(overlay_image, (0, 0), (16, 16))
            overlay_image = self._resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self._combine_images(egg_image, overlay_image, (3, 1))
            self._save_image(self.config.CHRYSALIS_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)

    def _generate_butterfly_spawn_eggs(self, egg_image: Image.Image, butterfly_entity_textures) -> None:
        """
        Generate butterfly spawn egg textures.
        """
        self.logger.info(f"Generating butterfly spawn egg textures")

        for texture_info in butterfly_entity_textures:
            overlay_image = self._rotate_image(texture_info[1], 90)
            overlay_image = self._resize_image(overlay_image, int(egg_image.width * 0.8), int(egg_image.height * 0.8))
            overlay_image = self._combine_images(egg_image, overlay_image, (0, 1))
            self._save_image(self.config.BUTTERFLY_SPAWN_EGG_TEXTURE_PATH / os.path.basename(texture_info[0])[10:], overlay_image)

    def _generate_spawn_eggs(self, butterfly_entity_textures) -> None:
        """
        Generate spawn egg textures.
        """
        egg_image = self._load_image(self.config.SPAWN_EGG_BASE_TEXTURE_PATH)
        self._generate_egg_spawn_eggs(egg_image)
        self._generate_caterpillar_spawn_eggs(egg_image)
        self._generate_chrysalis_spawn_eggs(egg_image)
        self._generate_butterfly_spawn_eggs(egg_image, butterfly_entity_textures)

    def _generate_bottled_butterfly(self, butterfly_entity_textures) -> None:
        """
        Generate bottled butterfly textures.
        """
        self.logger.info(f"Generating bottled butterfly textures")
        bottle_image = self._load_image(self.config.GLASS_BOTTLE_TEXTURE_PATH)

        for texture_info in butterfly_entity_textures:
            overlay_image = self._rotate_image(texture_info[1], 90)
            overlay_image = self._resize_image(overlay_image, int(bottle_image.width * 0.45), int(bottle_image.height * 0.45))
            overlay_image = self._combine_images(bottle_image, overlay_image, (1, 4))
            overlay_image = self._combine_images(overlay_image, bottle_image)
            self._save_image(self.config.BOTTLED_BUTTERFLY_TEXTURE_PATH / ('bottled_' + os.path.basename(texture_info[0])[10:]), overlay_image)

    def _generate_butterfly_scroll(self, butterfly_entity_textures) -> None:
        """
        Generate butterfly scroll textures.
        """
        self.logger.info(f"Generating butterfly scroll textures")
        paper_image = self._load_image(self.config.PAPER_TEXTURE_PATH)

        for texture_info in butterfly_entity_textures:
            overlay_image = self._rotate_image(texture_info[1], 45)
            overlay_image = self._resize_image(overlay_image, int(paper_image.width * 0.65), int(paper_image.height * 0.65))
            overlay_image = self._combine_images(paper_image, overlay_image, (1, 0))
            self._save_image(self.config.BUTTERFLY_SCROLL_TEXTURE_PATH / ('butterfly_scroll_' + os.path.basename(texture_info[0])[10:]), overlay_image)

    def _generate_butterfly_scroll_gui(self):
        """
        Generate butterfly scroll gui textures.
        """
        self.logger.info(f"Generating butterfly scroll GUI textures")

        scroll_image = self._load_image(self.config.SCROLL_TEXTURE_PATH)
        nail_image = self._load_image(self.config.NAIL_TEXTURE_PATH)

        butterfly_entity_textures = [
            f for f in self.config.BUTTERFLY_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("butterfly_")
        ]

        for texture in butterfly_entity_textures:
            # Base butterfly texture
            butterfly_image = self._load_image(texture)

            # Antennae
            antenna_image = self._crop_image(butterfly_image, (0, 0), (3, 2))
            antenna_image = self._rotate_image(antenna_image, 90)
            antenna_image = self._resize_image(
                antenna_image,
                int(antenna_image.width * 5),
                int(antenna_image.height * 5),
                Image.Resampling.NEAREST
            )

            overlay_image = self._combine_images(scroll_image, antenna_image, (-32, -80))
            antenna_image = antenna_image.transpose(Image.FLIP_LEFT_RIGHT)
            overlay_image = self._combine_images(overlay_image, antenna_image, (-48, -80))

            # Body
            body_image = self._crop_image(butterfly_image, (9, 22), (24, 24))
            body_image = self._rotate_image(body_image, 90)
            body_image = self._resize_image(
                body_image,
                int(body_image.width * 5),
                int(body_image.height * 5),
                Image.Resampling.NEAREST
            )

            overlay_image = self._combine_images(overlay_image, body_image, (15, -59))

            # Wings
            wing_image = self._crop_image(butterfly_image, (10, 0), (17, 10))
            wing_image = self._rotate_image(wing_image, 90)
            wing_image = self._resize_image(
                wing_image,
                int(wing_image.width * 5),
                int(wing_image.height * 5),
                Image.Resampling.NEAREST
            )

            overlay_image = self._combine_images(overlay_image, wing_image, (-68, -35))
            wing_image = wing_image.transpose(Image.FLIP_LEFT_RIGHT)
            overlay_image = self._combine_images(overlay_image, wing_image, (-11, -35))

            # Nail
            overlay_image = self._combine_images(overlay_image, nail_image)
            self._save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)

    def generate_textures(self) -> None:
        """
        Generate all derivative textures.
        """
        butterfly_entity_textures = [
            [f, self._crop_image(self._load_image(f), (10, 0), (17, 20))]
            for f in self.config.BUTTERFLY_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("butterfly_")
        ]

        self._generate_spawn_eggs(butterfly_entity_textures)
        self._generate_bottled_butterfly(butterfly_entity_textures)
        self._generate_butterfly_scroll(butterfly_entity_textures)
        self._generate_butterfly_scroll_gui()