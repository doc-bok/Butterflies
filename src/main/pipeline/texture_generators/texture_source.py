import os

from dataclasses import dataclass
from enum import Enum
from pathlib import Path

from PIL import Image

class ModelType(Enum):
    BASE = 0
    GLOW = 1
    DETAILED = 2
    HUMMINGBIRD = 3

@dataclass(frozen=True)
class TextureSource:

    BUTTERFLY_PREFIX = "butterfly_"
    CHRYSALIS_PREFIX = "chrysalis_"
    CATERPILLAR_PREFIX = "caterpillar_"
    EGG_SUFFIX = "_egg.png"

    path: Path
    image: Image.Image
    model_type: ModelType

    @property
    def name(self) -> str:
        return self.path.name

    @staticmethod
    def find_textures_with_prefix(directory: Path, prefix: str) -> list[Path]:
        return sorted(
            path
            for path in directory.iterdir()
            if path.is_file()
            and path.suffix.lower() == ".png"
            and path.name.startswith(prefix)
        )

    @staticmethod
    def find_textures_with_suffix(directory: Path, suffix: str) -> list[Path]:
        return sorted(
            path
            for path in directory.iterdir()
            if path.is_file()
            and os.path.basename(path).endswith(suffix)
        )

    @staticmethod
    def strip_prefix(name: str, prefix: str) -> str:
        if not name.startswith(prefix):
            raise ValueError(f"Expected {name!r} to begin with {prefix!r}")
        return name[len(prefix):]

    @staticmethod
    def strip_suffix(name: str, suffix: str) -> str:
        if not name.endswith(suffix):
            raise ValueError(f"Expected {name!r} to end with {suffix!r}")
        return name[:-len(suffix)]