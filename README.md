# Local Buddy - React Native App

A production-ready React Native app built with Expo that replicates the Local Buddy website experience. Connect households with trusted students and youth for errands and daily tasks in Tier-2 and Tier-3 cities across India.

![Local Buddy Status](https://img.shields.io/badge/Status-Production-brightgreen)
![Version](https://img.shields.io/badge/Version-1.0.0-blue)
![React Native](https://img.shields.io/badge/React%20Native-Expo-blue)
![Platform](https://img.shields.io/badge/Platform-Android-green)

## 🌟 Features

### App Features
- **Hyper-Local Marketplace**: First task marketplace for Tier-2 cities
- **Instant Matching**: AI-powered helper matching in seconds
- **Verified Students**: ID-checked helpers for total safety
- **Gamified Experience**: Earn Karma Points & level up
- **Earnings Calculator**: Interactive slider to estimate monthly income
- **Beautiful UI**: Matches the website design exactly with glassmorphism effects

### UI Features
- **Dark Theme**: Stunning dark interface (#030712 background)
- **Animated Elements**: 
  - Typing effect for hero text
  - Floating phone mockup
  - Pulsing ambient glow
  - Smooth scroll animations
- **Glassmorphism Design**: Modern blur effects throughout
- **Phone Preview**: Interactive app preview with notifications
- **Responsive Layout**: Optimized for all screen sizes

## 📱 Download & Install

### Direct APK Download
1. Go to the **[Actions](../../actions)** tab
2. Click the latest successful "Build Android APK" workflow run
3. Download **local-buddy-apk** artifact
4. Extract the ZIP file to get the APK
5. Enable "Install from Unknown Sources" in Android settings
6. Install the APK on your device (requires Android 7.0+)

### Alternative Download
Download directly from MediaFire: [LocalBuddy.apk](https://www.mediafire.com/file/zmafddx601he5j0/LocalBuddy.apk/file?dkey=c5yye4rqp1y&r=1899)

## 🚀 Development Setup

### Prerequisites
- Node.js 18+ 
- npm or yarn
- Expo CLI
- Android Studio (for Android development)
- Xcode (for iOS development, macOS only)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Bhalaramraika/Copilot-Demo.git
   cd Copilot-Demo
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npx expo start
   ```

4. **Run on device/emulator**
   - Press `a` for Android
   - Press `i` for iOS
   - Scan QR code with Expo Go app

## 🏗️ Building APK

### Using EAS Build (Recommended)

1. **Install EAS CLI**
   ```bash
   npm install -g eas-cli
   ```

2. **Login to Expo**
   ```bash
   eas login
   ```

3. **Configure build**
   ```bash
   eas build:configure
   ```

4. **Build APK**
   ```bash
   eas build --platform android --profile preview
   ```

### Using GitHub Actions
The repository includes a GitHub Actions workflow that automatically builds APK on push to main branch.

## 🛠️ Technology Stack

- **Framework**: React Native with Expo
- **UI Components**: React Native core components
- **Gradients**: expo-linear-gradient
- **Icons**: @expo/vector-icons (MaterialCommunityIcons)
- **Animations**: React Native Animated API & react-native-reanimated
- **Build**: Expo Application Services (EAS)

## 📖 App Sections

### Hero Section
- Animated typing effect with "Friend.", "Helper.", "Buddy."
- Live status badge for Balotra v1.0
- Direct download button with gradient
- Floating phone mockup with live preview

### Why Local Buddy?
- **Instant Matching**: AI finds nearest helper in seconds
- **Verified Students**: ID checked for total safety
- **Gamified**: Earn Karma Points & level up

### Earnings Calculator
- Interactive slider (1-10 hours per day)
- Real-time earnings calculation (₹400/hour base rate)
- Monthly income projection

### Get Started Guide
1. Download APK
2. Install on device
3. Login with mobile number

## 🎨 Design Specifications

### Colors
- Background: `#030712` (dark-bg)
- Primary Brand: `#3b82f6` (brand-500)
- Secondary Brand: `#2563eb` (brand-600)
- Text Primary: `#ffffff`
- Text Secondary: `#9ca3af`
- Success: `#10b981`
- Purple Accent: `#a855f7`
- Orange Accent: `#fb923c`

### Typography
- Headings: 48px bold
- Section Titles: 30px bold
- Subtitles: 18px regular
- Body: 14px regular

### Effects
- Glassmorphism: `rgba(17, 24, 39, 0.6)` with blur
- Card borders: `rgba(255, 255, 255, 0.08)`
- Shadows: Multiple elevation levels
- Border radius: 12-24px for cards

## 🔧 Configuration

### App Configuration (`app.json`)
- Name: Local Buddy
- Slug: local-buddy
- Version: 1.0.0
- Dark mode by default
- Android package: com.localbuddy.app

## 📂 Project Structure

```
Copilot-Demo/
├── .github/
│   └── workflows/
│       └── build-apk.yml       # GitHub Actions workflow
├── assets/                     # Images and icons
├── App.js                      # Main application file
├── app.json                    # Expo configuration
├── package.json                # Dependencies
└── README.md                   # This file
```

## 🌐 Related Links

- **Website**: [Local Buddy Download](https://github.com/Bhalaramraika/Download-LocalBuddy)
- **Expo**: [expo.dev](https://expo.dev)
- **React Native**: [reactnative.dev](https://reactnative.dev)

## 📄 License

© 2024 Local Buddy Inc. All rights reserved.

## 🙏 Acknowledgments

- Inspired by the Local Buddy website
- Built with React Native and Expo
- Designed for Tier-2 and Tier-3 cities in India

---

**"Connecting communities, one task at a time."**