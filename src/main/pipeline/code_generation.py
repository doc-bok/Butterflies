# code_generation.py
import json
from pathlib import Path
from typing import List, Optional
from .config import Config

class CodeGenerator:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    def find_traits_for_species(self, species: str) -> Optional[List[str]]:
        """
        Search the species JSON files across known folders and return its traits list.
        Return None if no JSON file found for species.
        """
        for folder in self.config.FOLDERS:
            json_path = self.config.BUTTERFLY_DATA / folder / f"{species}.json"
            if json_path.exists():
                with open(json_path, encoding="utf8") as f:
                    data = json.load(f)
                return data.get("traits", [])
        self.logger.warning(f"Traits not found for species: {species}")
        return None

    def generate_code(self, all_species: List[str]) -> None:
        """
        Generate the Java source file ButterflyInfo.java containing
        species arrays and traits arrays used by your mod code.
        """
        self.logger.info(f"Generating Java code file at {self.config.CODE_GENERATION}")

        with open(self.config.CODE_GENERATION, "w", encoding="utf8") as out:
            # Write the java package and class header
            out.write(
                "package com.bokmcdok.butterflies.world;\n\n"
                "/**\n"
                " * Generated code - do not modify\n"
                " */\n"
                "public class ButterflyInfo {\n"
                "    public static final String[] SPECIES = {\n"
            )

            # Write species array
            for name in all_species:
                out.write(f'            "{name}",\n')
            out.write("    };\n\n")

            # Write traits array
            out.write("    // A list of traits each butterfly has.\n")
            out.write("    public static final ButterflyData.Trait[][] TRAITS = {\n")

            for species in all_species:
                traits = self.find_traits_for_species(species)
                if traits is None:
                    out.write(f"        {{}}, // Missing traits for species: {species}\n")
                    continue
                out.write("        {\n")
                for trait in traits:
                    out.write(f"            ButterflyData.Trait.{trait.upper()},\n")
                out.write("        },\n")

            out.write("    };\n")
            out.write("}\n")

        self.logger.info("Java code generation complete.")
