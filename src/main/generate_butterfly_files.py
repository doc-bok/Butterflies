import json
import os
import pathlib
import shutil

# List of files to generate json files for.
COLORS = [
    'black',
    'blue',
    'brown',
    'cyan',
    'gray',
    'green',
    'light_blue',
    'light_gray',
    'lime',
    'magenta',
    'orange',
    'pink',
    'purple',
    'red',
    'white',
    'yellow'
]

FLOWERS = [
    'allium',
    'azure_bluet',
    'blue_orchid',
    'cornflower',
    'dandelion',
    'lily_of_the_valley',
    'orange_tulip',
    'oxeye_daisy',
    'pink_tulip',
    'poppy',
    'red_tulip',
    'white_tulip',
    'wither_rose'
]

# File locations
ACHIEVEMENTS = "resources/data/butterflies/advancement/butterfly/"
BIOME_MODIFIERS = "resources/data/butterflies/neoforge/biome_modifier/"
BUTTERFLY_DATA = "resources/data/butterflies/butterfly_data/"
CODE_GENERATION = "java/com/bokmcdok/butterflies/world/ButterflySpeciesList.java"
FROG_FOOD = "resources/data/minecraft/tags/entity_type/frog_food.json"
LOCALISATION = "resources/assets/butterflies/lang/en_us.json"
BUTTERFLY_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/butterfly/"
MALE_BUTTERFLY_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/butterfly_male/"
MOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/moth/"
MALE_MOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/moth_male/"
BOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/both/"
BIOME_MODIFIER_TEMPLATES = "resources/data/butterflies/templates/biome_modifiers/"

BUTTERFLIES_FOLDER = "butterflies/"
MALE_BUTTERFLIES_FOLDER = "butterflies/male/"
MOTHS_FOLDER = "moths/"
MALE_MOTHS_FOLDER = "moths/male/"
SPECIAL_FOLDER = "special/"


# Initial index
BUTTERFLY_INDEX = 0


# Generates a list of butterflies in a folder.
def generate_butterfly_list(folder):
    return generate_file_list(BUTTERFLY_DATA + folder)


# Generates a list of files in a folder.
def generate_file_list(folder):
    print(f"Generating file list for folder [{folder}]")
    result = []
    for (path, _, filenames) in os.walk(folder):
        result = [f.replace(".json", "") for f in filenames if f.endswith(".json")]
        break

    print(f"Result: {repr(result)}")
    return result


# Generates data files from the input array based on the first entry in the#
# array.
def generate_data_files(entries):
    print("Generating data files...")

    # Get list of files containing input[0]
    files = []
    for (path, _, filenames) in os.walk(os.getcwd()):

        # We only want json
        filenames = [f for f in filenames if f.endswith(".json") and entries[0] in f]

        if "loot_table" not in path:
            for name in filenames:
                files.append(pathlib.Path(path, name))

    # Loop Start
    for entry in entries:

        for file in files:

            # Get the new filename
            new_file = pathlib.Path(str(file).replace(entries[0], entry))

            if entry != entries[0]:

                # Check if the file exists
                if not new_file.is_file():

                    # Create the new file if it doesn't exist
                    shutil.copy(file, new_file)

                    # Read in the new file
                    with open(new_file, 'r') as input_file:
                        file_data = input_file.read()

                    # Replace the butterfly species
                    file_data = file_data.replace(entries[0], entry)

                    # Write the file out again
                    with open(new_file, 'w') as output_file:
                        output_file.write(file_data)

            with open(new_file, 'r', encoding="utf8") as input_file:
                json_data = json.load(input_file)

            global BUTTERFLY_INDEX
            if "index" in json_data:
                json_data["index"] = BUTTERFLY_INDEX
                BUTTERFLY_INDEX = BUTTERFLY_INDEX + 1

            if "entityId" in json_data:
                json_data["entityId"] = entry

            with open(new_file, 'w', encoding="utf8") as new_file:
                new_file.write(json.dumps(json_data,
                                          default=lambda o: o.__dict__,
                                          sort_keys=True,
                                          indent=2))


