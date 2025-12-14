# JARVIS Android - Project Structure

## 📁 Complete File Hierarchy

```
android/
├── 📄 build.gradle (Root build config - Kotlin plugin)
├── 📄 settings.gradle (Project settings)
├── 📄 gradle.properties (Build properties)
├── 📄 JARVIS_ARCHITECTURE.md (Technical documentation)
├── 📄 USER_GUIDE.md (User setup guide)
├── 📄 IMPLEMENTATION_SUMMARY.md (What was built)
├── 📄 PROJECT_STRUCTURE.md (This file)
│
└── app/
    ├── 📄 build.gradle (App build config - dependencies)
    ├── 📄 proguard-rules.pro (Code optimization rules)
    │
    └── src/main/
        ├── 📄 AndroidManifest.xml (App configuration + permissions)
        │
        ├── kotlin/com/jarvis/assistant/
        │   ├── 📄 JarvisApplication.kt (16 lines - App entry point)
        │   ├── 📄 MainActivity.kt (105 lines - Main Compose activity)
        │   │
        │   ├── ai/ (AI & Brain)
        │   │   ├── 📄 BrainManager.kt (74 lines - Command orchestration)
        │   │   └── 📄 GeminiClient.kt (244 lines - Gemini API integration)
        │   │
        │   ├── model/ (Data Models)
        │   │   └── 📄 ActionResult.kt (108 lines - AI response models)
        │   │
        │   ├── service/ (System Services)
        │   │   ├── 📄 JarvisSystemController.kt (225 lines - Accessibility)
        │   │   └── 📄 VoiceRecognitionService.kt (67 lines - Voice service)
        │   │
        │   ├── receiver/ (Broadcast Receivers)
        │   │   └── 📄 JarvisDeviceAdminReceiver.kt (83 lines - Device Admin)
        │   │
        │   ├── util/ (Utilities)
        │   │   ├── 📄 ActionExecutor.kt (278 lines - System control)
        │   │   └── 📄 SystemMonitor.kt (182 lines - Metrics collection)
        │   │
        │   └── ui/ (User Interface)
        │       ├── theme/
        │       │   └── 📄 Theme.kt (37 lines - Color scheme)
        │       │
        │       └── components/
        │           ├── 📄 ArcReactor.kt (148 lines - Animated reactor)
        │           ├── 📄 JarvisHUD.kt (214 lines - Main HUD screen)
        │           └── 📄 SystemIndicators.kt (315 lines - Gauges)
        │
        └── res/
            ├── values/
            │   ├── strings.xml (App strings + descriptions)
            │   └── themes.xml (App theme)
            │
            ├── xml/
            │   ├── accessibility_service_config.xml (Service config)
            │   └── device_admin_config.xml (Admin policies)
            │
            ├── layout/
            │   └── activity_main.xml (Legacy layout - unused)
            │
            └── mipmap-*/ (App icons)
```

## 📊 Statistics

### Code Distribution
```
Component              Files    Lines    Purpose
─────────────────────────────────────────────────────────────
UI Components            4      714     Compose UI & theme
AI Layer                 2      318     Gemini API & orchestration
System Control           2      460     Action execution & monitoring
Services                 2      292     Accessibility & voice
Models                   1      108     Data structures
Receivers                1       83     Device admin
Core                     2      121     Application & MainActivity
─────────────────────────────────────────────────────────────
TOTAL                   14    2,096     Pure Kotlin code

Documentation            3      829     Guides & architecture docs
Configuration            4      142     Gradle, ProGuard, Manifest
XML Resources            2       20     Service configs
─────────────────────────────────────────────────────────────
GRAND TOTAL             23    3,087     All new/modified files
```

### Package Breakdown
```
📦 com.jarvis.assistant
  ├── 📦 ai (2 files, 318 lines)          - Brain & API
  ├── 📦 model (1 file, 108 lines)        - Data models
  ├── 📦 service (2 files, 292 lines)     - System services
  ├── 📦 receiver (1 file, 83 lines)      - Broadcast receivers
  ├── 📦 util (2 files, 460 lines)        - Utilities
  └── 📦 ui (4 files, 714 lines)          - Compose UI
      ├── 📦 theme (1 file, 37 lines)
      └── 📦 components (3 files, 677 lines)
```

