package com.jarvis.assistant.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the AI's decision on what action to take
 */
data class ActionResult(
    @SerializedName("action_type")
    val actionType: String = "",
    
    @SerializedName("target_app_package")
    val targetAppPackage: String = "",
    
    @SerializedName("search_query")
    val searchQuery: String = "",
    
    @SerializedName("explanation")
    val explanation: String = "",
    
    @SerializedName("parameters")
    val parameters: Map<String, String> = emptyMap()
)

/**
 * Action types that JARVIS can perform
 */
object ActionType {
    const val OPEN_APP = "open_app"
    const val SEARCH_WEB = "search_web"
    const val SEARCH_YOUTUBE = "search_youtube"
    const val FLASHLIGHT_ON = "flashlight_on"
    const val FLASHLIGHT_OFF = "flashlight_off"
    const val WIFI_ON = "wifi_on"
    const val WIFI_OFF = "wifi_off"
    const val BLUETOOTH_ON = "bluetooth_on"
    const val BLUETOOTH_OFF = "bluetooth_off"
    const val MOBILE_DATA_ON = "mobile_data_on"
    const val MOBILE_DATA_OFF = "mobile_data_off"
    const val DO_NOT_DISTURB_ON = "dnd_on"
    const val DO_NOT_DISTURB_OFF = "dnd_off"
    const val BRIGHTNESS_UP = "brightness_up"
    const val BRIGHTNESS_DOWN = "brightness_down"
    const val VOLUME_UP = "volume_up"
    const val VOLUME_DOWN = "volume_down"
    const val LOCK_DEVICE = "lock_device"
    const val KILL_BACKGROUND_APPS = "kill_apps"
    const val TAKE_SCREENSHOT = "screenshot"
    const val UNKNOWN = "unknown"
}

/**
 * Gemini API request format
 */
data class GeminiRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GenerationConfig(
    val temperature: Double = 0.7,
    val maxOutputTokens: Int = 256,
    val topP: Double = 0.8,
    val topK: Int = 40
)

/**
 * Gemini API response format
 */
data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val error: GeminiError? = null
)

data class Candidate(
    val content: Content? = null,
    val finishReason: String? = null
)

data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)

/**
 * System status data
 */
data class SystemStatus(
    val cpuTemp: Float = 0f,
    val batteryLevel: Int = 0,
    val batteryVoltage: Int = 0,
    val isCharging: Boolean = false,
    val storageUsed: Long = 0L,
    val storageTotal: Long = 0L,
    val networkUpload: Float = 0f,
    val networkDownload: Float = 0f,
    val ramUsed: Long = 0L,
    val ramTotal: Long = 0L
)
