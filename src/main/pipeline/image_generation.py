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
        self.HUMMINGBIRD_MOTHS = [
            'clearwing-hummingbird',
            'hummingbird',
            'white-lined-sphinx'
        ]

    def _load_image(self, image_path: Path) -> Image.Image:
        """
        Loads an image from the given path.

        Args:
            image_path (Path): Path to the image file.

        Returns:
            Image.Image: Loaded image.

        Raises:
            OSError: If the image could not be opened.
        """
        try:
            with Image.open(image_path) as img:
                img.load()
                return img.copy()
        except OSError as e:
            self.logger.error(f"Could not open image {image_path}: {e}")
            raise

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
            resampling: Image.Resampling = Image.Resampling.LANCZOS
    ) -> Image.Image:
        """
        Resizes the given image.

        Args:
            image (Image.Image): Source image.
            new_width (int): Target width.
            new_height (int): Target height.
            resampling (Image.Resampling): Resampling algorithm.

        Returns:
            Image.Image: Resized image.
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

        # Ensure we are using RGBA in our images.
        if base_image.mode != 'RGBA':
            base_image = base_image.convert('RGBA')

        if overlay_image.mode != 'RGBA':
            overlay_image = overlay_image.convert('RGBA')

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

    def _save_image(self, image_path: Path, image: Image.Image) -> None:
        """
        Saves the image to the path, optimizing PNG files.

        Args:
            image_path (Path): Path to save image.
            image (Image.Image): Image to save.
        """
        try:
            suffix = image_path.suffix.lstrip(".").upper()
            if suffix == "PNG":
                image.save(image_path, optimize=True)
            else:
                image.save(image_path)
        except Exception as e:
            self.logger.error(f"Failed to save image {image_path}: {e}")
            raise

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

    def _generate_bottled_butterfly(self, bottle_image, butterfly_entity_textures) -> None:
        """
        Generate bottled butterfly textures.
        """
        self.logger.info(f"Generating bottled butterfly textures")

        for texture_info in butterfly_entity_textures:
            overlay_image = self._rotate_image(texture_info[1], 90)
            overlay_image = self._resize_image(overlay_image, int(bottle_image.width * 0.45), int(bottle_image.height * 0.45))
            overlay_image = self._combine_images(bottle_image, overlay_image, (1, 4))
            overlay_image = self._combine_images(overlay_image, bottle_image)
            self._save_image(self.config.BOTTLED_BUTTERFLY_TEXTURE_PATH / ('bottled_' + os.path.basename(texture_info[0])[10:]), overlay_image)

    def _generate_bottled_caterpillar(self, bottle_image, caterpillar_item_textures) -> None:
        """
        Generate bottled butterfly textures.
        """
        self.logger.info(f"Generating bottled butterfly textures")

        for texture_info in caterpillar_item_textures:
            overlay_image = self._resize_image(texture_info[1], int(bottle_image.width * 0.58), int(bottle_image.height * 0.58))
            overlay_image = self._combine_images(bottle_image, overlay_image, (1, 3))
            overlay_image = self._combine_images(overlay_image, bottle_image)
            self._save_image(self.config.BOTTLED_CATERPILLAR_TEXTURE_PATH / ('bottled_caterpillar_' + os.path.basename(texture_info[0])[12:]), overlay_image)

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

    def _generate_scroll_gui(self):
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
            if any(moth in str(texture) for moth in self.HUMMINGBIRD_MOTHS):
                self._generate_hummingbird_scroll_gui(scroll_image, nail_image, texture)
            else:
                self._generate_butterfly_scroll_gui(scroll_image, nail_image, texture)

    def _generate_butterfly_scroll_gui(self,
                                       scroll_image: Image.Image,
                                       nail_image: Image.Image,
                                       texture: Path) -> None:
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

        wing_image = self._crop_image(butterfly_image, (10, 10), (17, 10))
        wing_image = self._rotate_image(wing_image, 90)
        wing_image = self._resize_image(
            wing_image,
            int(wing_image.width * 5),
            int(wing_image.height * 5),
            Image.Resampling.NEAREST
        )
        overlay_image = self._combine_images(overlay_image, wing_image, (-11, -35))

        # Nail
        overlay_image = self._combine_images(overlay_image, nail_image)
        self._save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)


    def _generate_hummingbird_scroll_gui(self,
                                         scroll_image: Image.Image,
                                         nail_image: Image.Image,
                                         texture: Path) -> None:
        # Base butterfly texture
        butterfly_image = self._load_image(texture)

        # Antennae
        antannae_image = self._crop_image(butterfly_image, (0, 15), (6, 4))
        antannae_image = self._resize_image(
            antannae_image,
            int(antannae_image.width * 5),
            int(antannae_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(scroll_image, antannae_image, (-40, -75))

        # Head
        head_image = self._crop_image(butterfly_image, (13, 12), (4, 3))
        head_image = self._resize_image(
            head_image,
            int(head_image.width * 5),
            int(head_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, head_image, (-40, -59))

        # Proboscis
        proboscis_image = self._crop_image(butterfly_image, (0, 8), (9, 1))
        proboscis_image = self._rotate_image(proboscis_image, 90)
        proboscis_image = self._resize_image(
            proboscis_image,
            int(proboscis_image.width * 5),
            int(proboscis_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, proboscis_image, (-40, -80))

        # Thorax
        thorax_image = self._crop_image(butterfly_image, (3, 3), (4, 5))
        thorax_image = self._resize_image(
            thorax_image,
            int(thorax_image.width * 5),
            int(thorax_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, thorax_image, (-40, -40))

        # Abdomen
        abdomen_image = self._crop_image(butterfly_image, (16, 2), (4, 4))
        abdomen_image = self._resize_image(
            abdomen_image,
            int(abdomen_image.width * 5),
            int(abdomen_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, abdomen_image, (-40, -20))

        # Tail
        tail_image = self._crop_image(butterfly_image, (14, 6), (6, 2))
        tail_image = self._resize_image(
            tail_image,
            int(tail_image.width * 5),
            int(tail_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, tail_image, (-40, -5))

        # Wings
        wing_image = self._crop_image(butterfly_image, (0, 9), (5, 6))
        wing_image = self._resize_image(
            wing_image,
            int(wing_image.width * 5),
            int(wing_image.height * 5),
            Image.Resampling.NEAREST
        )

        overlay_image = self._combine_images(overlay_image, wing_image, (-62, -40))
        wing_image = wing_image.transpose(Image.FLIP_LEFT_RIGHT)
        overlay_image = self._combine_images(overlay_image, wing_image, (-17, -40))

        # Nail
        overlay_image = self._combine_images(overlay_image, nail_image)
        self._save_image(self.config.BUTTERFLY_SCROLL_GUI_TEXTURE_PATH / os.path.basename(texture)[10:], overlay_image)


    def _generate_caterpillars(self) -> None:
        """
        Generate caterpillar spawn egg textures.
        """
        self.logger.info(f"Generating caterpillar spawn egg textures")

        caterpillar_entity_textures = [
            f for f in self.config.CATERPILLAR_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("caterpillar_")
        ]

        for texture in caterpillar_entity_textures:
            entity_image = self._load_image(texture)
            item_image = Image.new('RGBA', (16, 16), (0, 0, 0, 0))

            pixel_map = [
                # Head
                [(2, 8),    (22, 23)],
                [(1, 9),    (20, 24)],
                [(2, 9),    (22, 24)],
                [(1, 10),   (20, 25)],
                [(1, 11),   (20, 26)],
                [(3, 9),    (23, 24)],
                [(2, 10),   (21, 26)],
                [(3, 10),   (22, 26)],
                [(2, 11),   (21, 27)],
                [(3, 11),   (22, 27)],

                # Body Segment 1
                [(3, 7),   (3, 13)],
                [(4, 7),   (13, 13)],
                [(5, 7),   (14, 14)],
                [(3, 8),   (0, 15)],
                [(4, 8),   (3, 15)],
                [(5, 8),   (13, 15)],
                [(6, 8),   (14, 16)],
                [(4, 9),   (0, 14)],
                [(5, 9),   (3, 16)],
                [(6, 9),   (13, 17)],
                [(4, 10),  (0, 17)],
                [(5, 10),  (3, 17)],

                # Body Segment 2
                [(5, 6),   (5, 5)],
                [(6, 6),   (6, 5)],
                [(7, 6),   (7, 5)],
                [(8, 6),   (8, 5)],
                [(9, 6),   (9, 5)],
                [(10, 6),  (10, 5)],
                [(6, 7),   (5, 6)],
                [(7, 7),   (6, 6)],
                [(8, 7),   (7, 6)],
                [(9, 7),   (8, 6)],
                [(10, 7),  (9, 6)],
                [(11, 7),  (10, 6)],
                [(7, 8),   (5, 7)],
                [(8, 8),   (6, 7)],
                [(9, 8),   (7, 7)],
                [(10, 8),  (8, 7)],
                [(7, 9),   (5, 8)],
                [(8, 9),   (6, 8)],
                [(9, 9),   (7, 8)],

                # Body Segment 3
                [(12, 7),   (22, 5)],
                [(11, 8),   (22, 6)],
                [(12, 8),   (23, 6)],
                [(13, 8),   (24, 6)],
                [(10, 9),   (22, 7)],
                [(11, 9),   (23, 7)],
                [(12, 9),   (24, 7)],
                [(13, 9),   (25, 5)],
                [(14, 9),   (26, 5)],
                [(10, 10),  (23, 7)],
                [(11, 10),  (24, 7)],
                [(12, 10),  (25, 7)],
                [(13, 10),  (26, 6)],
                [(14, 10),  (27, 5)],
                [(12, 11),  (26, 7)],
                [(13, 11),  (27, 7)],

                # Hairs
                [(2, 6), (23, 17)],
                [(1, 5), (23, 16)],

                [(4, 5), (23, 17)],
                [(3, 4), (23, 16)],

                [(7, 5), (23, 10)],
                [(7, 4), (23, 9)],

                [(10, 5), (23, 10)],
                [(11, 4), (23, 9)],

                [(13, 6), (3, 19)],
                [(14, 5), (3, 18)],
            ]

            for pixel in pixel_map:
                item_image.putpixel(pixel[0], entity_image.getpixel(pixel[1]))

            self._save_image(self.config.CATERPILLAR_ITEM_TEXTURE_PATH / os.path.basename(texture), item_image)

    def _load_butterfly_entity_texture(self, f):
        image = self._load_image(f)

        # Is this a hummingbird moth?
        if any(moth in str(f) for moth in self.HUMMINGBIRD_MOTHS):

            # Get the wing texture
            image = self._crop_image(image, (0, 9), (5, 6))

            # Create a mirror image of the texture
            reflected_image = image.transpose(Image.FLIP_LEFT_RIGHT)

            # Create a new image with both wings
            new_image = Image.new('RGBA', (image.width * 2, image.height))
            new_image.paste(image, (0, 0))
            new_image.paste(reflected_image, (image.width, 0))

            # Rotate the image so it is facing the same way as other butterfly
            # images
            return self._rotate_image(new_image, -90)
        else:

            # Not a hummingbird, just extract the wings
            return self._crop_image(image, (10, 0), (17, 20))

    def generate_textures(self) -> None:
        """
        Generate all derivative textures.
        """

        self._generate_caterpillars()

        butterfly_entity_textures = [
            [f, self._load_butterfly_entity_texture(f)]
            for f in self.config.BUTTERFLY_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("butterfly_")
        ]

        caterpillar_item_textures = [
            [f, self._load_image(f)]
            for f in self.config.CATERPILLAR_ITEM_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("caterpillar_")
        ]

        bottle_image = self._load_image(self.config.GLASS_BOTTLE_TEXTURE_PATH)

        self._generate_spawn_eggs(butterfly_entity_textures)
        self._generate_bottled_butterfly(bottle_image, butterfly_entity_textures)
        self._generate_bottled_caterpillar(bottle_image, caterpillar_item_textures)
        self._generate_butterfly_scroll(butterfly_entity_textures)
        self._generate_scroll_gui()