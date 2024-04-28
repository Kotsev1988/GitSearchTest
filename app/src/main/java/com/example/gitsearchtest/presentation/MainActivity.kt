package com.example.gitsearchtest.presentation

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitsearchtest.presentation.MainViewModel
import com.example.gitsearchtest.presentation.SharedViewModel
import com.example.gitsearchtest.presentation.main.MainScreen
import com.example.gitsearchtest.ui.theme.GitSearchTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _isDownloadSuccess = mutableStateOf(false)

    private val loadReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){

                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (id != -1L){
                    _isDownloadSuccess.value =  true
                }
            }
        }
    }
    private val intentFilter = IntentFilter("android.intent.action.DOWNLOAD_COMPLETE")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        registerReceiver(loadReceiver, intentFilter)
        setContent {
            GitSearchTestTheme {

                val mainViewModel: MainViewModel = hiltViewModel()
                val sharedViewModel: SharedViewModel = hiltViewModel()
                val repoData by sharedViewModel.repoName.collectAsState()
                val userData by sharedViewModel.userName.collectAsState()

                var startDestination by remember {
                    mutableStateOf("")
                }

                startDestination = "main"

                if (_isDownloadSuccess.value) {
                    CoroutineScope(Dispatchers.IO).launch {
                        mainViewModel.saveArchive(repoName = repoData, userName = userData)
                        _isDownloadSuccess.value = false
                    }
                }

                androidx.compose.material.Surface(
                    color = androidx.compose.material.MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable("main") {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(loadReceiver)
    }
}