# Frog food class used to generate JSON.
class FrogFood(object):
    replace = False
    values = []

    # Initialise with a set of values.
    def __init__(self, values):
        self.values = values
        self.replace = False

    # Convert the class to a JSON string.
    def to_json(self):
        return json.dumps(
            self,
            default=lambda o: o.__dict__,
            sort_keys=True,
            indent=2)


# Generate list of entities to add to frog food.
def generate_frog_food(species):
    print("Generating frog food...")

    values = ["butterflies:" + i for i in species]
    frog_food = FrogFood(values)

    with open(FROG_FOOD, 'w') as file:
        file.write(frog_food.to_json())


# Add a value to the specified JSON data, but only if the key doesn't already
# exist.
def try_add_localisation_string(json_data, key, value):
    if key not in json_data:
        json_data[key] = value


# Generates localisation strings if they don't already exist.
def generate_localisation_strings(all_butterflies, all_moths):
    print("Generating localisation strings...")

    with open(LOCALISATION, 'r', encoding="utf8") as input_file:
        json_data = json.load(input_file)

    for i in all_butterflies:
        name = i.replace('_', ' ')
        name = i.replace('-', ' ')
        name = name.title()
        try_add_localisation_string(json_data, "entity.butterflies." + i, name + " Butterfly")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_caterpillar", name + " Caterpillar")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_chrysalis", name + " Chrysalis")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_egg", name + " Butterfly Egg")
        try_add_localisation_string(json_data, "item.butterflies." + i, name + " Butterfly")
        try_add_localisation_string(json_data, "item.butterflies." + i + "_egg", name + " Butterfly Egg")
        try_add_localisation_string(json_data, "item.butterflies." + i + "_caterpillar", name + " Caterpillar")

    for i in all_moths:
        name = i.replace('_', ' ')
        name = i.replace('-', ' ')
        name = name.title()
        try_add_localisation_string(json_data, "entity.butterflies." + i, name + " Moth")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_caterpillar", name + " Larva")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_chrysalis", name + " Cocoon")
        try_add_localisation_string(json_data, "entity.butterflies." + i + "_egg", name + " Moth Egg")
        try_add_localisation_string(json_data, "item.butterflies." + i, name + " Moth")
        try_add_localisation_string(json_data, "item.butterflies." + i + "_egg", name + " Moth Egg")
        try_add_localisation_string(json_data, "item.butterflies." + i + "_caterpillar", name + " Larva")

    for i in all_butterflies + all_moths:
        try_add_localisation_string(json_data, "gui.butterflies.fact." + i, "")

    with open(LOCALISATION, 'w', encoding="utf8") as file:
        file.write(json.dumps(json_data,
                              default=lambda o: o.__dict__,
                              sort_keys=True,
                              indent=2))


# Generates advancements based on templates that can be found in the specified#
# location.
def generate_advancements(entities, templates):
    print("Generating advancements...")

    files = []
    for (_, _, filenames) in os.walk(templates):
        files.extend(filenames)
        break

    for file in files:
        with open(templates + file, 'r', encoding="utf8") as input_file:
            json_data = json.load(input_file)

        for butterfly in entities:
            json_data["criteria"][butterfly] = {
                "trigger": "minecraft:inventory_changed",
                "conditions": {
                    "items": [
                        {
                            "items": [
                                json_data["base_item"] + butterfly + json_data["item_ext"]
                            ]
                        }
                    ]
                }
            }

            if json_data["requires_all"]:
                json_data["requirements"].append([butterfly])

        if not json_data["requires_all"]:
            json_data["requirements"] = [entities]

        # Remove the extra keys to avoid errors.
        json_data.pop("base_item", None)
        json_data.pop("requires_all", None)
        json_data.pop("item_ext", None)

        with open(ACHIEVEMENTS + file, 'w', encoding="utf8") as output_file:
            output_file.write(json.dumps(json_data,
                                         default=lambda o: o.__dict__,
                                         sort_keys=True,
                                         indent=2))


