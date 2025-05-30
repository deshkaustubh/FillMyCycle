package com.example.fillmycycle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.example.fillmycycle.ui.theme.FillMyCycleTheme
import com.example.fillmycycle.ui.theme.GenderTheme


@Composable
fun FMCScreen() {
    var gender by remember { mutableStateOf(GenderTheme.GIRL) }

    FillMyCycleTheme(gender) {  // Apply theme dynamically
        Scaffold(
            topBar = { CycleTopAppBar(gender) {
                gender = if (gender == GenderTheme.BOY) GenderTheme.GIRL else GenderTheme.BOY
            } }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),  // Uses your defined background color
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (gender == GenderTheme.BOY) "Boy Mode Active" else "Girl Mode Active",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp
                )
            }
        }
    }
}



@Preview
@Composable
fun FMCScreenPreview() {
    FMCScreen()
}