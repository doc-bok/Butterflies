
import os

def delete_caterpillar_json_files(folder_path):
    for filename in os.listdir(folder_path):
        if filename.endswith('_caterpillar.json'):
            file_path = os.path.join(folder_path, filename)
            if os.path.isfile(file_path):
                os.remove(file_path)

delete_caterpillar_json_files("./")