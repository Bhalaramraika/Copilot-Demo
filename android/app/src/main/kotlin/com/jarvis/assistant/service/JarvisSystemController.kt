package com.jarvis.assistant.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

/**
 * Accessibility Service for system-level control
 * This allows JARVIS to interact with other apps programmatically
 */
class JarvisSystemController : AccessibilityService() {
    
    private val TAG = "JarvisSystemController"
    
    companion object {
        private var instance: JarvisSystemController? = null
        
        fun getInstance(): JarvisSystemController? = instance
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        Log.d(TAG, "JARVIS System Controller Connected")
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Log accessibility events for debugging
        event?.let {
            Log.d(TAG, "Accessibility Event: ${it.eventType}, Package: ${it.packageName}")
        }
    }
    
    override fun onInterrupt() {
        Log.d(TAG, "JARVIS System Controller Interrupted")
    }
    
    override fun onUnbind(intent: android.content.Intent?): Boolean {
        instance = null
        return super.onUnbind(intent)
    }
    
    /**
     * Click on a specific point on the screen
     */
    fun clickAt(x: Float, y: Float): Boolean {
        val path = Path().apply {
            moveTo(x, y)
        }
        
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 100))
        
        return dispatchGesture(
            gestureBuilder.build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    Log.d(TAG, "Click gesture completed at ($x, $y)")
                }
                
                override fun onCancelled(gestureDescription: GestureDescription?) {
                    Log.w(TAG, "Click gesture cancelled")
                }
            },
            null
        )
    }
    
    /**
     * Swipe from one point to another
     */
    fun swipe(startX: Float, startY: Float, endX: Float, endY: Float, duration: Long = 300): Boolean {
        val path = Path().apply {
            moveTo(startX, startY)
            lineTo(endX, endY)
        }
        
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, duration))
        
        return dispatchGesture(
            gestureBuilder.build(),
            object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    Log.d(TAG, "Swipe gesture completed")
                }
                
                override fun onCancelled(gestureDescription: GestureDescription?) {
                    Log.w(TAG, "Swipe gesture cancelled")
                }
            },
            null
        )
    }
    
    /**
     * Get the current screen content
     */
    fun getScreenContent(): List<String> {
        val result = mutableListOf<String>()
        
        rootInActiveWindow?.let { root ->
            traverseNode(root, result)
        }
        
        return result
    }
    
    /**
     * Recursively traverse accessibility node tree
     */
    private fun traverseNode(node: AccessibilityNodeInfo?, result: MutableList<String>) {
        if (node == null) return
        
        node.text?.toString()?.let {
            if (it.isNotEmpty()) {
                result.add(it)
            }
        }
        
        node.contentDescription?.toString()?.let {
            if (it.isNotEmpty()) {
                result.add(it)
            }
        }
        
        for (i in 0 until node.childCount) {
            traverseNode(node.getChild(i), result)
        }
    }
    
    /**
     * Find and click on a node with specific text
     */
    fun clickNodeWithText(text: String): Boolean {
        rootInActiveWindow?.let { root ->
            return findAndClickNode(root, text)
        }
        return false
    }
    
    /**
     * Recursively find and click a node
     */
    private fun findAndClickNode(node: AccessibilityNodeInfo?, targetText: String): Boolean {
        if (node == null) return false
        
        val nodeText = node.text?.toString() ?: ""
        val contentDesc = node.contentDescription?.toString() ?: ""
        
        if (nodeText.contains(targetText, ignoreCase = true) ||
            contentDesc.contains(targetText, ignoreCase = true)) {
            return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
        
        for (i in 0 until node.childCount) {
            if (findAndClickNode(node.getChild(i), targetText)) {
                return true
            }
        }
        
        return false
    }
    
    /**
     * Type text (requires an EditText to be focused)
     */
    fun typeText(text: String): Boolean {
        rootInActiveWindow?.let { root ->
            val editText = findEditText(root)
            if (editText != null) {
                val arguments = android.os.Bundle().apply {
                    putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        text
                    )
                }
                return editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
            }
        }
        return false
    }
    
    /**
     * Find the first EditText node
     */
    private fun findEditText(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        
        if (node.className == "android.widget.EditText") {
            return node
        }
        
        for (i in 0 until node.childCount) {
            val result = findEditText(node.getChild(i))
            if (result != null) return result
        }
        
        return null
    }
    
    /**
     * Perform back action
     */
    fun performBack(): Boolean {
        return performGlobalAction(GLOBAL_ACTION_BACK)
    }
    
    /**
     * Perform home action
     */
    fun performHome(): Boolean {
        return performGlobalAction(GLOBAL_ACTION_HOME)
    }
    
    /**
     * Perform recent apps action
     */
    fun performRecents(): Boolean {
        return performGlobalAction(GLOBAL_ACTION_RECENTS)
    }
}