## 🎯 File Purposes

### Core Application Files

**JarvisApplication.kt**
- Application singleton
- Global initialization point
- Context management

**MainActivity.kt**
- Jetpack Compose activity
- Permission handling
- System status updates
- BrainManager integration

### AI & Brain

**BrainManager.kt**
- Command orchestration layer
- Coroutine-based async processing
- Callback management for UI updates
- Error handling and recovery

**GeminiClient.kt**
- Retrofit API client
- Gemini API integration
- Natural language processing
- JSON parsing with fallbacks
- System instruction prompts

### Data Models

**ActionResult.kt**
- Action type constants (20+)
- Gemini API request/response models
- System status data class
- JSON serialization annotations

### Services

**JarvisSystemController.kt**
- AccessibilityService implementation
- Screen content reading
- UI element interaction
- Gesture simulation (clicks, swipes)
- Node tree traversal

**VoiceRecognitionService.kt**
- Foreground service for voice
- Notification management
- Ready for speech recognition
- Microphone permission handling

### Receivers

**JarvisDeviceAdminReceiver.kt**
- Device Admin API integration
- Device locking functionality
- Admin status checking
- Permission prompting

### Utilities

**ActionExecutor.kt**
- 15+ action implementations
- Hardware control (flashlight, WiFi, Bluetooth)
- System settings (brightness, DND, volume)
- App launching and management
- Intent creation and execution

**SystemMonitor.kt**
- CPU temperature reading
- Battery status monitoring
- Storage capacity calculation
- RAM usage tracking
- Network stats (placeholder)

### UI Components

**Theme.kt**
- JARVIS color scheme
- Material3 dark theme
- Cyan/blue holographic palette
- Color constants

**ArcReactor.kt**
- Animated central reactor
- Infinite rotation animation
- Pulse effects
- Amplitude-reactive particles
- Radial gradients and glow
- Canvas drawing

**JarvisHUD.kt**
- Main screen composition
- System indicator positioning
- Arc Reactor integration
- Terminal command input
- Status message display
- Layout management

**SystemIndicators.kt**
- CPU temperature gauge
- Battery circular indicator
- Storage bar chart
- Network activity graph
- Reusable gauge components
- Canvas-based drawing

## 🔧 Configuration Files

### Build Configuration

**android/build.gradle**
```gradle
- Kotlin version: 1.9.20
- Android Gradle Plugin: 8.1.0
- Repositories: Google + Maven Central
```

**android/app/build.gradle**
```gradle
- Kotlin Android plugin
- Compose configuration
- Java 17 compatibility
- 27 dependencies:
  * Core Android + KTX
  * Jetpack Compose BOM
  * Material3
  * Accompanist
  * Retrofit + OkHttp
  * Gson
  * Coroutines
```

### Manifest Configuration

**AndroidManifest.xml**
```xml
Permissions (20+):
- Core: INTERNET, NETWORK_STATE
- Audio: RECORD_AUDIO
- System: WRITE_SETTINGS, WRITE_SECURE_SETTINGS
- Hardware: CAMERA, BLUETOOTH, WIFI
- Special: ACCESSIBILITY, DEVICE_ADMIN, SYSTEM_ALERT_WINDOW

Components:
- MainActivity (launcher)
- JarvisSystemController (accessibility service)
- VoiceRecognitionService (foreground service)
- JarvisDeviceAdminReceiver (device admin)
```

### ProGuard Rules

**proguard-rules.pro**
```
- Keep JARVIS classes
- Kotlin coroutines
- Retrofit + OkHttp
- Gson serialization
- AndroidX + Compose
- Accessibility service
- Device admin receiver
```

## 📈 Complexity Analysis

### Lines of Code by Component
```
SystemIndicators.kt  ████████████████████████  315
ActionExecutor.kt    ██████████████████████    278
GeminiClient.kt      ███████████████████       244
JarvisController.kt  ████████████████          225
JarvisHUD.kt         ██████████████            214
SystemMonitor.kt     ████████████              182
ArcReactor.kt        ██████████                148
ActionResult.kt      ██████                    108
MainActivity.kt      ███████                   105
DeviceAdmin.kt       █████                      83
BrainManager.kt      █████                      74
VoiceService.kt      ████                       67
Theme.kt             ██                         37
JarvisApp.kt         █                          16
```

