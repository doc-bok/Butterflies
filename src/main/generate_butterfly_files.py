import json
import os
import pathlib
import shutil

# The list of butterfly species included in the mod.
BUTTERFLIES = [
    'admiral', 
    'buckeye',
    'cabbage',
    'chalkhill',
    'clipper',
    'common',
    'emperor',
    'forester',
    'glasswing',
    'hairstreak',
    'heath',
    'longwing',
    'monarch',
    'morpho',
    'rainbow',
    'swallowtail',
    'peacock'
]

# List of files to generate json files for.
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
FROG_FOOD = "resources/data/minecraft/tags/entity_types/frog_food.json"
LOCALISATION = "resources/assets/butterflies/lang/en_us.json"
ACHIEVEMENTS = "resources/data/butterflies/advancements/butterfly/"
ACHIEVEMENT_TEMPLATES = "resources/data/butterflies/advancements/templates/"
CODE_GENERATION = "java/com/bokmcdok/butterflies/world/ButterflySpeciesList.java"


def generate_data_files(input):
    # Get list of files containing input[0]
    files = []
    for (path, _, filenames) in os.walk(os.getcwd()):

        # We only want json
        filenames = [f for f in filenames if f.endswith(".json") and input[0] in f]

        for name in filenames:
            files.append(pathlib.Path(path, name))

    # Loop Start
    for entry in input:

        # We don't need to do this for the template
        if entry == input[0]:
            continue

        for file in files:

            # Get the new filename
            new_file = pathlib.Path(str(file).replace(input[0], entry))

            # Check if the file exists
            if not new_file.is_file():

                # Create the new file if it doesn't exist
                shutil.copy(file, new_file)

                # Read in the new file
                with open(new_file, 'r') as input_file:
                    file_data = input_file.read()

                # Replace the butterfly species
                file_data = file_data.replace(input[0], entry)

                # Write the file out again
                with open(new_file, 'w') as output_file:
                    output_file.write(file_data)


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
def generate_frog_food():
    values = []
    for i in BUTTERFLIES:
        values.append("butterflies:" + i)

    print(values)

    frog_food = FrogFood(values)

    with open(FROG_FOOD, 'w') as file:
        file.write(frog_food.to_json())


# Generates localisation strings if they don't already exist.
def generate_localisation_strings():
    with open(LOCALISATION, 'r', encoding="utf8") as input_file:
        json_data = json.load(input_file)

    for i in BUTTERFLIES:
        test_key = "item.butterflies." + i + "_egg"
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Butterfly Egg"

        test_key = "entity.butterflies." + i
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Butterfly"

        test_key = "entity.butterflies." + i + "_caterpillar"
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Caterpillar"

        test_key = "item.butterflies." + i
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Butterfly"

        test_key = "item.butterflies." + i + "_caterpillar"
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Caterpillar"

        test_key = "entity.butterflies." + i + "_chrysalis"
        if test_key not in json_data:
            json_data[test_key] = i.capitalize() + " Chrysalis"

        test_key = "gui.butterflies.fact." + i
        if test_key not in json_data:
            json_data[test_key] = ""

    with open(LOCALISATION, 'w', encoding="utf8") as file:
        file.write(json.dumps(json_data,
                              default=lambda o: o.__dict__,
                              sort_keys=True,
                              indent=2))


def generate_advancements():
    files = []
    for (_, _, filenames) in os.walk(ACHIEVEMENT_TEMPLATES):
        files.extend(filenames)
        break

    for file in files:
        with open(ACHIEVEMENT_TEMPLATES + file, 'r', encoding="utf8") as input_file:
            json_data = json.load(input_file)

        for butterfly in BUTTERFLIES:
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
            json_data["requirements"] = [BUTTERFLIES]

        # Remove the extra keys to avoid errors.
        json_data.pop("base_item", None)
        json_data.pop("requires_all", None)
        json_data.pop("item_ext", None)

        with open(ACHIEVEMENTS + file, 'w', encoding="utf8") as output_file:
            output_file.write(json.dumps(json_data,
                                         default=lambda o: o.__dict__,
                                         sort_keys=True,
                                         indent=2))


def generate_code():
    with open(CODE_GENERATION, 'w', encoding="utf8") as output_file:
        output_file.write("""package com.bokmcdok.butterflies.world;

/**
 * Generated code - do not modify
 */
public class ButterflySpeciesList {
    public static final String[] SPECIES = {
""")

        for butterfly in BUTTERFLIES:
            output_file.write("""            \"""" + butterfly + """\",
""")

        output_file.write("""    };
}
""")




# Python's main entry point
if __name__ == "__main__":
    generate_data_files(BUTTERFLIES)
    # generate_data_files(FLOWERS) # Disabled for now due to tulip problem
    generate_frog_food()
    generate_localisation_strings()
    generate_advancements()
    generate_code()
