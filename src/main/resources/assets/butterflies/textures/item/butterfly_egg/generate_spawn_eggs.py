import os
from PIL import Image

def overlay_shrunk_image(base_path, overlay_path, output_path, shrink_factor=1.0):
    base_img = Image.open(base_path).convert('RGBA')
    overlay_img = Image.open(overlay_path).convert('RGBA')
    new_width = int(base_img.width * shrink_factor)
    new_height = int(base_img.height * shrink_factor)
    overlay_shrunk = overlay_img.resize((new_width, new_height), Image.Resampling.LANCZOS)
    temp_overlay = Image.new('RGBA', base_img.size, (0,0,0,0))
    position = ((base_img.width - new_width) // 2, (base_img.height - new_height) // 2)
    temp_overlay.paste(overlay_shrunk, position, overlay_shrunk)
    combined = Image.alpha_composite(base_img, temp_overlay)
    combined.save(output_path)

for x in os.listdir():
    if x.endswith('_egg.png') and x != 'spawn_egg.png':
        overlay_shrunk_image('spawn_egg.png', x, '../spawn_egg/egg/' + x[:-8] + '.png', 0.8)