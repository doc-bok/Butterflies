import json
import os
import tempfile
from pathlib import Path


def write_json_atomically(path: Path, data: object) -> None:
    """
    Writes data to a temporary file and only copies it if the write succeeds.
    :param path: The path of the file to write.
    :param data: The data to write to the file.
    """
    payload = json.dumps(
        data,
        sort_keys=True,
        indent=2,
        ensure_ascii=False,
    ) + "\n"

    temporary_path: Path | None = None

    try:
        with tempfile.NamedTemporaryFile(
            mode="w",
            encoding="utf-8",
            dir=path.parent,
            prefix=f".{path.name}.",
            suffix=".tmp",
            delete=False,
        ) as stream:
            temporary_path = Path(stream.name)
            stream.write(payload)
            stream.flush()
            os.fsync(stream.fileno())

        temporary_path.replace(path)
    finally:
        if temporary_path is not None:
            temporary_path.unlink(missing_ok=True)