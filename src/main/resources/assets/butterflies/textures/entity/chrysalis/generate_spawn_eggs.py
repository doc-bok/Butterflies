import os
from PIL import Image

def overlay_images(base_path, overlay_path, output_path, crop_size=(16, 16), offset=(0, 0), scale=1.0):
    base_img = Image.open(base_path).convert('RGBA')
    overlay_img = Image.open(overlay_path).convert('RGBA')

    # Crop the image
    overlay_cropped = overlay_img.crop((0, 0, crop_size[0], crop_size[1]))

    # Offset the image
    overlay_offset = Image.new('RGBA', base_img.size, (0, 0, 0, 0))
    overlay_offset.paste(overlay_cropped, offset, overlay_cropped)

    # Resize the image
    new_width = int(base_img.width * scale)
    new_height = int(base_img.height * scale)
    temp_overlay = overlay_offset.resize((new_width, new_height), Image.Resampling.LANCZOS)

    overlay_resized = Image.new('RGBA', base_img.size, (0,0,0,0))
    position = ((base_img.width - new_width) // 2, (base_img.height - new_height) // 2)
    overlay_resized.paste(temp_overlay, position, temp_overlay)

    # Ensure the overlay fits
    if overlay_resized.size > base_img.size:
        overlay_resized = overlay_cropped.resize(base_img.size, Image.Resampling.LANCZOS)

    # Save the image
    combined = Image.alpha_composite(base_img, overlay_resized)
    combined.save(output_path)

for x in os.listdir():
    if x.endswith(".png") and x.startswith("chrysalis_"):
        overlay_images('spawn_egg.png', x, '../../item/spawn_egg/chrysalis/' + x[10:], offset=(4, 1), scale=0.8)