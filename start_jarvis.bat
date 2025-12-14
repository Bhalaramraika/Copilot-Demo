@echo off
REM JARVIS AI Bot Startup Script for Windows

echo ==================================================
echo   Starting JARVIS AI Assistant
echo ==================================================
echo.

REM Check if Python is installed
python --version >nul 2>&1
if errorlevel 1 (
    echo Error: Python is not installed or not in PATH.
    echo Please install Python 3.8 or higher and add it to PATH.
    pause
    exit /b 1
)

REM Install dependencies
if exist requirements.txt (
    echo Installing dependencies...
    pip install -r requirements.txt --quiet
    echo Dependencies installed successfully.
    echo.
)

REM Check if jarvis_bot.py exists
if not exist jarvis_bot.py (
    echo Error: jarvis_bot.py not found.
    echo Please ensure you're in the correct directory.
    pause
    exit /b 1
)

echo Launching JARVIS...
echo.
echo Once started, open your browser and navigate to:
echo   -^> http://localhost:5000
echo.
echo Press Ctrl+C to stop JARVIS.
echo.
echo ==================================================
echo.

REM Start JARVIS
python jarvis_bot.py

pause
