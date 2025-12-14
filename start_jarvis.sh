#!/bin/bash

# JARVIS AI Bot Startup Script

echo "=================================================="
echo "  Starting JARVIS AI Assistant"
echo "=================================================="
echo ""

# Check if Python is installed
if ! command -v python3 &> /dev/null; then
    echo "Error: Python 3 is not installed."
    echo "Please install Python 3.8 or higher."
    exit 1
fi

# Check if pip is installed
if ! command -v pip &> /dev/null && ! command -v pip3 &> /dev/null; then
    echo "Error: pip is not installed."
    echo "Please install pip."
    exit 1
fi

# Install dependencies if requirements.txt exists
if [ -f "requirements.txt" ]; then
    echo "Installing dependencies..."
    pip install -r requirements.txt --quiet
    echo "Dependencies installed successfully."
    echo ""
fi

# Check if jarvis_bot.py exists
if [ ! -f "jarvis_bot.py" ]; then
    echo "Error: jarvis_bot.py not found."
    echo "Please ensure you're in the correct directory."
    exit 1
fi

echo "Launching JARVIS..."
echo ""
echo "Once started, open your browser and navigate to:"
echo "  -> http://localhost:5000"
echo ""
echo "Press Ctrl+C to stop JARVIS."
echo ""
echo "=================================================="
echo ""

# Start JARVIS
python3 jarvis_bot.py
