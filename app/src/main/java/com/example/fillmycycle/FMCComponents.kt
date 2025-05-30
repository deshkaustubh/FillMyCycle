package com.example.fillmycycle

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.fillmycycle.ui.theme.GenderTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CycleTopAppBar(
    gender: GenderTheme,
    onModeToggle: () -> Unit
) {
    TopAppBar(
        title = { Text("Fill My Cycle", modifier = Modifier) },
        navigationIcon = {
//            IconButton(onClick = { /* Handle menu click */ }) {
//                Icon(Icons.Filled.Menu, contentDescription = "Menu")
//            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = onModeToggle) {
                Icon(
                    imageVector = if (gender == GenderTheme.BOY) Icons.Filled.Male else Icons.Filled.Female,
                    contentDescription = if (gender == GenderTheme.BOY) "Boy Mode" else "Girl Mode",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}




@Preview
@Composable
fun FMCComponentPreview(){
    CycleTopAppBar(gender = GenderTheme.BOY) { }
}