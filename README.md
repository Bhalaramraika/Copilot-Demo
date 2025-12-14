# JARVIS - AI Assistant Bot

A high-tech AI bot inspired by JARVIS from Iron Man, designed to control applications, provide information, and manage your digital environment with a stunning futuristic interface.

![JARVIS Status](https://img.shields.io/badge/Status-Operational-brightgreen)
![Version](https://img.shields.io/badge/Version-1.0.0-blue)
![Python](https://img.shields.io/badge/Python-3.8+-blue)
![Android](https://img.shields.io/badge/Android-7.0+-green)
![Platform](https://img.shields.io/badge/Platform-Web%20%7C%20Android-blue)

## 🌟 Features

### Core Capabilities
- **Voice-Inspired Interface**: Stunning circular UI with animated visualizations similar to JARVIS
- **Intelligent Command Processing**: Natural language understanding for various commands
- **System Information**: Real-time system specs, CPU, memory, and battery status
- **Time & Date Functions**: Quick access to current time and date
- **Application Control**: Ability to open and manage applications
- **Web Search Integration**: Search capabilities for information lookup
- **Weather Information**: Weather status and forecasts
- **System Control**: Simulated system operations (shutdown, restart)
- **Interactive Chat**: Real-time communication log with JARVIS

### UI Features
- **Animated Background**: Dynamic grid with flowing animations
- **Pulsing Circle**: Central JARVIS visualization with ripple effects
- **Voice Indicators**: Animated bars simulating voice activity
- **Glowing Effects**: Neon-style cyan glow throughout the interface
- **Quick Commands**: One-click access to common functions
- **Real-time Stats**: Live system status and uptime display
- **Responsive Design**: Adapts to different screen sizes

## 📱 Native Android App - Complete JARVIS Experience!

🚀 **NEW**: JARVIS is now a full-featured native Android app with AI integration, futuristic HUD, and deep system control!

### ✨ Key Features

- **🤖 Gemini AI Integration**: Natural language command processing using Google's Gemini API
- **🎨 Futuristic HUD**: Iron Man-inspired interface with animated Arc Reactor
- **📊 System Monitoring**: Real-time CPU, Battery, Storage, and Network indicators
- **🎮 Deep System Control**: Control flashlight, WiFi, Bluetooth, brightness, DND, and more
- **🔒 Device Admin**: Lock device with voice commands
- **♿ Accessibility Service**: Interact with other apps programmatically
- **💬 Smart Commands**: "I'm stuck on a coding error" → Opens YouTube with search
- **⚡ Jetpack Compose**: Modern, reactive UI built with Compose

### 📥 Download & Install

1. Go to **[Actions](../../actions)** tab
2. Click the latest successful "Build Android APK" workflow run
3. Download **JARVIS-Android-APK** artifact
4. Extract the ZIP file to get the APK
5. Enable "Install from Unknown Sources" in your Android settings
6. Install the APK on your Android device (requires Android 7.0+)

### 📖 Documentation

- **[User Guide](android/USER_GUIDE.md)**: Complete setup and usage instructions
- **[Architecture Guide](android/JARVIS_ARCHITECTURE.md)**: Technical documentation for developers

### 🛠️ Technology Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material3
- **AI**: Google Gemini API
- **Networking**: Retrofit + OkHttp
- **Architecture**: MVVM with BrainManager orchestration

📖 **Detailed Instructions**: 
- [English Documentation](android/README.md)
- [हिंदी में डॉक्यूमेंटेशन](ANDROID_SETUP_HINDI.md)

## 🚀 Quick Start (Web Version)

### Prerequisites
- Python 3.8 or higher
- pip (Python package manager)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Bhalaramraika/Copilot-Demo.git
   cd Copilot-Demo
   ```

2. **Install dependencies**
   ```bash
   pip install -r requirements.txt
   ```

3. **Run JARVIS**
   ```bash
   python jarvis_bot.py
   ```

4. **Access the interface**
   Open your browser and navigate to:
   ```
   http://localhost:5000
   ```

## 💻 Usage

### Available Commands

#### Greetings
- "Hello JARVIS"
- "Hi"
- "Good morning"

#### Time & Date
- "What time is it?"
- "Tell me the date"
- "What day is today?"

#### System Information
- "System specs"
- "System status"
- "Battery status"

#### Control Commands
- "Open [application name]"
- "Search for [query]"
- "Weather"

#### Help & Status
- "Help"
- "What can you do?"
- "Status check"
- "Who are you?"

### Quick Command Buttons
Use the right panel quick command buttons for instant access to:
- Current time
- Current date
- System status
- Battery information
- System specifications

## 🎨 UI Highlights

### Design Elements
- **Color Scheme**: Cyan (#00d4ff) on dark background - iconic JARVIS aesthetic
- **Animations**: 
  - Pulsing central circle
  - Ripple effects
  - Voice activity bars
  - Glowing text effects
  - Animated grid background
- **Layout**: Three-panel design with chat, central visualization, and controls

### Responsive Features
- Adapts to different screen sizes
- Maintains visual appeal on mobile and desktop
- Optimized performance with CSS animations

## 🏗️ Architecture

### Backend (Python/Flask)
- **jarvis_bot.py**: Main server with AI command processing
- RESTful API endpoints for command processing
- Modular command handler system
- Real-time system monitoring

### Frontend (HTML/CSS/JavaScript)
- **templates/index.html**: Single-page application
- Pure CSS animations (no external libraries)
- Vanilla JavaScript for interactivity
- WebSocket-ready architecture

## 🔧 Configuration

### Server Settings
Default configuration in `jarvis_bot.py`:
- Host: `0.0.0.0` (development) or `127.0.0.1` (production)
- Port: `5000`
- Debug: Enabled in development, disabled in production

### Production Deployment
For production use, set the environment variable:
```bash
export JARVIS_PRODUCTION=true
python jarvis_bot.py
```

This will:
- Disable debug mode (preventing arbitrary code execution)
- Bind to localhost only (127.0.0.1)
- Remove development warnings

For production deployment, consider using a WSGI server like Gunicorn:
```bash
pip install gunicorn
gunicorn jarvis_bot:app --bind 127.0.0.1:5000
```

### Customization
You can customize:
- Color scheme in CSS variables
- Command responses in JarvisAI class
- Animation speeds and effects
- Quick command buttons

## 📝 API Endpoints

### POST /api/command
Process user commands
```json
{
  "command": "What time is it?"
}
```

Response:
```json
{
  "response": "The current time is 10:30 AM, Sir.",
  "type": "time",
  "data": "10:30 AM"
}
```

### GET /api/status
Get JARVIS status
```json
{
  "name": "JARVIS",
  "version": "1.0.0",
  "active": true,
  "timestamp": "2025-12-14T07:52:47.360Z"
}
```

## 🛡️ Security Notes

- System control commands are simulated for safety
- Application control requires appropriate permissions
- No sensitive data is logged or stored
- CORS enabled for development (configure for production)

## 🔮 Future Enhancements

- [ ] Voice recognition integration
- [ ] Text-to-speech responses
- [ ] Smart home device integration
- [ ] Calendar and reminder management
- [ ] Email and message handling
- [ ] Advanced AI/ML capabilities
- [ ] Mobile app version
- [ ] Cloud deployment support

## 🤝 Contributing

Contributions are welcome! Feel free to:
- Report bugs
- Suggest new features
- Submit pull requests
- Improve documentation

## 📄 License

This project is open source and available for educational and personal use.

## 🙏 Acknowledgments

- Inspired by JARVIS from Marvel's Iron Man
- Built with Flask and modern web technologies
- Designed for the AI enthusiast community

## 📧 Contact

For questions or support, please open an issue on GitHub.

---

**"Just A Rather Very Intelligent System - At Your Service, Sir."**