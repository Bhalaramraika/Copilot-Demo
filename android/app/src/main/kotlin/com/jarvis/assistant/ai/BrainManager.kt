package com.jarvis.assistant.ai

import android.content.Context
import com.jarvis.assistant.model.ActionResult
import com.jarvis.assistant.util.ActionExecutor
import kotlinx.coroutines.*

/**
 * The Brain: Manages AI processing and action execution
 */
class BrainManager(private val context: Context) {
    
    private val geminiClient = GeminiClient()
    private val actionExecutor = ActionExecutor(context)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    private var onActionResult: ((ActionResult) -> Unit)? = null
    private var onExecutionComplete: ((Boolean, String) -> Unit)? = null
    
    /**
     * Set callback for when action result is received from AI
     */
    fun setOnActionResultListener(listener: (ActionResult) -> Unit) {
        onActionResult = listener
    }
    
    /**
     * Set callback for when action execution is complete
     */
    fun setOnExecutionCompleteListener(listener: (Boolean, String) -> Unit) {
        onExecutionComplete = listener
    }
    
    /**
     * Process a user command
     */
    fun processCommand(userInput: String) {
        scope.launch {
            try {
                // Step 1: Send to Gemini for intent understanding
                val actionResult = withContext(Dispatchers.IO) {
                    geminiClient.processCommand(userInput)
                }
                
                // Notify UI of the action decision
                onActionResult?.invoke(actionResult)
                
                // Step 2: Execute the action
                val success = withContext(Dispatchers.IO) {
                    actionExecutor.executeAction(actionResult)
                }
                
                // Notify execution complete
                val message = if (success) {
                    actionResult.explanation.ifEmpty { "Action completed successfully" }
                } else {
                    "Failed to execute: ${actionResult.actionType}"
                }
                onExecutionComplete?.invoke(success, message)
                
            } catch (e: Exception) {
                e.printStackTrace()
                onExecutionComplete?.invoke(false, "Error: ${e.message}")
            }
        }
    }
    
    /**
     * Cancel all pending operations
     */
    fun cleanup() {
        scope.cancel()
    }
}
