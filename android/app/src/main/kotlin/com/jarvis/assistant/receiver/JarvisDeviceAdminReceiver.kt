package com.jarvis.assistant.receiver

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Device Admin Receiver for advanced device control (e.g., locking device)
 */
class JarvisDeviceAdminReceiver : DeviceAdminReceiver() {
    
    private val TAG = "JarvisDeviceAdmin"
    
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG, "Device Admin enabled")
        Toast.makeText(context, "JARVIS Device Admin Enabled", Toast.LENGTH_SHORT).show()
    }
    
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.d(TAG, "Device Admin disabled")
        Toast.makeText(context, "JARVIS Device Admin Disabled", Toast.LENGTH_SHORT).show()
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        if (intent.action == "com.jarvis.assistant.LOCK_DEVICE") {
            lockDevice(context)
        }
    }
    
    companion object {
        /**
         * Lock the device immediately
         */
        fun lockDevice(context: Context): Boolean {
            return try {
                val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                val componentName = ComponentName(context, JarvisDeviceAdminReceiver::class.java)
                
                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.lockNow()
                    true
                } else {
                    Log.w("JarvisDeviceAdmin", "Device admin not active, cannot lock device")
                    // Open settings to enable device admin
                    val adminIntent = Intent(android.app.admin.DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                        putExtra(android.app.admin.DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                        putExtra(
                            android.app.admin.DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Enable JARVIS as Device Admin to allow device locking"
                        )
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(adminIntent)
                    false
                }
            } catch (e: Exception) {
                Log.e("JarvisDeviceAdmin", "Error locking device", e)
                false
            }
        }
        
        /**
         * Check if device admin is enabled
         */
        fun isAdminActive(context: Context): Boolean {
            return try {
                val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                val componentName = ComponentName(context, JarvisDeviceAdminReceiver::class.java)
                devicePolicyManager.isAdminActive(componentName)
            } catch (e: Exception) {
                false
            }
        }
    }
}
