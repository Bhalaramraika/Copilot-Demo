# JARVIS - User Guide

Welcome to JARVIS (Just A Rather Very Intelligent System) - Your AI-Powered Android Assistant!

## 📱 Installation

### Option 1: Download from GitHub Actions

1. Go to the [GitHub Actions](https://github.com/Bhalaramraika/Copilot-Demo/actions) page
2. Find the latest successful workflow run
3. Download the `JARVIS-Android-APK` artifact
4. Extract the ZIP file to get the APK
5. Install the APK on your Android device (you may need to enable "Install from Unknown Sources")

### Option 2: Build from Source

```bash
git clone https://github.com/Bhalaramraika/Copilot-Demo.git
cd Copilot-Demo/android
./gradlew assembleRelease
# APK will be in: app/build/outputs/apk/release/
```

## 🔧 Initial Setup

After installing JARVIS, you need to grant several permissions for full functionality:

### 1. Basic Permissions (Automatic)

When you first open JARVIS, it will request:
- ✅ **Microphone Access**: For voice commands
- ✅ **Camera Access**: For flashlight control

Tap "Allow" for each permission.

### 2. Accessibility Service (Manual)

This is the most important permission for system control:

1. Open **Settings** on your device
2. Navigate to **Accessibility**
3. Find **JARVIS System Controller** in the list
4. Toggle it **ON**
5. Confirm the warning dialog

✨ This allows JARVIS to:
- Control other apps
- Click buttons and interact with UI
- Read screen content
- Perform gestures

### 3. Do Not Disturb Access (Optional)

For DND commands:

1. Open **Settings** → **Apps** → **JARVIS**
2. Tap **Permissions** → **Do Not Disturb**
3. Select **Allow**

### 4. Modify System Settings (Optional)

For brightness control:

1. Open **Settings** → **Apps** → **JARVIS**
2. Tap **Special App Access** → **Modify system settings**
3. Toggle **Allow modify system settings**

### 5. Device Admin (Optional)

For device locking:

1. Open **Settings** → **Security** → **Device admin apps**
2. Find **JARVIS Device Admin** and enable it

Or simply try the "lock device" command, and JARVIS will guide you.

## 🎮 How to Use

### Interface Overview

```
┌─────────────────────────┐
│  [CPU]         [Battery]│  ← System Indicators
│                         │
│       ⚡ ARC REACTOR ⚡  │  ← Central Animation
│                         │
│      J.A.R.V.I.S.       │  ← Status Display
│                         │
│  [Storage]    [Network] │  ← More Indicators
│                         │
│  > Type command here... │  ← Command Terminal
└─────────────────────────┘
```

### System Indicators

- **Top-Left (CPU)**: Shows CPU temperature in real-time
- **Top-Right (Battery)**: Battery percentage, voltage, and charging status
- **Bottom-Left (Storage)**: Used vs. Total storage space
- **Bottom-Right (Network)**: Network activity (simulated)
- **Center (Arc Reactor)**: Pulses and reacts to voice (future feature)

### Giving Commands

You can interact with JARVIS in two ways:

#### 1. Text Input (Currently Active)

Type your command in the terminal at the bottom and press "SEND" or Enter.

#### 2. Voice Input (Coming Soon)

Tap the microphone button and speak your command.

## 🗣️ Command Examples

### Hardware Control

| Command | Action |
|---------|--------|
| "Turn on the flashlight" | Enables device flashlight |
| "Turn off the flashlight" | Disables flashlight |
| "It's too dark" | Turns on flashlight (AI understands intent) |
| "Make screen brighter" | Increases brightness |
| "Dim the screen" | Decreases brightness |

### Connectivity

| Command | Action |
|---------|--------|
| "Turn on WiFi" | Enables WiFi (or opens WiFi settings on Android 10+) |
| "Disable WiFi" | Disables WiFi |
| "Turn on Bluetooth" | Enables Bluetooth |
| "Turn off Bluetooth" | Disables Bluetooth |

### System Settings

| Command | Action |
|---------|--------|
| "Don't disturb me" | Enables Do Not Disturb mode |
| "I want to sleep" | Enables DND (AI understands intent) |
| "Turn off DND" | Disables Do Not Disturb |
| "Lock my phone" | Locks the device (requires Device Admin) |
| "Lock device" | Same as above |

### App Control

| Command | Action |
|---------|--------|
| "Open Instagram" | Launches Instagram app |
| "Open WhatsApp" | Launches WhatsApp |
| "Open YouTube" | Launches YouTube |
| "Open Chrome" | Opens Chrome browser |
| "Open Settings" | Opens device Settings |

### Smart Search

| Command | Action |
|---------|--------|
| "I'm stuck on this coding error" | Opens YouTube and searches for coding help |
| "Search for pizza near me" | Opens Google search |
| "How to fix Android app crash" | Opens YouTube search |
| "Show me cat videos" | Searches YouTube for cat videos |

### System Optimization

| Command | Action |
|---------|--------|
| "My phone is slow" | Opens app settings to kill background apps |
| "Clean up memory" | Same as above |
| "Free up RAM" | Suggests closing background apps |

## 🤖 How the AI Works

JARVIS uses Google's **Gemini AI** to understand your commands:

1. **You speak/type**: "I want to sleep"
2. **JARVIS processes**: Sends to Gemini API with system instructions
3. **AI understands**: Returns `{"action_type": "dnd_on", ...}`
4. **JARVIS executes**: Enables Do Not Disturb mode
5. **Feedback**: Shows status message

The AI is trained to understand:
- ✅ Natural language (no exact phrases needed)
- ✅ Context and intent
- ✅ Different ways to say the same thing
- ✅ Common app names and packages

## 🎨 Customization

### Theme

JARVIS uses a dark, sci-fi theme inspired by Iron Man:
- **Background**: Pure black (#0A0A0A)
- **Primary**: Holographic cyan (#00D4FF)
- **Accents**: Blue gradients

### Future Features

Coming soon:
- Settings panel to customize colors
- Toggle indicators on/off
- Adjust animation speed
- Choose voice language
- Custom wake word

## ⚠️ Troubleshooting

### "JARVIS can't control my device"

**Solution**: Enable Accessibility Service
1. Settings → Accessibility → JARVIS System Controller → ON

### "Command not working"

**Possible causes**:
- Permission not granted (check permissions)
- Android version restriction (some features require specific Android versions)
- AI didn't understand (try rephrasing)

**Try**:
- Be more specific: "Turn on flashlight" instead of "Light"
- Check status message for errors
- Grant required permissions

### "Flashlight won't turn on"

**Solution**: Grant Camera permission
1. Settings → Apps → JARVIS → Permissions → Camera → Allow

### "Can't lock device"

**Solution**: Enable Device Admin
1. Settings → Security → Device admin apps → Enable JARVIS

### "DND not working"

**Solution**: Grant DND permission
1. Settings → Apps → JARVIS → Do Not Disturb → Allow

### "WiFi/Bluetooth toggle not working"

**Note**: Android 10+ restricts programmatic toggling. JARVIS will open the relevant settings page for you to toggle manually.

## 📊 System Requirements

- **Minimum Android**: 7.0 (API 24)
- **Target Android**: 14 (API 34)
- **Recommended RAM**: 2GB+
- **Storage**: ~100MB (includes AI features and assets)
- **Network**: Required for AI processing

## 🔒 Privacy & Security

### Data Collection

JARVIS:
- ✅ Processes commands locally where possible
- ✅ Sends voice/text to Gemini API for AI processing
- ❌ Does NOT store your commands
- ❌ Does NOT send data to any other servers
- ❌ Does NOT track your usage

### API Key

The Gemini API key is included in the app for demo purposes. In a production scenario:
- You could provide your own API key
- Keys would be rotated regularly
- Usage would be monitored

### Permissions Explained

JARVIS requests many permissions, but here's why:

| Permission | Why? |
|------------|------|
| Microphone | Voice commands |
| Camera | Flashlight control |
| Accessibility | System control and app interaction |
| Write Settings | Brightness adjustment |
| DND Access | Do Not Disturb control |
| Device Admin | Device locking |
| Network | API calls for AI processing |

**You're in control**: You can deny any permission, but related features won't work.

## 🆘 Support

Having issues? Here's how to get help:

1. **Check this guide**: Most issues are covered above
2. **Read the logs**: If the app crashes, check Android logcat
3. **GitHub Issues**: Report bugs at [GitHub Issues](https://github.com/Bhalaramraika/Copilot-Demo/issues)
4. **Documentation**: See `JARVIS_ARCHITECTURE.md` for technical details

## 🚀 Advanced Usage

### For Developers

Want to customize JARVIS or add new commands?

1. **Add new action types**: Edit `ActionType` in `model/ActionResult.kt`
2. **Implement action**: Add case in `ActionExecutor.kt`
3. **Train AI**: Update system instruction in `GeminiClient.kt`
4. **Build**: `./gradlew assembleDebug`

### Custom Commands

The AI is flexible! Try creative commands:
- "I'm going to a meeting" → Could trigger DND + auto-reply
- "Battery saver mode" → Could disable WiFi/Bluetooth, dim screen
- "Show me my day" → Could open calendar (if implemented)

## 📜 Credits

**Inspired by**: Tony Stark's J.A.R.V.I.S. from the Marvel Cinematic Universe

**Built with**:
- Kotlin
- Jetpack Compose
- Google Gemini AI
- Android Accessibility Service
- Love for Iron Man ❤️

**Disclaimer**: This is a fan project. Not affiliated with Marvel or Disney.

---

**Version**: 1.0.0
**Last Updated**: December 2024

Enjoy your JARVIS assistant! 🦾
