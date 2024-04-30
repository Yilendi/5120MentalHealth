package com.example.cis5120mentalhealth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedButtonWithTextOnBorder() {
    var expanded by remember { mutableStateOf(false) }
    var selectedNumber by remember { mutableStateOf(3) }  // Default or previously saved value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(46.dp)  // Set the specific height
            .fillMaxWidth(0.5f)  // Make it half the width of the parent, or set a fixed width as needed
            .background(Color.White, RoundedCornerShape(24.dp))  // Set background and rounded corners
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidth = 1.dp.toPx()  // Define the stroke width
            // Adjust the size by subtracting the stroke width from both width and height
            val adjustedWidth = size.width - strokeWidth
            val adjustedHeight = size.height - strokeWidth
            drawRoundRect(
                color = Color.Black,
                topLeft = Offset(x = strokeWidth / 2, y = strokeWidth / 2),
                size = Size(width = adjustedWidth, height = adjustedHeight),
                cornerRadius = CornerRadius(x = 22.dp.toPx(), y = 22.dp.toPx()),  // Adjusted to fit within the 24.dp corner radius
                style = Stroke(width = strokeWidth)
            )
        }

        // Text positioned on the outline of the button
        Text(
            text = "Number of times a day",
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 32.dp, top = -6.dp)
        )

        // Dropdown button
        Button(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
        ) {
            Text("Select", textAlign = TextAlign.Center, modifier = Modifier.padding(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                modifier = Modifier.size(24.dp)
            )
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            (1..5).forEach { index ->
                DropdownMenuItem(
                    onClick = {
                        selectedNumber = index
                        expanded = false
                    },
                    text = { Text("$index") }
                )
            }
        }
    }
}
