package com.example.fillmycycle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fillmycycle.ui.theme.FillMyCycleTheme
import com.example.fillmycycle.ui.theme.GenderTheme


@Composable
fun FMCScreen() {
    var gender by remember { mutableStateOf(GenderTheme.GIRL) }
    var cycle1BoxCount by remember { mutableStateOf("5") } // User input for Cycle 1 count
    var cycle2EndCount by remember { mutableStateOf("10") } // User input for Cycle 2 end count

    val cycle2StartCount = cycle1BoxCount.toIntOrNull() ?: 5
    val cycle2BoxCount = (cycle2EndCount.toIntOrNull() ?: 10) - cycle2StartCount

    FillMyCycleTheme(gender) { // Apply theme dynamically
        Scaffold(
            topBar = {
                CycleTopAppBar(gender) {
                    gender = if (gender == GenderTheme.BOY) GenderTheme.GIRL else GenderTheme.BOY
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {

                FillMyCycleScreen()

            }
        }
    }
}


@Preview
@Composable
fun FMCScreenPreview() {
    FMCScreen()
}