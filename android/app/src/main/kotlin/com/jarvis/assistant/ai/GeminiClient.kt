package com.jarvis.assistant.ai

import com.google.gson.Gson
import com.jarvis.assistant.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Gemini API Interface
 */
interface GeminiApiService {
    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}

/**
 * Client for interacting with Google's Gemini API
 */
class GeminiClient {
    
    companion object {
        private const val BASE_URL = "https://generativelanguage.googleapis.com/"
        private const val API_KEY = "AIzaSyA0tkbFf1fvrf9ba94RszVhw3W2IOZZBBU"
        
        private const val SYSTEM_INSTRUCTION = """
You are JARVIS, an advanced AI assistant embedded in an Android device. Your role is to understand natural language commands and translate them into actionable system operations.

When the user gives you a command, analyze the intent and respond ONLY with a valid JSON object in this exact format:
{
  "action_type": "the_action_to_perform",
  "target_app_package": "package.name.if.needed",
  "search_query": "search terms if needed",
  "explanation": "brief explanation of what you're doing",
  "parameters": {}
}

Available action_types:
- open_app: Open an application (provide target_app_package)
- search_web: Search on Google (provide search_query)
- search_youtube: Search on YouTube (provide search_query)
- flashlight_on: Turn on flashlight
- flashlight_off: Turn off flashlight
- wifi_on: Enable WiFi
- wifi_off: Disable WiFi
- bluetooth_on: Enable Bluetooth
- bluetooth_off: Disable Bluetooth
- mobile_data_on: Enable mobile data
- mobile_data_off: Disable mobile data
- dnd_on: Enable Do Not Disturb mode
- dnd_off: Disable Do Not Disturb mode
- brightness_up: Increase screen brightness
- brightness_down: Decrease screen brightness
- volume_up: Increase volume
- volume_down: Decrease volume
- lock_device: Lock the device
- kill_apps: Kill background apps to free RAM
- screenshot: Take a screenshot
- unknown: When you cannot determine the intent

Common app packages:
- YouTube: com.google.android.youtube
- Chrome: com.android.chrome
- Gmail: com.google.android.gm
- Maps: com.google.android.apps.maps
- Camera: com.android.camera2 or com.google.android.GoogleCamera
- Settings: com.android.settings
- Phone: com.google.android.dialer or com.android.dialer
- Messages: com.google.android.apps.messaging
- WhatsApp: com.whatsapp
- Instagram: com.instagram.android
- Facebook: com.facebook.katana
- Twitter: com.twitter.android
- Spotify: com.spotify.music

Examples:
User: "I'm stuck on this coding error, help me"
Response: {"action_type":"search_youtube","target_app_package":"com.google.android.youtube","search_query":"coding error help tutorial","explanation":"Opening YouTube to search for coding help"}

User: "It's too dark in here"
Response: {"action_type":"flashlight_on","target_app_package":"","search_query":"","explanation":"Turning on flashlight"}

User: "I want to sleep, don't disturb me"
Response: {"action_type":"dnd_on","target_app_package":"","search_query":"","explanation":"Enabling Do Not Disturb mode"}

User: "Open Instagram"
Response: {"action_type":"open_app","target_app_package":"com.instagram.android","search_query":"","explanation":"Opening Instagram"}

User: "My phone is slow, clean it up"
Response: {"action_type":"kill_apps","target_app_package":"","search_query":"","explanation":"Killing background apps to free up memory"}

Respond ONLY with the JSON object, no additional text.
"""
    }
    
    private val api: GeminiApiService
    private val gson = Gson()
    
    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        
        api = retrofit.create(GeminiApiService::class.java)
    }
    
    /**
     * Process a user command and get an action result
     */
    suspend fun processCommand(userInput: String): ActionResult {
        return try {
            val prompt = "$SYSTEM_INSTRUCTION\n\nUser command: $userInput"
            
            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(Part(prompt))
                    )
                ),
                generationConfig = GenerationConfig(
                    temperature = 0.3,
                    maxOutputTokens = 512,
                    topP = 0.8,
                    topK = 40
                )
            )
            
            val response = api.generateContent(API_KEY, request)
            
            if (response.isSuccessful && response.body() != null) {
                val geminiResponse = response.body()!!
                
                if (geminiResponse.error != null) {
                    return ActionResult(
                        actionType = ActionType.UNKNOWN,
                        explanation = "API Error: ${geminiResponse.error.message}"
                    )
                }
                
                val text = geminiResponse.candidates?.firstOrNull()
                    ?.content?.parts?.firstOrNull()?.text ?: ""
                
                return parseActionFromResponse(text)
            } else {
                return ActionResult(
                    actionType = ActionType.UNKNOWN,
                    explanation = "API request failed: ${response.code()}"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ActionResult(
                actionType = ActionType.UNKNOWN,
                explanation = "Error: ${e.message}"
            )
        }
    }
    
    /**
     * Parse JSON response from Gemini into ActionResult
     */
    private fun parseActionFromResponse(responseText: String): ActionResult {
        return try {
            // Clean up the response text
            val cleanedText = responseText.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()
            
            // Try to parse the JSON
            gson.fromJson(cleanedText, ActionResult::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback to simple keyword matching if JSON parsing fails
            fallbackActionDetection(responseText)
        }
    }
    
    /**
     * Fallback method using simple keyword matching
     */
    private fun fallbackActionDetection(text: String): ActionResult {
        val lowerText = text.lowercase()
        
        return when {
            lowerText.contains("flashlight") && lowerText.contains("on") ->
                ActionResult(actionType = ActionType.FLASHLIGHT_ON, explanation = "Turning on flashlight")
            
            lowerText.contains("flashlight") && lowerText.contains("off") ->
                ActionResult(actionType = ActionType.FLASHLIGHT_OFF, explanation = "Turning off flashlight")
            
            lowerText.contains("wifi") && lowerText.contains("on") ->
                ActionResult(actionType = ActionType.WIFI_ON, explanation = "Enabling WiFi")
            
            lowerText.contains("wifi") && lowerText.contains("off") ->
                ActionResult(actionType = ActionType.WIFI_OFF, explanation = "Disabling WiFi")
            
            lowerText.contains("bluetooth") && lowerText.contains("on") ->
                ActionResult(actionType = ActionType.BLUETOOTH_ON, explanation = "Enabling Bluetooth")
            
            lowerText.contains("bluetooth") && lowerText.contains("off") ->
                ActionResult(actionType = ActionType.BLUETOOTH_OFF, explanation = "Disabling Bluetooth")
            
            lowerText.contains("do not disturb") || lowerText.contains("dnd") ->
                ActionResult(actionType = ActionType.DO_NOT_DISTURB_ON, explanation = "Enabling Do Not Disturb")
            
            lowerText.contains("lock") ->
                ActionResult(actionType = ActionType.LOCK_DEVICE, explanation = "Locking device")
            
            else ->
                ActionResult(actionType = ActionType.UNKNOWN, explanation = "Could not understand command")
        }
    }
}
