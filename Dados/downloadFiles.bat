:: Code from https://www.youtube.com/watch?v=hcUIwiJwidY
@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

for /F "tokens=1,2" %%x in (files.txt) do (
    echo "Downloading from %%x"
    echo "Creating file %%y"

    powershell -Command "Invoke-WebRequest %%x -Outfile %%y"
)

echo "Done!"
cls
pause