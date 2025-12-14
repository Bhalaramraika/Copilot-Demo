package com.jarvis.assistant.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Environment
import android.os.StatFs
import com.jarvis.assistant.model.SystemStatus
import java.io.File
import java.io.RandomAccessFile

/**
 * Monitors system status and provides metrics for the HUD
 */
class SystemMonitor(private val context: Context) {
    
    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    
    /**
     * Get current system status
     */
    fun getSystemStatus(): SystemStatus {
        return SystemStatus(
            cpuTemp = getCpuTemperature(),
            batteryLevel = getBatteryLevel(),
            batteryVoltage = getBatteryVoltage(),
            isCharging = isCharging(),
            storageUsed = getStorageUsed(),
            storageTotal = getStorageTotal(),
            networkUpload = 0f, // Network stats require special permissions
            networkDownload = 0f,
            ramUsed = getRamUsed(),
            ramTotal = getRamTotal()
        )
    }
    
    /**
     * Get CPU temperature (approximation)
     */
    private fun getCpuTemperature(): Float {
        return try {
            // Try to read from thermal zone
            val file = File("/sys/class/thermal/thermal_zone0/temp")
            if (file.exists()) {
                val temp = file.readText().trim().toFloatOrNull() ?: 0f
                // Temperature is usually in millidegrees
                temp / 1000f
            } else {
                // Fallback: estimate based on battery temperature
                val batteryTemp = getBatteryTemperature()
                batteryTemp + 5f // CPU is usually a bit hotter
            }
        } catch (e: Exception) {
            40f // Default fallback value
        }
    }
    
    /**
     * Get battery temperature
     */
    private fun getBatteryTemperature(): Float {
        return try {
            val batteryIntent = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val temp = batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
            temp / 10f // Temperature in tenths of a degree Celsius
        } catch (e: Exception) {
            35f
        }
    }
    
    /**
     * Get battery level percentage
     */
    private fun getBatteryLevel(): Int {
        return try {
            val batteryIntent = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            if (level >= 0 && scale > 0) {
                (level * 100 / scale)
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }
    }
    
    /**
     * Get battery voltage in millivolts
     */
    private fun getBatteryVoltage(): Int {
        return try {
            val batteryIntent = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            batteryIntent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
        } catch (e: Exception) {
            0
        }
    }
    
    /**
     * Check if device is charging
     */
    private fun isCharging(): Boolean {
        return try {
            val batteryIntent = context.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Get used storage in bytes
     */
    private fun getStorageUsed(): Long {
        return try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val total = stat.blockCountLong * stat.blockSizeLong
            val available = stat.availableBlocksLong * stat.blockSizeLong
            total - available
        } catch (e: Exception) {
            0L
        }
    }
    
    /**
     * Get total storage in bytes
     */
    private fun getStorageTotal(): Long {
        return try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            stat.blockCountLong * stat.blockSizeLong
        } catch (e: Exception) {
            0L
        }
    }
    
    /**
     * Get used RAM in bytes
     */
    private fun getRamUsed(): Long {
        return try {
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)
            memInfo.totalMem - memInfo.availMem
        } catch (e: Exception) {
            0L
        }
    }
    
    /**
     * Get total RAM in bytes
     */
    private fun getRamTotal(): Long {
        return try {
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)
            memInfo.totalMem
        } catch (e: Exception) {
            0L
        }
    }
}
