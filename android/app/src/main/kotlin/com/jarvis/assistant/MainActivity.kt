package com.jarvis.assistant

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jarvis.assistant.ai.BrainManager
import com.jarvis.assistant.model.SystemStatus
import com.jarvis.assistant.ui.components.JarvisHUD
import com.jarvis.assistant.ui.theme.JarvisBlack
import com.jarvis.assistant.ui.theme.JarvisTheme
import com.jarvis.assistant.util.SystemMonitor
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var brainManager: BrainManager
    private lateinit var systemMonitor: SystemMonitor
    
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize components
        brainManager = BrainManager(this)
        systemMonitor = SystemMonitor(this)
        
        // Setup brain callbacks
        brainManager.setOnExecutionCompleteListener { success, message ->
            lifecycleScope.launch {
                Toast.makeText(
                    this@MainActivity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        
        setContent {
            JarvisTheme {
                val systemUiController = rememberSystemUiController()
                
                // Hide system UI for immersive experience
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = JarvisBlack,
                        darkIcons = false
                    )
                }
                
                // Permission handling
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                    )
                )
                
                LaunchedEffect(Unit) {
                    if (!permissionsState.allPermissionsGranted) {
                        permissionsState.launchMultiplePermissionRequest()
                    }
                }
                
                // System status updates
                var systemStatus by remember { mutableStateOf(SystemStatus()) }
                var statusMessage by remember { mutableStateOf("") }
                
                LaunchedEffect(Unit) {
                    while (isActive) {
                        systemStatus = systemMonitor.getSystemStatus()
                        delay(1000) // Update every second
                    }
                }
                
                JarvisHUD(
                    systemStatus = systemStatus,
                    onCommandSubmit = { command ->
                        statusMessage = "Processing: $command"
                        brainManager.processCommand(command)
                    },
                    statusMessage = statusMessage,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    
    override fun onDestroy() {
        brainManager.cleanup()
        super.onDestroy()
    }
}