# Generates a Java file with an array containing every species. This array is
# then used to create all the entities and items related to each butterfly,
# saving a ton of new code needing to be written every time we add a new
# butterfly or moth.
def generate_code(all):
    print("Generating code...")

    with open(CODE_GENERATION, 'w', encoding="utf8") as output_file:
        output_file.write("""package com.bokmcdok.butterflies.world;

/**
 * Generated code - do not modify
 */
public class ButterflySpeciesList {
    public static final String[] SPECIES = {
""")

        for butterfly in all:
            output_file.write("""            \"""" + butterfly + """\",
""")

        output_file.write("""    };
}
""")


# Generates placeholder textures
def generate_textures(entries, base):
    print("Generating textures...")

    # Get list of files containing input[0]
    files = []
    for (path, _, filenames) in os.walk(os.getcwd()):

        # We only want json
        filenames = [f for f in filenames if f.endswith(".png") and base in f]

        for name in filenames:
            files.append(pathlib.Path(path, name))

    # Loop Start
    for entry in entries:

        for file in files:

            # Get the new filename
            new_file = pathlib.Path(str(file).replace(base, entry))

            # Create the new file if it doesn't exist
            if entry != base:
                if not new_file.is_file():
                    shutil.copy(file, new_file)


# Reset the biome modifiers ready for generation.
def reset_biome_modifiers():
    for (_, _, files) in os.walk(BIOME_MODIFIER_TEMPLATES):
        for file_ in files:
            src_file = os.path.join(BIOME_MODIFIER_TEMPLATES, file_)
            dst_file = os.path.join(BIOME_MODIFIERS, file_)
            if os.path.exists(dst_file):
                # in case of the src and dst are the same file
                if os.path.samefile(src_file, dst_file):
                    continue
                os.remove(dst_file)
            shutil.copy(src_file, BIOME_MODIFIERS)


# Generate biome modifiers based on rarity and habitat
def generate_biome_modifiers(list, folder, is_male):
    print("Generating biome modifiers...")

    # Iterate over butterflies
    for i in list:
        addSpawns(folder, i, is_male)


# Add a group of spawns for a single butterfly.
def addSpawns(folder, species, is_male):

    # Open Butterfly data
    with open(BUTTERFLY_DATA + folder + species + ".json", 'r', encoding="utf8") as input_file:
        json_data = json.load(input_file)

    rarity = json_data["rarity"]
    habitat = json_data["habitat"]

    # Generate weights/min/max
    if (rarity == "common"):
        weight = 100
        maximum = 4

    elif (rarity == "uncommon"):
        weight = 50
        maximum = 3

    else:
        weight = 20
        maximum = 2

    # Open relevant files and add butterfly spawns
    if "forests" in habitat:
        addSingleSpawn("cherry_grove", species, weight, maximum, is_male)
        addSingleSpawn("dense", species, weight, maximum, is_male)
        addSingleSpawn("forest", species, weight, maximum, is_male)
        addSingleSpawn("lush", species, weight, maximum, is_male)
        addSingleSpawn("taiga", species, weight, maximum, is_male)

    if "hills" in habitat:
        addSingleSpawn("hill", species, weight, maximum, is_male)

    if "ice" in habitat:
        addSingleSpawn("snowy", species, weight, maximum, is_male)
        addSingleSpawn("taiga", species, weight, maximum, is_male)

    if "jungles" in habitat:
        addSingleSpawn("jungle", species, weight, maximum, is_male)

    if "nether" in habitat:
        addSingleSpawn("nether", species, weight, maximum, True)

    if "plains" in habitat:
        addSingleSpawn("plains", species, weight, maximum, is_male)

    if "plateaus" in habitat:
        addSingleSpawn("lush", species, weight, maximum, is_male)
        addSingleSpawn("plateau", species, weight, maximum, is_male)

    if "savanna" in habitat:
        addSingleSpawn("savanna", species, weight, maximum, is_male)

    if "villages" in habitat:
        addSingleSpawn("village_desert", species, weight, maximum, is_male)
        addSingleSpawn("village_plains", species, weight, maximum, is_male)
        addSingleSpawn("village_savanna", species, weight, maximum, is_male)
        addSingleSpawn("village_snowy", species, weight, maximum, is_male)
        addSingleSpawn("village_taiga", species, weight, maximum, is_male)

    if "wetlands" in habitat:
        addSingleSpawn("mushroom", species, weight, maximum, is_male)
        addSingleSpawn("river", species, weight, maximum, is_male)
        addSingleSpawn("swamp", species, weight, maximum, is_male)


