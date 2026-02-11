import React, { useState, useEffect, useRef } from 'react';
import { 
  StyleSheet, 
  Text, 
  View, 
  ScrollView, 
  TouchableOpacity, 
  Linking,
  Animated,
  Dimensions,
  StatusBar
} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { MaterialCommunityIcons } from '@expo/vector-icons';

const { width, height } = Dimensions.get('window');

// Configuration
const DOWNLOAD_URL = 'https://www.mediafire.com/file/zmafddx601he5j0/LocalBuddy.apk/file?dkey=c5yye4rqp1y&r=1899';
const HOURLY_RATE = 400; // Rs per hour
const WORDS = ['Friend.', 'Helper.', 'Buddy.'];

export default function App() {
  const [hoursValue, setHoursValue] = useState(1);
  const [currentWordIndex, setCurrentWordIndex] = useState(0);
  const [displayText, setDisplayText] = useState('');
  const [isDeleting, setIsDeleting] = useState(false);
  
  const fadeAnim = useRef(new Animated.Value(0)).current;
  const slideAnim = useRef(new Animated.Value(30)).current;
  const floatAnim = useRef(new Animated.Value(0)).current;
  const pulseAnim = useRef(new Animated.Value(1)).current;
  const notificationAnim = useRef(new Animated.Value(0)).current;

  // Typing effect
  useEffect(() => {
    const word = WORDS[currentWordIndex];
    let timeoutId;

    if (!isDeleting && displayText.length < word.length) {
      timeoutId = setTimeout(() => {
        setDisplayText(word.substring(0, displayText.length + 1));
      }, 150);
    } else if (!isDeleting && displayText.length === word.length) {
      timeoutId = setTimeout(() => {
        setIsDeleting(true);
      }, 2000);
    } else if (isDeleting && displayText.length > 0) {
      timeoutId = setTimeout(() => {
        setDisplayText(displayText.substring(0, displayText.length - 1));
      }, 100);
    } else if (isDeleting && displayText.length === 0) {
      setIsDeleting(false);
      setCurrentWordIndex((currentWordIndex + 1) % WORDS.length);
    }

    return () => clearTimeout(timeoutId);
  }, [displayText, isDeleting, currentWordIndex]);

  // Initial fade in animation
  useEffect(() => {
    Animated.parallel([
      Animated.timing(fadeAnim, {
        toValue: 1,
        duration: 800,
        useNativeDriver: true,
      }),
      Animated.timing(slideAnim, {
        toValue: 0,
        duration: 800,
        useNativeDriver: true,
      }),
    ]).start();

    // Float animation
    Animated.loop(
      Animated.sequence([
        Animated.timing(floatAnim, {
          toValue: -20,
          duration: 3000,
          useNativeDriver: true,
        }),
        Animated.timing(floatAnim, {
          toValue: 0,
          duration: 3000,
          useNativeDriver: true,
        }),
      ])
    ).start();

    // Pulse animation
    Animated.loop(
      Animated.sequence([
        Animated.timing(pulseAnim, {
          toValue: 1.1,
          duration: 2000,
          useNativeDriver: true,
        }),
        Animated.timing(pulseAnim, {
          toValue: 1,
          duration: 2000,
          useNativeDriver: true,
        }),
      ])
    ).start();

    // Notification animation
    Animated.loop(
      Animated.sequence([
        Animated.timing(notificationAnim, {
          toValue: 1,
          duration: 1000,
          useNativeDriver: true,
        }),
        Animated.timing(notificationAnim, {
          toValue: 0,
          duration: 1000,
          useNativeDriver: true,
        }),
      ])
    ).start();
  }, []);

  const handleDownload = () => {
    Linking.openURL(DOWNLOAD_URL);
  };

  const calculateEarnings = (hours) => {
    return (hours * HOURLY_RATE * 30).toLocaleString('en-IN');
  };

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" />
      
      {/* Background Grid */}
      <View style={styles.gridBackground} />
      
      {/* Ambient Glow */}
      <Animated.View style={[styles.ambientGlow, { opacity: pulseAnim }]} />

      {/* Floating Background Elements */}
      <Animated.View style={[styles.floatingBlob1, { transform: [{ translateY: floatAnim }] }]} />
      <Animated.View style={[styles.floatingBlob2, { 
        transform: [{ 
          translateY: floatAnim.interpolate({
            inputRange: [-20, 0],
            outputRange: [0, -20]
          })
        }] 
      }]} />

      {/* Fixed Navigation */}
      <View style={styles.navContainer}>
        <View style={styles.glassCard}>
          <View style={styles.navContent}>
            <View style={styles.logoContainer}>
              <View style={styles.logoBox}>
                <Text style={styles.logoText}>LB</Text>
              </View>
              <Text style={styles.logoTitle}>
                Local<Text style={styles.brandText}>Buddy</Text>
              </Text>
            </View>
            <TouchableOpacity 
              style={styles.getAppButton}
              onPress={handleDownload}
              activeOpacity={0.8}
            >
              <Text style={styles.getAppButtonText}>Get App</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>

      <ScrollView 
        style={styles.scrollView}
        showsVerticalScrollIndicator={false}
        contentContainerStyle={styles.scrollContent}
      >
        {/* Hero Section */}
        <Animated.View 
          style={[
            styles.heroSection,
            { 
              opacity: fadeAnim,
              transform: [{ translateY: slideAnim }]
            }
          ]}
        >
          {/* Live Badge */}
          <View style={styles.liveBadge}>
            <View style={styles.pingContainer}>
              <Animated.View style={[styles.pingOuter, { opacity: pulseAnim }]} />
              <View style={styles.pingInner} />
            </View>
            <Text style={styles.liveBadgeText}>Live in Balotra v1.0</Text>
          </View>

          {/* Main Heading */}
          <Text style={styles.mainHeading}>Your City.</Text>
          <View style={styles.typingContainer}>
            <LinearGradient
              colors={['#60a5fa', '#a78bfa']}
              start={{ x: 0, y: 0 }}
              end={{ x: 1, y: 0 }}
              style={styles.gradientText}
            >
              <Text style={styles.gradientHeading}>{displayText}</Text>
            </LinearGradient>
            <Text style={styles.cursor}>|</Text>
          </View>

          {/* Subtitle */}
          <Text style={styles.subtitle}>
            The first hyper-local task marketplace for Tier-2 cities. Connect with student helpers instantly.
          </Text>

          {/* Download Button */}
          <TouchableOpacity 
            style={styles.downloadButton}
            onPress={handleDownload}
            activeOpacity={0.9}
          >
            <LinearGradient
              colors={['#3b82f6', '#2563eb']}
              style={styles.downloadGradient}
            >
              <View style={styles.downloadContent}>
                <MaterialCommunityIcons name="android" size={32} color="white" />
                <View style={styles.downloadTextContainer}>
                  <Text style={styles.downloadLabel}>Official Build</Text>
                  <Text style={styles.downloadTitle}>Download APK</Text>
                </View>
              </View>
              <MaterialCommunityIcons name="download" size={24} color="white" />
            </LinearGradient>
          </TouchableOpacity>

          {/* Security Badge */}
          <View style={styles.securityBadge}>
            <MaterialCommunityIcons name="shield-check" size={16} color="#10b981" />
            <Text style={styles.securityText}>Safe & Signed • 20MB</Text>
          </View>

          {/* Phone Mockup */}
          <Animated.View style={[styles.phoneMockup, { transform: [{ translateY: floatAnim }] }]}>
            <View style={styles.phoneFrame}>
              <View style={styles.phoneContent}>
                {/* Status Bar */}
                <View style={styles.phoneStatusBar}>
                  <Text style={styles.phoneLocation}>Balotra, RJ</Text>
                  <View style={styles.phoneAvatar} />
                </View>

                {/* Cards */}
                <View style={styles.phoneCards}>
                  <View style={styles.phoneCard}>
                    <View style={styles.cardHeader}>
                      <View style={[styles.cardIcon, { backgroundColor: 'rgba(16, 185, 129, 0.2)' }]}>
                        <MaterialCommunityIcons name="cart" size={16} color="#10b981" />
                      </View>
                      <Text style={styles.cardTime}>Now</Text>
                    </View>
                    <View style={styles.cardLine1} />
                    <View style={styles.cardLine2} />
                  </View>

                  <View style={[styles.phoneCard, { opacity: 0.6 }]}>
                    <View style={styles.cardHeader}>
                      <View style={[styles.cardIcon, { backgroundColor: 'rgba(168, 85, 247, 0.2)' }]}>
                        <MaterialCommunityIcons name="pill" size={16} color="#a855f7" />
                      </View>
                    </View>
                    <View style={styles.cardLine1} />
                  </View>
                </View>

                {/* Bottom Navigation */}
                <View style={styles.phoneBottomNav}>
                  <MaterialCommunityIcons name="home" size={20} color="#3b82f6" />
                  <MaterialCommunityIcons name="magnify" size={20} color="#6b7280" />
                  <MaterialCommunityIcons name="chat" size={20} color="#6b7280" />
                  <MaterialCommunityIcons name="account" size={20} color="#6b7280" />
                </View>

                {/* Notification Overlay */}
                <Animated.View style={[styles.phoneNotification, { opacity: notificationAnim }]}>
                  <View style={styles.notificationContent}>
                    <View style={styles.notificationIcon}>
                      <Text style={styles.notificationRupee}>₹</Text>
                    </View>
                    <View>
                      <Text style={styles.notificationTitle}>Payment Received</Text>
                      <Text style={styles.notificationSubtitle}>You earned ₹150</Text>
                    </View>
                  </View>
                </Animated.View>
              </View>
            </View>
          </Animated.View>
        </Animated.View>

        {/* Why Local Buddy Section */}
        <View style={styles.featuresSection}>
          <Text style={styles.sectionTitle}>
            Why <Text style={styles.brandText}>Local Buddy?</Text>
          </Text>

          <View style={styles.featureCard}>
            <View style={[styles.featureIcon, { backgroundColor: 'rgba(59, 130, 246, 0.2)' }]}>
              <MaterialCommunityIcons name="rocket-launch" size={24} color="#60a5fa" />
            </View>
            <View style={styles.featureContent}>
              <Text style={styles.featureTitle}>Instant Matching</Text>
              <Text style={styles.featureDescription}>AI finds the nearest helper in seconds.</Text>
            </View>
          </View>

          <View style={styles.featureCard}>
            <View style={[styles.featureIcon, { backgroundColor: 'rgba(168, 85, 247, 0.2)' }]}>
              <MaterialCommunityIcons name="shield-check" size={24} color="#a78bfa" />
            </View>
            <View style={styles.featureContent}>
              <Text style={styles.featureTitle}>Verified Students</Text>
              <Text style={styles.featureDescription}>ID checked for total safety.</Text>
            </View>
          </View>

          <View style={styles.featureCard}>
            <View style={[styles.featureIcon, { backgroundColor: 'rgba(249, 115, 22, 0.2)' }]}>
              <MaterialCommunityIcons name="trophy" size={24} color="#fb923c" />
            </View>
            <View style={styles.featureContent}>
              <Text style={styles.featureTitle}>Gamified</Text>
              <Text style={styles.featureDescription}>Earn Karma Points & level up.</Text>
            </View>
          </View>
        </View>

        {/* Calculator Section */}
        <View style={styles.calculatorSection}>
          <View style={styles.calculatorCard}>
            <Text style={styles.calculatorTitle}>Estimate Earnings</Text>

            <View style={styles.earningsDisplay}>
              <Text style={styles.earningsLabel}>Potential Monthly Income</Text>
              <View style={styles.earningsAmountContainer}>
                <Text style={styles.earningsAmount}>₹{calculateEarnings(hoursValue)}</Text>
              </View>
              <Text style={styles.earningsNote}>Working {hoursValue} hr/day</Text>
            </View>

            <View style={styles.sliderContainer}>
              <View style={styles.sliderTrack}>
                <View style={[styles.sliderFill, { width: `${hoursValue * 10}%` }]} />
              </View>
              <View style={styles.sliderButtons}>
                {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((hour) => (
                  <TouchableOpacity
                    key={hour}
                    style={[
                      styles.sliderButton,
                      hoursValue === hour && styles.sliderButtonActive
                    ]}
                    onPress={() => setHoursValue(hour)}
                  >
                    <Text style={[
                      styles.sliderButtonText,
                      hoursValue === hour && styles.sliderButtonTextActive
                    ]}>
                      {hour}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
              <View style={styles.sliderLabels}>
                <Text style={styles.sliderLabel}>Part Time</Text>
                <Text style={styles.sliderLabel}>Full Time</Text>
              </View>
            </View>
          </View>
        </View>

        {/* Get Started Section */}
        <View style={styles.getStartedSection}>
          <Text style={styles.sectionTitle}>Get Started</Text>

          <View style={styles.stepsContainer}>
            <View style={styles.stepLine} />

            <View style={styles.step}>
              <View style={[styles.stepNumber, styles.stepNumberActive]}>
                <Text style={styles.stepNumberText}>1</Text>
              </View>
              <View style={styles.stepCard}>
                <Text style={styles.stepTitle}>Download APK</Text>
                <Text style={styles.stepDescription}>Click the button below to get the file.</Text>
              </View>
            </View>

            <View style={styles.step}>
              <View style={styles.stepNumber}>
                <Text style={styles.stepNumberText}>2</Text>
              </View>
              <View style={styles.stepCard}>
                <Text style={styles.stepTitle}>Install</Text>
                <Text style={styles.stepDescription}>Allow installation from browser if asked.</Text>
              </View>
            </View>

            <View style={styles.step}>
              <View style={styles.stepNumber}>
                <Text style={styles.stepNumberText}>3</Text>
              </View>
              <View style={styles.stepCard}>
                <Text style={styles.stepTitle}>Login</Text>
                <Text style={styles.stepDescription}>Use your mobile number to sign up.</Text>
              </View>
            </View>
          </View>

          <TouchableOpacity 
            style={styles.finalDownloadButton}
            onPress={handleDownload}
            activeOpacity={0.8}
          >
            <MaterialCommunityIcons name="download" size={24} color="#000" />
            <Text style={styles.finalDownloadText}>Download Now</Text>
          </TouchableOpacity>

          <Text style={styles.safetyNote}>Safe • Secure • Verified</Text>
        </View>
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#030712',
  },
  gridBackground: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    opacity: 0.5,
  },
  ambientGlow: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    width: width * 2,
    height: height * 2,
    marginLeft: -width,
    marginTop: -height,
    backgroundColor: 'rgba(59, 130, 246, 0.15)',
    borderRadius: width,
  },
  floatingBlob1: {
    position: 'absolute',
    top: 80,
    right: -100,
    width: 256,
    height: 256,
    backgroundColor: 'rgba(168, 85, 247, 0.2)',
    borderRadius: 128,
    opacity: 0.5,
  },
  floatingBlob2: {
    position: 'absolute',
    bottom: 80,
    left: -100,
    width: 256,
    height: 256,
    backgroundColor: 'rgba(59, 130, 246, 0.2)',
    borderRadius: 128,
    opacity: 0.5,
  },
  navContainer: {
    position: 'absolute',
    top: 40,
    left: 16,
    right: 16,
    zIndex: 50,
  },
  glassCard: {
    backgroundColor: 'rgba(17, 24, 39, 0.6)',
    borderRadius: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 8 },
    shadowOpacity: 0.3,
    shadowRadius: 32,
  },
  navContent: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  logoContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  logoBox: {
    width: 32,
    height: 32,
    borderRadius: 8,
    backgroundColor: '#3b82f6',
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: '#3b82f6',
    shadowOffset: { width: 0, height: 0 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  logoText: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 14,
  },
  logoTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },
  brandText: {
    color: '#3b82f6',
  },
  getAppButton: {
    backgroundColor: 'white',
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 8,
  },
  getAppButtonText: {
    color: '#000',
    fontWeight: 'bold',
    fontSize: 12,
  },
  scrollView: {
    flex: 1,
  },
  scrollContent: {
    paddingTop: 120,
    paddingBottom: 60,
  },
  heroSection: {
    paddingHorizontal: 16,
    alignItems: 'center',
    marginBottom: 80,
  },
  liveBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    paddingHorizontal: 12,
    paddingVertical: 6,
    backgroundColor: 'rgba(17, 24, 39, 0.6)',
    borderRadius: 20,
    borderWidth: 1,
    borderColor: 'rgba(59, 130, 246, 0.2)',
    marginBottom: 32,
  },
  pingContainer: {
    width: 8,
    height: 8,
    position: 'relative',
  },
  pingOuter: {
    position: 'absolute',
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#60a5fa',
  },
  pingInner: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#3b82f6',
  },
  liveBadgeText: {
    color: '#93c5fd',
    fontSize: 12,
    fontWeight: '500',
  },
  mainHeading: {
    fontSize: 48,
    fontWeight: 'bold',
    color: 'white',
    textAlign: 'center',
    marginBottom: 8,
  },
  typingContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 24,
  },
  gradientText: {
    paddingHorizontal: 0,
  },
  gradientHeading: {
    fontSize: 48,
    fontWeight: 'bold',
    color: 'transparent',
  },
  cursor: {
    fontSize: 48,
    fontWeight: 'bold',
    color: 'white',
    marginLeft: 4,
  },
  subtitle: {
    fontSize: 18,
    color: '#9ca3af',
    textAlign: 'center',
    maxWidth: 400,
    marginBottom: 40,
    lineHeight: 28,
  },
  downloadButton: {
    width: '100%',
    maxWidth: 400,
    borderRadius: 16,
    overflow: 'hidden',
    marginBottom: 16,
  },
  downloadGradient: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 24,
    paddingVertical: 16,
  },
  downloadContent: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 16,
  },
  downloadTextContainer: {
    alignItems: 'flex-start',
  },
  downloadLabel: {
    fontSize: 10,
    fontWeight: 'bold',
    color: '#93c5fd',
    textTransform: 'uppercase',
  },
  downloadTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'white',
  },
  securityBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    marginBottom: 64,
  },
  securityText: {
    fontSize: 12,
    color: '#6b7280',
  },
  phoneMockup: {
    width: 280,
    height: 550,
    backgroundColor: '#1f2937',
    borderRadius: 40,
    borderWidth: 4,
    borderColor: '#374151',
    overflow: 'hidden',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 20 },
    shadowOpacity: 0.5,
    shadowRadius: 40,
  },
  phoneFrame: {
    flex: 1,
  },
  phoneContent: {
    flex: 1,
    backgroundColor: '#0f172a',
  },
  phoneStatusBar: {
    height: 64,
    backgroundColor: '#3b82f6',
    paddingHorizontal: 16,
    flexDirection: 'row',
    alignItems: 'flex-end',
    justifyContent: 'space-between',
    paddingBottom: 12,
  },
  phoneLocation: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 14,
  },
  phoneAvatar: {
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
  },
  phoneCards: {
    padding: 16,
    gap: 12,
  },
  phoneCard: {
    backgroundColor: 'rgba(31, 41, 55, 0.5)',
    borderRadius: 12,
    padding: 12,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.05)',
    height: 96,
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 8,
  },
  cardIcon: {
    width: 32,
    height: 32,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  cardTime: {
    fontSize: 10,
    color: '#9ca3af',
  },
  cardLine1: {
    height: 8,
    width: 96,
    backgroundColor: '#374151',
    borderRadius: 4,
    marginBottom: 8,
  },
  cardLine2: {
    height: 8,
    width: 64,
    backgroundColor: '#374151',
    borderRadius: 4,
  },
  phoneBottomNav: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 64,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-around',
    borderTopWidth: 1,
    borderTopColor: 'rgba(255, 255, 255, 0.05)',
  },
  phoneNotification: {
    position: 'absolute',
    top: 80,
    left: 16,
    right: 16,
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
    backdropFilter: 'blur(12px)',
    borderRadius: 12,
    padding: 12,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.1)',
  },
  notificationContent: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  notificationIcon: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: '#10b981',
    alignItems: 'center',
    justifyContent: 'center',
  },
  notificationRupee: {
    fontSize: 12,
    fontWeight: 'bold',
    color: 'white',
  },
  notificationTitle: {
    fontSize: 12,
    fontWeight: 'bold',
    color: 'white',
  },
  notificationSubtitle: {
    fontSize: 12,
    color: '#d1d5db',
  },
  featuresSection: {
    paddingHorizontal: 16,
    marginBottom: 80,
  },
  sectionTitle: {
    fontSize: 30,
    fontWeight: 'bold',
    color: 'white',
    textAlign: 'center',
    marginBottom: 48,
  },
  featureCard: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 16,
    backgroundColor: 'rgba(17, 24, 39, 0.6)',
    borderRadius: 16,
    padding: 24,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    marginBottom: 16,
  },
  featureIcon: {
    width: 48,
    height: 48,
    borderRadius: 24,
    alignItems: 'center',
    justifyContent: 'center',
  },
  featureContent: {
    flex: 1,
  },
  featureTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
    marginBottom: 4,
  },
  featureDescription: {
    fontSize: 14,
    color: '#9ca3af',
  },
  calculatorSection: {
    paddingHorizontal: 16,
    marginBottom: 80,
  },
  calculatorCard: {
    backgroundColor: 'rgba(17, 24, 39, 0.6)',
    borderRadius: 24,
    padding: 24,
    borderWidth: 1,
    borderColor: 'rgba(59, 130, 246, 0.3)',
  },
  calculatorTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: 'white',
    textAlign: 'center',
    marginBottom: 24,
  },
  earningsDisplay: {
    backgroundColor: 'rgba(17, 24, 39, 0.5)',
    borderRadius: 16,
    padding: 24,
    alignItems: 'center',
    marginBottom: 24,
  },
  earningsLabel: {
    fontSize: 14,
    color: '#9ca3af',
    marginBottom: 8,
  },
  earningsAmountContainer: {
    marginVertical: 8,
  },
  earningsAmount: {
    fontSize: 36,
    fontWeight: 'bold',
    color: 'white',
  },
  earningsNote: {
    fontSize: 12,
    color: '#60a5fa',
    fontWeight: '500',
    marginTop: 8,
  },
  sliderContainer: {
    paddingHorizontal: 8,
  },
  sliderTrack: {
    height: 4,
    backgroundColor: '#1e293b',
    borderRadius: 2,
    marginBottom: 16,
  },
  sliderFill: {
    height: 4,
    backgroundColor: '#3b82f6',
    borderRadius: 2,
  },
  sliderButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 24,
  },
  sliderButton: {
    width: 32,
    height: 32,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 16,
    backgroundColor: 'rgba(59, 130, 246, 0.1)',
  },
  sliderButtonActive: {
    backgroundColor: '#3b82f6',
  },
  sliderButtonText: {
    fontSize: 12,
    color: '#9ca3af',
    fontWeight: 'bold',
  },
  sliderButtonTextActive: {
    color: 'white',
  },
  sliderLabels: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  sliderLabel: {
    fontSize: 10,
    color: '#6b7280',
    fontWeight: 'bold',
    textTransform: 'uppercase',
  },
  getStartedSection: {
    paddingHorizontal: 16,
    marginBottom: 80,
  },
  stepsContainer: {
    position: 'relative',
    paddingLeft: 32,
    marginBottom: 48,
  },
  stepLine: {
    position: 'absolute',
    left: 16,
    top: 0,
    bottom: 0,
    width: 2,
    backgroundColor: '#374151',
  },
  step: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: 40,
  },
  stepNumber: {
    position: 'absolute',
    left: -40,
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: '#374151',
    borderWidth: 4,
    borderColor: '#111827',
    alignItems: 'center',
    justifyContent: 'center',
  },
  stepNumberActive: {
    backgroundColor: '#3b82f6',
  },
  stepNumberText: {
    fontSize: 12,
    fontWeight: 'bold',
    color: 'white',
  },
  stepCard: {
    flex: 1,
    backgroundColor: 'rgba(17, 24, 39, 0.6)',
    borderRadius: 12,
    padding: 20,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
  },
  stepTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
    marginBottom: 4,
  },
  stepDescription: {
    fontSize: 14,
    color: '#9ca3af',
  },
  finalDownloadButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 12,
    backgroundColor: 'white',
    paddingVertical: 16,
    borderRadius: 12,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 8 },
    shadowOpacity: 0.2,
    shadowRadius: 16,
  },
  finalDownloadText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#000',
  },
  safetyNote: {
    fontSize: 12,
    color: '#6b7280',
    textAlign: 'center',
  },
});
