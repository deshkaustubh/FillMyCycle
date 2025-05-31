package com.example.fillmycycle

// 📦 Imports
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 🧠 Main Composable
@Composable
fun FillMyCycleScreen() {
    var cycle1End by remember { mutableStateOf("") }
    var cycle2End by remember { mutableStateOf("") }

    val cycle1Start = 1
    val cycle1EndValue = cycle1End.toIntOrNull() ?: 0
    val cycle2StartValue = cycle1EndValue + 1
    val cycle2EndValue = cycle2End.toIntOrNull() ?: 0

    val cycle1Range = if (cycle1EndValue >= cycle1Start) cycle1Start..cycle1EndValue else IntRange.EMPTY
    val cycle2Range = if (cycle2EndValue >= cycle2StartValue) cycle2StartValue..cycle2EndValue else IntRange.EMPTY

    var cycle1Marks by remember(cycle1Range) { mutableStateOf(List(cycle1Range.count()) { "" }) }
    var cycle2Marks by remember(cycle2Range) { mutableStateOf(List(cycle2Range.count()) { "" }) }

    val cycle1Avg = averageOf(cycle1Marks)
    val cycle2Avg = averageOf(cycle2Marks)

    var showCycle1Details by remember { mutableStateOf(false) }
    var showCycle2Details by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
//        Text("Fill My Cycle", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        // Inputs
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {


            OutlinedTextField(
                value = cycle1Start.toString(),
                onValueChange = { },
//                label = { Text("Cycle 1 Start") },
                label = null,
                enabled = false,
                modifier = Modifier.weight(1f).padding(4.dp)
            )

            Text("to", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)

            OutlinedTextField(
                value = cycle1End,
                onValueChange = { cycle1End = it },
//                label = { Text("Cycle 1 End") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).padding(4.dp),
            )

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedTextField(
                value = if (cycle1EndValue >= cycle1Start) cycle2StartValue.toString() else "",
                onValueChange = {},
//                label = { Text("Cycle 2 Start") },
                label = null,
                enabled = false,
                modifier = Modifier.weight(1f).padding(4.dp)
            )

            Text("to", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)

            OutlinedTextField(
                value = cycle2End,
                onValueChange = { cycle2End = it },
//                label = { Text("Cycle 2 End") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f).padding(4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Marks Input
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!cycle1Range.isEmpty()) {
                MarksColumn("Cycle 1 (${cycle1Range.first}–${cycle1Range.last})", cycle1Marks) { cycle1Marks = it }
            }
            if (!cycle2Range.isEmpty()) {
                MarksColumn("Cycle 2 (${cycle2Range.first}–${cycle2Range.last})", cycle2Marks) { cycle2Marks = it }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Cycle 1 Avg: $cycle1Avg", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onPrimary)
            Text("Cycle 2 Avg: $cycle2Avg", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(Modifier.height(16.dp))

        SummaryTable()

        Spacer(Modifier.height(16.dp))

        ExpandableSection(
            title = "Cycle 1 (Experiments ${cycle1Range.firstOrNull() ?: "-"}–${cycle1Range.lastOrNull() ?: "-"})",
            expanded = showCycle1Details,
            onToggle = { showCycle1Details = !showCycle1Details }
        ) {
            PerformanceTable(
                marks = List(cycle1Range.count()) { "✔" },
                title = "Cycle 1"
            )
        }

        ExpandableSection(
            title = "Cycle 2 (Experiments ${cycle2Range.firstOrNull() ?: "-"}–${cycle2Range.lastOrNull() ?: "-"})",
            expanded = showCycle2Details,
            onToggle = { showCycle2Details = !showCycle2Details }
        ) {
            PerformanceTable(
                marks = List(cycle2Range.count()) { "✔" },
                title = "Cycle 2"
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                cycle1End = ""
                cycle2End = ""
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Reset All")
        }
    }
}


// 🔢 Input Column for Marks
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarksColumn(title: String, marks: List<String>, onMarksChanged: (List<String>) -> Unit) {
    Column {
        Text(title, fontWeight = FontWeight.SemiBold)
        marks.forEachIndexed { index, mark ->
            OutlinedTextField(
                value = mark,
                onValueChange = { value ->
                    onMarksChanged(marks.toMutableList().also { it[index] = value })
                },
                singleLine = true,
                modifier = Modifier
                    .size(width = 90.dp, height = 60.dp)
                    .clip(RoundedCornerShape(35.dp))
                    .padding(4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(18.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedBoxComponent(
    index: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .size(width = 90.dp, height = 60.dp)
            .clip(RoundedCornerShape(35.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            errorBorderColor = MaterialTheme.colorScheme.error,
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(35.dp)
    )
}


// 🧮 Average Calculator
fun averageOf(marks: List<String>): Int {
    val nums = marks.mapNotNull { it.toIntOrNull() }
    return if (nums.isNotEmpty()) nums.sum() / nums.size else 0
}

// 📊 Summary Table
@Composable
fun SummaryTable() {
    Column {
        Text("Summary Totals", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        val headers = listOf("Parameter", "TK", "TD", "LI", "B", "C", "Total")
        val rows = listOf(
            listOf("1 to 5", "24", "24", "10", "10", "14", "82"),
            listOf("6 to 10", "24", "18", "6", "4", "14", "66"),
            listOf("Total", "48", "42", "16", "14", "28", "148")
        )
        SummaryRow(headers, bold = true)
        rows.forEach { SummaryRow(it) }
    }
}

@Composable
fun SummaryRow(items: List<String>, bold: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        items.forEach {
            Text(it, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
        }
    }
}

// 📋 Performance Table
@Composable
fun PerformanceTable(marks: List<String>, title: String) {
    val rows = listOf("TK (30)", "TD (30)", "LI (10)", "B (10)", "C (20)")
    val columns = listOf("E", "VG", "G", "A", "BA")
    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Parameter")
            columns.forEach { Text(it) }
        }
        rows.forEachIndexed { index, param ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(param)
                columns.forEachIndexed { i, _ ->
                    val mark = if (i == index) marks.getOrNull(index) ?: "" else ""
                    Text(mark)
                }
            }
        }
    }
}

// ⬇ Expandable Section
@Composable
fun ExpandableSection(title: String, expanded: Boolean, onToggle: () -> Unit, content: @Composable () -> Unit) {
    Column {
        TextButton(onClick = onToggle) {
            Text(if (expanded) "▼ $title" else "▶ $title")
        }
        if (expanded) {
            content()
        }
    }
}

// 🖼 Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FillMyCycleScreenPreview() {
    FillMyCycleScreen()
}