# Add a single spawn for a butterfly.
def addSingleSpawn(tag, species, weight, maximum, is_male):
    target_file = BIOME_MODIFIERS + tag + "_butterflies.json"

    with open(target_file, 'r', encoding="utf8") as input_file:
        json_data = json.load(input_file)

    json_data["spawners"].append({
        "type" : "butterflies:" + species,
        "weight" : weight,
        "minCount" : 1,
        "maxCount" : maximum
    })

    if not is_male:
        json_data["spawners"].append({
            "type" : "butterflies:" + species + "_caterpillar",
            "weight" : weight,
            "minCount" : 1,
            "maxCount" : maximum
        })

        json_data["spawners"].append({
            "type" : "butterflies:" + species + "_chrysalis",
            "weight" : weight,
            "minCount" : 1,
            "maxCount" : maximum
        })

        json_data["spawners"].append({
            "type" : "butterflies:" + species + "_egg",
            "weight" : weight,
            "minCount" : 1,
            "maxCount" : maximum
        })

    with open(target_file, 'w', encoding="utf8") as output_file:
        output_file.write(json.dumps(json_data,
                                     default=lambda o: o.__dict__,
                                     sort_keys=True,
                                     indent=2))


# Use to generate missing item models.
def generate_item_models():
    files = generate_file_list("resources/assets/butterflies/models/items/")
    for file in files:
        with open("resources/assets/butterflies/items/" + file + ".json", 'w+', encoding="utf8") as output_file:
            output_file.write("""{
  "model": {
    "model": "butterflies:item/""" + file + """",
    "type": "minecraft:model"
  }
}
""")


# Python's main entry point
if __name__ == "__main__":
    # Get the species lists
    butterflies = generate_butterfly_list(BUTTERFLIES_FOLDER)
    male_butterflies = generate_butterfly_list(MALE_BUTTERFLIES_FOLDER)
    moths = generate_butterfly_list(MOTHS_FOLDER)
    male_moths = generate_butterfly_list(MALE_MOTHS_FOLDER)
    special = generate_butterfly_list(SPECIAL_FOLDER)

    all = butterflies + male_butterflies + moths + male_moths + special
    all_butterflies = butterflies + male_butterflies
    all_moths = moths + male_moths

    # Generate the data files
    generate_data_files(butterflies)
    generate_data_files(male_butterflies)
    generate_data_files(moths)
    generate_data_files(male_moths)
    generate_data_files(special)

    #generate_textures(BUTTERFLIES, "clipper") # Change this to use a different base for textures
    #generate_data_files(FLOWERS) # Disabled for now due to tulip problem
    #generate_data_files(COLORS)

    generate_frog_food(all)
    generate_localisation_strings(all_butterflies + special, all_moths)

    # Generate the advancements
    generate_advancements(butterflies, BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    generate_advancements(all_butterflies, MALE_BUTTERFLY_ACHIEVEMENT_TEMPLATES)
    generate_advancements(moths, MOTH_ACHIEVEMENT_TEMPLATES)
    generate_advancements(all_moths, MALE_MOTH_ACHIEVEMENT_TEMPLATES)
    generate_advancements(butterflies + moths, BOTH_ACHIEVEMENT_TEMPLATES)

    generate_code(all)

    # Generate biome modifiers.
    reset_biome_modifiers()
    generate_biome_modifiers(butterflies, BUTTERFLIES_FOLDER, False)
    generate_biome_modifiers(male_butterflies, MALE_BUTTERFLIES_FOLDER, True)
    generate_biome_modifiers(moths, MOTHS_FOLDER, False)
    generate_biome_modifiers(male_moths, MALE_MOTHS_FOLDER, True)
    generate_biome_modifiers(special, SPECIAL_FOLDER, False)

