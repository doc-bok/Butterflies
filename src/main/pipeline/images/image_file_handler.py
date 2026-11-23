import os

from pathlib import Path
from PIL import Image

class ImageFileHandler:
    """
    Contains methods to help when manipulating image files.
    """

    def load_image(self, image_path: Path) -> Image.Image:
        """
        Loads an image from the given path.
        :param image_path: The path of the image to load.
        :return: The loaded image data.
        """
        try:
            with Image.open(image_path) as img:
                img.load()
                return img.copy()
        except OSError as e:
            self.logger.error(f"Could not open image {image_path}: {e}")
            raise

    def save_image(self, image_path: Path, image: Image.Image) -> None:
        """
        Saves the image to the path, optimizing PNG files.
        :param image_path: The path to save the file.
        :param image: The image data to save.
        """
        try:
            suffix = image_path.suffix.lstrip(".").upper()
            image.save(image_path, format="PNG", optimize=True)
        except Exception as e:
            self.logger.error(f"Failed to save image {image_path}: {e}")
            raise

    def resize_image(self,
            image: Image.Image,
            new_width: int,
            new_height: int,
            resampling: Image.Resampling
    ) -> Image.Image:
        """
        Resizes the given image.
        :param image: The image to resize.
        :param new_width: The new width of the image.
        :param new_height: The new height of the image.
        :param resampling: The resampling method to use.
        :return: A new, resized image.
        """
        return image.resize((new_width, new_height), resampling)

    def combine_images(self,
            base_image: Image.Image,
            overlay_image: Image.Image,
            offset: tuple[int, int] = (0, 0)
    ) -> Image.Image:
        """
        Combine the given images and return the combined image.
        :param base_image: The image to use as the base.
        :param overlay_image: The image to overlay on top.
        :param offset: The offset of the overlay image.
        :return A new image that combines the input images.
        """

        # Ensure we are using RGBA in our images.
        if base_image.mode != 'RGBA':
            base_image = base_image.convert('RGBA')

        if overlay_image.mode != 'RGBA':
            overlay_image = overlay_image.convert('RGBA')

        # Ensure the overlay fits
        if overlay_image.width > base_image.width or overlay_image.height > base_image.height:
            overlay_resized = overlay_image.resize(base_image.size, Image.Resampling.LANCZOS)
        else:
            overlay_resized = overlay_image

        offset_x, offset_y = offset
        position_x = (base_image.width - overlay_image.width) // 2 + offset_x
        position_y = (base_image.height - overlay_image.height) // 2 + offset_y

        if position_x < 0 or position_y < 0:
            raise ValueError(
                f"Overlay position ({position_x}, {position_y}) is outside "
                f"the base image"
            )

        # Offset the image
        overlay_final = Image.new('RGBA', base_image.size, (0, 0, 0, 0))
        overlay_final.paste(overlay_resized, (position_x, position_y), overlay_resized)

        return Image.alpha_composite(base_image, overlay_final)

    def crop_image(self,
            image: Image.Image,
            crop_start: tuple[int, int],
            crop_size: tuple[int, int]
    ) -> Image.Image:
        """
        Crop the given image and return the cropped image.
        :param image: The image to crop.
        :param crop_start: The coordinates to start the crop.
        :param crop_size: The size of the crop.
        :return: A newly cropped image.
        """
        left, top = crop_start
        width, height = crop_size
        right = left + width
        bottom = top + height

        if left < 0 or top < 0:
            raise ValueError(
                f"Crop origin must be non-negative: got ({left}, {top})"
            )

        if width <= 0 or height <= 0:
            raise ValueError(
                f"Crop size must be positive: got {width}x{height}"
            )

        if right > image.width or bottom > image.height:
            raise ValueError(
                f"Crop rectangle ({left}, {top}, {right}, {bottom}) "
                f"exceeds image bounds {image.width}x{image.height}"
            )

        return image.crop((left, top, right, bottom))

    def rotate_image(self, image: Image.Image, rotate: int) -> Image.Image:
        """
        Rotate the given image and return the rotated image.
        :param image: The image to rotate.
        :param rotate: The amount to rotate, in degrees.
        :return: A newly rotated image.
        """
        return image.rotate(rotate, expand=True)