### Component Dependencies
```
MainActivity
  ↓
BrainManager → GeminiClient (API)
  ↓
ActionExecutor → JarvisSystemController (Accessibility)
  ↓             JarvisDeviceAdminReceiver (Device Admin)
  ↓
Android System

UI Components
JarvisHUD
  ├── ArcReactor
  ├── SystemIndicators
  └── Theme

Utilities
SystemMonitor (reads system metrics)
```

## 🎨 UI Component Hierarchy

```
JarvisHUD (Main Screen)
├── TopStart: CpuTemperatureIndicator
│   └── CircularGauge
├── TopEnd: BatteryIndicator
│   └── CircularGauge
├── Center: Column
│   ├── ArcReactor (animated)
│   ├── Text ("J.A.R.V.I.S.")
│   └── Text (status message)
├── BottomStart: StorageIndicator
│   └── HorizontalBarChart
├── BottomEnd: NetworkIndicator
│   └── NetworkGraph
└── BottomCenter: CommandTerminal
    ├── Text (">")
    ├── BasicTextField (input)
    └── Button ("SEND")
```

## 🔄 Data Flow

```
User Input (Voice/Text)
  ↓
MainActivity.onCommandSubmit()
  ↓
BrainManager.processCommand()
  ↓
GeminiClient.processCommand()
  ↓
Retrofit → Gemini API (Cloud)
  ↓
GeminiResponse (JSON)
  ↓
ActionResult (parsed)
  ↓
ActionExecutor.executeAction()
  ↓
Android APIs / AccessibilityService
  ↓
System Action Performed
  ↓
Callback → UI Update
```

## 📦 Dependency Tree

```
com.jarvis.assistant
├── androidx.core:core-ktx
├── androidx.compose:compose-bom
│   ├── compose.ui:ui
│   ├── compose.material3:material3
│   ├── compose.animation:animation
│   └── compose.foundation:foundation
├── androidx.activity:activity-compose
├── androidx.lifecycle:lifecycle-*
├── com.google.accompanist:accompanist-*
├── com.squareup.retrofit2:retrofit
│   └── converter-gson
├── com.squareup.okhttp3:okhttp
│   └── logging-interceptor
├── com.google.code.gson:gson
└── org.jetbrains.kotlinx:kotlinx-coroutines-*
```

## 🎯 Key Features by File

| Feature | Primary File | Supporting Files |
|---------|--------------|------------------|
| AI Processing | GeminiClient.kt | BrainManager.kt, ActionResult.kt |
| UI/HUD | JarvisHUD.kt | ArcReactor.kt, SystemIndicators.kt |
| System Control | ActionExecutor.kt | JarvisSystemController.kt |
| Metrics | SystemMonitor.kt | SystemIndicators.kt |
| Device Lock | JarvisDeviceAdminReceiver.kt | ActionExecutor.kt |
| Voice Input | VoiceRecognitionService.kt | MainActivity.kt |
| Theme | Theme.kt | All UI components |

## 🔐 Security Considerations

**Files with Security Implications:**
- GeminiClient.kt: Contains API key (documented warning)
- ActionExecutor.kt: System permission checks
- JarvisSystemController.kt: Accessibility access
- JarvisDeviceAdminReceiver.kt: Device admin rights
- proguard-rules.pro: Code obfuscation rules

## 🚀 Build Artifacts

**Generated Files (not in repo):**
```
app/build/
├── outputs/
│   └── apk/
│       ├── debug/
│       │   └── app-debug.apk (~20MB)
│       └── release/
│           └── app-release.apk (~8MB, minified)
└── intermediates/ (Kotlin, Compose, resources)
```

## 📝 Documentation Files

| File | Lines | Purpose |
|------|-------|---------|
| JARVIS_ARCHITECTURE.md | 159 | Technical architecture guide |
| USER_GUIDE.md | 332 | User setup and usage |
| IMPLEMENTATION_SUMMARY.md | 338 | What was built summary |
| PROJECT_STRUCTURE.md | (this file) | File organization |

---

**Total Project Size**: 2,096 lines of Kotlin + 3,087 total files
**Creation Date**: December 2024
**Version**: 1.0.0
