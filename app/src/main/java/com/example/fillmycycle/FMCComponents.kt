package com.example.fillmycycle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fillmycycle.ui.theme.GenderTheme


fun calculateAverage(values: List<String>): Double {
    val numericValues = values.mapNotNull { it.toDoubleOrNull() } // Convert valid inputs to numbers
    return if (numericValues.isNotEmpty()) {
        "%.2f".format(numericValues.average()).toDouble() // Compute average and round to 2 decimals
    } else {
        0.0 // Default to 0 if no valid inputs exist
    }
}

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

@Composable
fun BoxComponent(
    index: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 90.dp, height = 60.dp)
            .clip(RoundedCornerShape(35.dp))
            .padding(5.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) { // Allow only numeric input
                    onValueChange(newValue)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CycleComponent(
    cycleNumber: Int,
    startCount: Int, // Dynamically set in Cycle 2 from Cycle 1
    onEndValueChange: (Int) -> Unit,
    onAverageComputed: (Double) -> Unit // Pass average value to parent
) {
    var endValue by remember { mutableStateOf(startCount + 5) } // User sets this
    var values by remember { mutableStateOf(List((endValue - startCount) + 1) { "" }) }
    var manualAverage by remember { mutableStateOf("") }

    val computedAverage = calculateAverage(values) // Compute dynamically
    onAverageComputed(computedAverage) // Pass computed average to parent

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Cycle $cycleNumber",
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        // "Start to End" Input Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxComponent(0, startCount.toString()) { } // Auto-filled start count
            Text("to")
            BoxComponent(0, endValue.toString()) { newEndValue ->
                if (newEndValue.all { it.isDigit() }) {
                    endValue = newEndValue.toInt()
                    values = List((endValue - startCount) + 1) { "" } // Update dynamically
                    onEndValueChange(endValue) // Pass updated count to parent
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Centering the cycle boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                values.forEachIndexed { index, value ->
                    BoxComponent(index, value) { newValue ->
                        values = values.toMutableList().apply { set(index, newValue) }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // AVG Label Outside the Box
        Text(
            text = "Average:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        BoxComponent(0, manualAverage.ifEmpty { computedAverage.toString() }) { newValue ->
            manualAverage = newValue // Allows manual override
        }
    }
}

@Composable
fun CycleBar() {
    var cycle1EndValue by remember { mutableStateOf(1) }
    var cycle1Average by remember { mutableStateOf(0.0) }
    var cycle2EndValue by remember { mutableStateOf(1) }
    var cycle2Average by remember { mutableStateOf(0.0) }

    val cycle2StartCount = cycle1EndValue + 1 // Auto-set for Cycle 2

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.weight(1f)) {
            CycleComponent(
                cycleNumber = 1,
                startCount = 1, // Always starts at 1
                onEndValueChange = { cycle1EndValue = it }, // Store Cycle 1’s end value
                onAverageComputed = { cycle1Average = it } // Store Cycle 1’s computed average
            )
        }
        Spacer(modifier = Modifier.width(16.dp)) // Space between cycles
        Column(modifier = Modifier.weight(1f)) {
            CycleComponent(
                cycleNumber = 2,
                startCount = cycle2StartCount, // Auto-filled from Cycle 1’s end value +1
                onEndValueChange = { cycle2EndValue = it }, // Store Cycle 2’s end value
                onAverageComputed = { cycle2Average = it } // Store Cycle 2’s computed average
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FMCComponentPreview() {
    CycleBar()
}