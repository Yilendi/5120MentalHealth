package com.example.cis5120mentalhealth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun InputDialog(
    initialText: String, // Add this parameter
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialText) }
    // Assuming showDialog controls are handled outside, but kept parameter for clarity
    Dialog(onDismissRequest = onDismiss) {
        // Dialog UI components here
        Surface(shape = MaterialTheme.shapes.medium, elevation = 8.dp) {
            Column(modifier = Modifier.padding(16.dp)) {

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter Symptoms") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        onConfirm(text) // Pass the entered text back on confirm
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}
