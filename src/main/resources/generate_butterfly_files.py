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
    'swallowtail'
]

# Generate butterfly data files that don't exist yet
def generate_butterfly_files():
    # Get list of files containing BUTTERFLIES[0]
    files = []
    for (path, _, filenames) in os.walk(os.getcwd()):
    
        # We only want json
        filenames = [ f for f in filenames if f.endswith(".json") and BUTTERFLIES[0] in f ]
        
        for name in filenames:
            files.append(pathlib.Path(path, name))
    
    # Loop Start
    for butterfly in BUTTERFLIES:
        
        # We don't need to do this for the template
        if butterfly == BUTTERFLIES[0]:
            continue
            
        for file in files:
            
            # Get the new filename
            newfile = pathlib.Path(str(file).replace(BUTTERFLIES[0], butterfly))
            
            # Check if the file exists            
            if not newfile.is_file():
                
                # Create the new file if it doesn't exist
                shutil.copy(file, newfile)
                
                # Read in the new file
                with open(newfile, 'r') as file:
                  filedata = file.read()

                # Replace the butterfly species
                filedata = filedata.replace(BUTTERFLIES[0], butterfly)

                # Write the file out again
                with open(newfile, 'w') as file:
                  file.write(filedata)

# Python's main entry point
if __name__ == "__main__":
    generate_butterfly_files()