import os
from PIL import Image

def overlay_images(base_path, overlay_path, output_path):
    base_img = Image.open(base_path).convert('RGBA')
    overlay_img = Image.open(overlay_path).convert('RGBA')
    if overlay_img.size != base_img.size:
        overlay_img = overlay_img.resize(base_img.size, Image.ANTIALIAS)
    combined = Image.alpha_composite(base_img, overlay_img)
    combined.save(output_path)

for x in os.listdir():
    if x.endswith(".png") and x.startswith("caterpillar_"):
        overlay_images('spawn_egg.png', x, '../spawn_egg/caterpillar/' + x[12:])