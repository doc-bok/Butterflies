# code_generation.py
import json
from typing import Optional
from .config import Config

class CodeGenerator:
    def __init__(self, config: Config):
        self.config = config
        self.logger = config.logger

    def _load_species_data(self, species: str) -> Optional[dict]:
        """
        Attempts to load the JSON data for this species across known folders.
        Returns dict if found, None otherwise.
        """
        for folder in self.config.FOLDERS:
            json_path = self.config.BUTTERFLY_DATA / folder / f"{species}.json"
            if json_path.exists():
                try:
                    return json.loads(json_path.read_text(encoding="utf8"))
                except (OSError, json.JSONDecodeError) as e:
                    self.logger.error(f"Failed to read {json_path}: {e}")
                    return None
        return None


    def _write_enum_array(
            self,
            out,
            array_name,
            enum_type,
            all_species: list[str],
            fetcher,
            nested: bool = True,
            header_comment: str = ""):
        """
        Helper to write out data. Supports 1D or 2D arrays.
        """
        if header_comment:
            out.write(f"    // {header_comment}\n")

        if nested:
            out.write(f"    public static final {enum_type}[][] {array_name} = {{\n")
        else:
            out.write(f"    public static final {enum_type}[] {array_name} = {{\n")

        for species in all_species:
            values = fetcher(species)
            if not values:
                if nested:
                    out.write(f"        {{}}, // No {array_name.lower()} for {species}\n")
                else:
                    out.write(f"        {enum_type}.COMMON, // Missing {array_name.lower()} for {species}\n")
                continue

            if nested:
                out.write("        {\n")
                for v in values:
                    out.write(f"            {enum_type}.{v.upper()},\n")
                out.write("        },\n")
            else:
                out.write(f"            {enum_type}.{values.upper()},\n")

        out.write("    };\n\n")


    def generate_code(self, all_species: list[str]) -> None:
        """
        Generate the Java source file ButterflyInfo.java containing
        species arrays and traits arrays used by your mod code.
        """
        self.logger.info(f"Generating Java code file at {self.config.CODE_GENERATION}")

        # Pre-load butterfly data.
        species_data = {s: self._load_species_data(s) or {} for s in all_species}

        with open(self.config.CODE_GENERATION, "w", encoding="utf8") as out:
            # Write the java package and class header
            out.write(
                "package com.bokmcdok.butterflies.world;\n\n"
                "/**\n"
                " * Generated code - do not modify.\n"
                " * Provides data that needs to be accessed before butterfly data files are\n"
                " * loaded.\n"
                " */\n"
                " \n"
                "public class ButterflyInfo {\n"
                "\n"
                "    // A list of all the species in the mod.\n"
                "    public static final String[] SPECIES = {\n"
            )

            # Write species array
            for name in all_species:
                out.write(f'            "{name}",\n')
            out.write("    };\n\n")

            # Write traits array
            self._write_enum_array(
                out,
                "TRAITS",
                "ButterflyData.Trait",
                all_species,
                lambda s: species_data[s].get("traits") if species_data[s] else None,
                header_comment="A list of traits each butterfly has.")

            #   Write types array
            self._write_enum_array(
                out,
                "TYPES",
                "ButterflyData.ButterflyType",
                all_species,
                lambda s: species_data[s].get("type") if species_data[s] else "BUTTERFLY",
                nested=False,
                header_comment="A list of types of  butterflies.")

            #   Write rarity array
            self._write_enum_array(
                out,
                "RARITIES",
                "ButterflyData.Rarity",
                all_species,
                lambda s: species_data[s].get("rarity") if species_data[s] else "COMMON",
                nested=False,
                header_comment="A list of how rare each butterfly is.")

            # End file
            out.write("}\n")

        self.logger.info("Java code generation complete.")
