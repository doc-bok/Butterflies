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
ACHIEVEMENTS = "resources/data/butterflies/advancements/butterfly/"
BUTTERFLY_DATA = "resources/data/butterflies/butterfly_data/"
CODE_GENERATION = "java/com/bokmcdok/butterflies/world/ButterflyInfo.java"
FROG_FOOD = "resources/data/minecraft/tags/entity_types/frog_food.json"
LOCALISATION = "resources/assets/butterflies/lang/en_us.json"
BUTTERFLY_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/butterfly/"
MALE_BUTTERFLY_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/butterfly_male/"
MOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/moth/"
MALE_MOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/moth_male/"
BOTH_ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/templates/advancements/both/"

BUTTERFLIES_FOLDER = "butterflies/"
MALE_BUTTERFLIES_FOLDER = "butterflies/male/"
MOTHS_FOLDER = "moths/"
MALE_MOTHS_FOLDER = "moths/male/"
SPECIAL_FOLDER = "special/"


# Initial index
BUTTERFLY_INDEX = 0

def generate_butterfly_list(folder):
    print(f"Generating species list for folder [{BUTTERFLY_DATA + folder}]")
    result = []
    for (path, _, filenames) in os.walk(BUTTERFLY_DATA + folder):
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

        if "loot_tables" not in path:
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

    folders = [BUTTERFLIES_FOLDER, MALE_BUTTERFLIES_FOLDER, MOTHS_FOLDER, MALE_MOTHS_FOLDER, SPECIAL_FOLDER]
    values = []

    # Iterate over so we can exclude inedible butterflies.
    for butterfly in species:
        for i in range(len(folders)):
            folder = folders[i]
            try:
                with open(BUTTERFLY_DATA + folder + butterfly + ".json", 'r', encoding="utf8") as input_file:
                    json_data = json.load(input_file)

                # Check for the inedible trait before we add it.
                traits = json_data["traits"]
                if "inedible" not in traits:
                    values += ["butterflies:" + butterfly]

            except FileNotFoundError:
                # doesn't exist
                pass
            else:
                # exists
                pass


    #values = ["butterflies:" + i for i in species]
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
 * Generated code - do not modify.
 * Provides data that needs to be accessed before butterfly data files are
 * loaded.
 */
 
public class ButterflyInfo {

    // A list of all the species in the mod.
    public static final String[] SPECIES = {
""")

        for butterfly in all:
            output_file.write("""            \"""" + butterfly + """\",
""")
        output_file.write("""    };
""")

        output_file.write("""
    // A list of traits each butterfly has.
    public static final ButterflyData.Trait[][] TRAITS = {
""")

        for butterfly in all:
            folders = [BUTTERFLIES_FOLDER, MALE_BUTTERFLIES_FOLDER, MOTHS_FOLDER, MALE_MOTHS_FOLDER, SPECIAL_FOLDER]
            traits = None
            i = 0

            while traits is None and i < len(folders):
                folder = folders[i]
                try:
                    with open(BUTTERFLY_DATA + folder + butterfly + ".json", 'r', encoding="utf8") as input_file:
                        json_data = json.load(input_file)

                    traits = json_data["traits"]
                except FileNotFoundError:
                    # doesn't exist
                    pass
                else:
                    # exists
                    pass

                i = i + 1


            output_file.write("""        {
""")

            for trait in traits:
                output_file.write("""            ButterflyData.Trait.""" + trait.upper() + """,
""")
            output_file.write("""        },
""")

        output_file.write("""    };
""")

        output_file.write("""
    // A list of habitats butterflies can be found in.
    public static final ButterflyData.Habitat[][] HABITATS = {
""")

        for butterfly in all:
            folders = [BUTTERFLIES_FOLDER, MALE_BUTTERFLIES_FOLDER, MOTHS_FOLDER, MALE_MOTHS_FOLDER, SPECIAL_FOLDER]
            habitat = None
            i = 0

            while habitat is None and i < len(folders):
                folder = folders[i]
                try:
                    with open(BUTTERFLY_DATA + folder + butterfly + ".json", 'r', encoding="utf8") as input_file:
                            json_data = json.load(input_file)

                    habitats = json_data["habitats"]
                except FileNotFoundError:
                    # doesn't exist
                    pass
                else:
                    # exists
                    pass

                i = i + 1


            output_file.write("""        {
""")

            for habitat in habitats:
                output_file.write("""            ButterflyData.Habitat.""" + habitat.upper() + """,
""")
            output_file.write("""        },
""")
        output_file.write("""    };
""")
        output_file.write("""
    // A list of how rare each butterfly is.
    public static final ButterflyData.Rarity[] RARITIES = {
""")

        for butterfly in all:
            folders = [BUTTERFLIES_FOLDER, MALE_BUTTERFLIES_FOLDER, MOTHS_FOLDER, MALE_MOTHS_FOLDER, SPECIAL_FOLDER]
            rarity = None
            i = 0

            while rarity is None and i < len(folders):
                folder = folders[i]
                try:
                    with open(BUTTERFLY_DATA + folder + butterfly + ".json", 'r', encoding="utf8") as input_file:
                        json_data = json.load(input_file)

                    rarity = json_data["rarity"]
                except FileNotFoundError:
                    # doesn't exist
                    pass
                else:
                    # exists
                    pass

                i = i + 1

            output_file.write("""            ButterflyData.Rarity.""" + rarity.upper() + """,
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
