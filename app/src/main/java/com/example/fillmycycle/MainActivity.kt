package com.example.fillmycycle



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.fillmycycle.ui.theme.FillMyCycleTheme
import com.example.fillmycycle.ui.theme.GenderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedTheme by remember { mutableStateOf(GenderTheme.GIRL) }

            FillMyCycleTheme(gender = selectedTheme) {
                // App UI goes here
//                FillMyCycleScreen(
//                    genderTheme = selectedTheme,
//                    onThemeToggle = { selectedTheme = it }
//                )
            }
        }
    }
}
