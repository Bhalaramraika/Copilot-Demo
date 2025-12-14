package com.jarvis.assistant.util

import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.jarvis.assistant.model.ActionResult
import com.jarvis.assistant.model.ActionType

/**
 * Executes actions determined by the AI brain
 */
class ActionExecutor(private val context: Context) {
    
    private val TAG = "ActionExecutor"
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    
    /**
     * Execute the action based on AI decision
     */
    fun executeAction(action: ActionResult): Boolean {
        Log.d(TAG, "Executing action: ${action.actionType}")
        
        return try {
            when (action.actionType) {
                ActionType.OPEN_APP -> openApp(action.targetAppPackage)
                ActionType.SEARCH_WEB -> searchWeb(action.searchQuery)
                ActionType.SEARCH_YOUTUBE -> searchYouTube(action.searchQuery)
                ActionType.FLASHLIGHT_ON -> setFlashlight(true)
                ActionType.FLASHLIGHT_OFF -> setFlashlight(false)
                ActionType.WIFI_ON -> setWifi(true)
                ActionType.WIFI_OFF -> setWifi(false)
                ActionType.BLUETOOTH_ON -> setBluetooth(true)
                ActionType.BLUETOOTH_OFF -> setBluetooth(false)
                ActionType.DO_NOT_DISTURB_ON -> setDoNotDisturb(true)
                ActionType.DO_NOT_DISTURB_OFF -> setDoNotDisturb(false)
                ActionType.BRIGHTNESS_UP -> adjustBrightness(true)
                ActionType.BRIGHTNESS_DOWN -> adjustBrightness(false)
                ActionType.LOCK_DEVICE -> lockDevice()
                ActionType.KILL_BACKGROUND_APPS -> killBackgroundApps()
                else -> {
                    Log.w(TAG, "Unknown or unsupported action: ${action.actionType}")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error executing action: ${action.actionType}", e)
            false
        }
    }
    
    private fun openApp(packageName: String): Boolean {
        if (packageName.isEmpty()) return false
        
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                true
            } else {
                Log.w(TAG, "Cannot find launch intent for package: $packageName")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening app: $packageName", e)
            false
        }
    }
    
    private fun searchWeb(query: String): Boolean {
        if (query.isEmpty()) return false
        
        return try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.google.com/search?q=${Uri.encode(query)}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error searching web", e)
            false
        }
    }
    
    private fun searchYouTube(query: String): Boolean {
        if (query.isEmpty()) return false
        
        return try {
            // Try opening YouTube app first
            val appIntent = Intent(Intent.ACTION_SEARCH).apply {
                `package` = "com.google.android.youtube"
                putExtra("query", query)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            
            try {
                context.startActivity(appIntent)
                true
            } catch (e: Exception) {
                // Fallback to web browser
                val webIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://www.youtube.com/results?search_query=${Uri.encode(query)}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(webIntent)
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error searching YouTube", e)
            false
        }
    }
    
    private fun setFlashlight(enabled: Boolean): Boolean {
        return try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, enabled)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error controlling flashlight", e)
            false
        }
    }
    
    @Suppress("DEPRECATION")
    private fun setWifi(enabled: Boolean): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ requires user to manually enable/disable WiFi via Settings
                val intent = Intent(Settings.Panel.ACTION_WIFI).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                true
            } else {
                wifiManager.isWifiEnabled = enabled
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error controlling WiFi", e)
            false
        }
    }
    
    @Suppress("DEPRECATION")
    private fun setBluetooth(enabled: Boolean): Boolean {
        return try {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                Log.w(TAG, "Bluetooth not supported on this device")
                return false
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Android 12+ requires user to manually enable/disable Bluetooth via Settings
                val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                true
            } else {
                if (enabled) {
                    bluetoothAdapter.enable()
                } else {
                    bluetoothAdapter.disable()
                }
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error controlling Bluetooth", e)
            false
        }
    }
    
    private fun setDoNotDisturb(enabled: Boolean): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!notificationManager.isNotificationPolicyAccessGranted) {
                    // Open settings to grant permission
                    val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                    return false
                }
                
                val filter = if (enabled) {
                    NotificationManager.INTERRUPTION_FILTER_NONE
                } else {
                    NotificationManager.INTERRUPTION_FILTER_ALL
                }
                notificationManager.setInterruptionFilter(filter)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error controlling DND", e)
            false
        }
    }
    
    private fun adjustBrightness(increase: Boolean): Boolean {
        return try {
            if (!Settings.System.canWrite(context)) {
                // Open settings to grant permission
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return false
            }
            
            val currentBrightness = Settings.System.getInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                128
            )
            
            val newBrightness = if (increase) {
                (currentBrightness + 30).coerceAtMost(255)
            } else {
                (currentBrightness - 30).coerceAtLeast(0)
            }
            
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                newBrightness
            )
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error adjusting brightness", e)
            false
        }
    }
    
    private fun lockDevice(): Boolean {
        return try {
            // This requires Device Admin permission
            // Will be handled by JarvisDeviceAdminReceiver
            val intent = Intent(context, com.jarvis.assistant.receiver.JarvisDeviceAdminReceiver::class.java).apply {
                action = "com.jarvis.assistant.LOCK_DEVICE"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.sendBroadcast(intent)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error locking device", e)
            false
        }
    }
    
    private fun killBackgroundApps(): Boolean {
        return try {
            // This is limited on modern Android versions
            // Best we can do is suggest the user to clear recent apps
            val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error killing apps", e)
            false
        }
    }
}
