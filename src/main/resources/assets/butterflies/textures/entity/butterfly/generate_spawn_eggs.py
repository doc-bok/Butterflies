import os
from PIL import Image

def overlay_images(base_path, overlay_path, output_path, crop_start=(0, 0), crop_size=(16, 16), offset=(0, 0), scale=1.0, rotate=0):
    base_img = Image.open(base_path).convert('RGBA')
    overlay_img = Image.open(overlay_path).convert('RGBA')

    # Crop the image
    overlay_cropped = overlay_img.crop((crop_start[0], crop_start[1], crop_start[0] + crop_size[0], crop_start[1] + crop_size[1]))

    # Rotate the image
    overlay_rotated = overlay_cropped.rotate(rotate, expand=True)

    # Resize the image
    new_width = int(base_img.width * scale)
    new_height = int(base_img.height * scale)
    overlay_resized = overlay_rotated.resize((new_width, new_height), Image.Resampling.LANCZOS)

    # Ensure the overlay fits
    if overlay_resized.size > base_img.size:
        new_width = base_img.width
        new_height = base_img.height
        overlay_resized = overlay_resized.resize(base_img.size, Image.Resampling.LANCZOS)

    position = (((base_img.width - new_width) // 2) + offset[0], ((base_img.height - new_height) // 2) + offset[1])

    # Offset the image
    overlay_final = Image.new('RGBA', base_img.size, (0, 0, 0, 0))
    overlay_final.paste(overlay_resized, position, overlay_resized)

    # Save the image
    combined = Image.alpha_composite(base_img, overlay_final)
    combined.save(output_path)

for x in os.listdir():
    if x.endswith(".png") and x.startswith("butterfly_"):
        overlay_images(
            'spawn_egg.png', x, '../../item/spawn_egg/butterfly/' + x[10:],
            crop_start=(10, 0),
            crop_size=(17, 20),
            rotate=90,
            scale=0.8,
            offset=(0, 1))