# JARVIS Android Architecture

## Overview

This is a production-grade Android application that functions like Tony Stark's JARVIS - an AI-powered assistant with deep system control and a futuristic HUD interface.

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **AI Engine**: Google Gemini API
- **Networking**: Retrofit + OkHttp
- **Concurrency**: Kotlin Coroutines
- **Architecture**: MVVM-like with BrainManager orchestration

## Project Structure

```
app/src/main/kotlin/com/jarvis/assistant/
├── JarvisApplication.kt          # Application entry point
├── MainActivity.kt                # Main Compose activity
├── ai/
│   ├── BrainManager.kt           # AI orchestration layer
│   └── GeminiClient.kt           # Gemini API integration
├── model/
│   └── ActionResult.kt           # Data models for AI decisions
├── service/
│   ├── JarvisSystemController.kt # Accessibility Service for system control
│   └── VoiceRecognitionService.kt# Voice recognition foreground service
├── receiver/
│   └── JarvisDeviceAdminReceiver.kt # Device admin for locking
├── ui/
│   ├── theme/
│   │   └── Theme.kt              # JARVIS color scheme
│   └── components/
│       ├── ArcReactor.kt         # Animated Arc Reactor component
│       ├── SystemIndicators.kt   # CPU, Battery, Storage, Network indicators
│       └── JarvisHUD.kt          # Main HUD interface
└── util/
    ├── ActionExecutor.kt         # Executes AI-determined actions
    └── SystemMonitor.kt          # Collects system metrics
```

## Key Features

### 1. AI Brain (Gemini Integration)

The `BrainManager` and `GeminiClient` work together to:
- Accept natural language commands
- Send them to Google's Gemini API with structured prompts
- Parse JSON responses containing action decisions
- Execute the determined actions

**API Key**: AIzaSyA0tkbFf1fvrf9ba94RszVhw3W2IOZZBBU

### 2. Futuristic HUD

Built with Jetpack Compose, featuring:
- **Arc Reactor**: Animated central component that pulses and reacts to voice
- **System Indicators**: 
  - Top-Left: CPU Temperature gauge
  - Top-Right: Battery level with charging status
  - Bottom-Left: Storage capacity bar chart
  - Bottom-Right: Network speed graph
- **Command Terminal**: Terminal-like input at the bottom
- **Dark Theme**: Black background with cyan/blue holographic accents

### 3. Deep System Control

Via `ActionExecutor` and `JarvisSystemController`:
- **Hardware Control**: Flashlight, WiFi, Bluetooth, Mobile Data
- **System Settings**: Brightness, Volume, Do Not Disturb
- **App Management**: Launch apps, kill background processes
- **Device Lock**: Via Device Admin API
- **Accessibility**: Programmatic UI interaction, screen parsing

### 4. Supported Commands

Natural language examples:
- "I'm stuck on this coding error, help me" → Opens YouTube search
- "It's too dark in here" → Turns on flashlight
- "I want to sleep, don't disturb me" → Enables DND mode
- "Open Instagram" → Launches Instagram app
- "My phone is slow" → Kills background apps
- "Turn off WiFi" → Disables WiFi

## Permissions

The app requires extensive permissions:
- `RECORD_AUDIO`: Voice commands
- `SYSTEM_ALERT_WINDOW`: HUD overlay
- `BIND_ACCESSIBILITY_SERVICE`: System control
- `WRITE_SETTINGS`: Brightness control
- `ACCESS_NOTIFICATION_POLICY`: DND control
- `CAMERA`: Flashlight control
- And more... (see AndroidManifest.xml)

## Setup & Configuration

### First-Time Setup

1. **Grant Accessibility Permission**:
   - Settings → Accessibility → JARVIS System Controller → Enable

2. **Grant Device Admin Permission** (for device locking):
   - Settings → Security → Device Admin → Enable JARVIS

3. **Grant Other Permissions**:
   - The app will request microphone, camera, etc. on first launch
   - Some permissions (like DND, Write Settings) require manual setup

### Building

```bash
cd android
./gradlew assembleDebug    # Debug build
./gradlew assembleRelease  # Release build (signed with debug keystore)
```

### CI/CD

GitHub Actions automatically builds the APK on every push:
- Workflow: `.github/workflows/build-android.yml`
- Artifacts: Available in Actions tab
- Release APKs: Attached to tagged releases

## Security Notes

⚠️ **API Key Warning**: The Gemini API key is hardcoded for demo purposes. In production:
- Store keys in `local.properties` or environment variables
- Use BuildConfig to inject keys at build time
- Rotate keys regularly

⚠️ **Debug Keystore**: The release build uses a debug keystore for convenience. For production:
- Generate a proper release keystore
- Store credentials securely (GitHub Secrets, etc.)
- Never commit keystores to version control

## Known Limitations

1. **Network Stats**: Real-time upload/download speeds require `PACKAGE_USAGE_STATS` and are difficult to access on modern Android
2. **WiFi/Bluetooth Toggle**: Android 10+ restricts programmatic toggling; users are redirected to settings
3. **Background App Killing**: Limited by Android's process management; best effort only
4. **Voice Recognition**: Basic implementation; continuous listening not yet fully implemented

## Future Enhancements

- [ ] Continuous voice recognition with wake word detection
- [ ] Text-to-speech responses
- [ ] Settings panel with toggles
- [ ] System alerts panel for app crashes
- [ ] Enhanced visualizer with real-time audio amplitude
- [ ] Gesture controls
- [ ] Widget support
- [ ] Wear OS companion app

## License

Demo/Educational purposes. Not affiliated with Marvel or Disney.
