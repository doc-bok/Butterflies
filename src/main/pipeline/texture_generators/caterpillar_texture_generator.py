import os

from PIL import Image

from pipeline.config import Config
from pipeline.images.image_file_handler import ImageFileHandler

class CaterpillarTextureGenerator:
    """
    Generator for Caterpillar Item Textures.
    """

    def __init__(self, config: Config, image_file_handler: ImageFileHandler):
        self.config = config
        self.logger = config.logger
        self.image_file_handler = image_file_handler

    def generate(self) -> None:
        self.logger.info(f"Generating caterpillar item textures")

        caterpillar_entity_textures = [
            f for f in self.config.CATERPILLAR_ENTITY_TEXTURE_PATH.iterdir()
            if f.suffix == ".png" and os.path.basename(f).startswith("caterpillar_")
        ]

        for texture in caterpillar_entity_textures:
            entity_image = self.image_file_handler.load_image(texture)
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

            self.image_file_handler.save_image(self.config.CATERPILLAR_ITEM_TEXTURE_PATH / os.path.basename(texture), item_image)