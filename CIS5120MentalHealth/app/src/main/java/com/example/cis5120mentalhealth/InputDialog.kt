package com.example.cis5120mentalhealth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
                    label = { Text("Enter Here") }
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


@Composable
fun NumberPickerDialog(
    showDialog: Boolean,
    onNumberSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Select a number",
                        style = MaterialTheme.typography.h6
                    )
                    LazyColumn {
                        items((1..5).toList()) { number ->
                            TextButton(
                                onClick = {
                                    onNumberSelected(number)
                                    onDismiss()
                                }
                            ) {
                                Text(text = "$number")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Recorded mood so far:",
                        style = MaterialTheme.typography.body1
                    )

                    Text(
                        text = "Today: \uD83D\uDE41",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "You are sad.",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "You have a headache.",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Yesterday: \uD83D\uDE41",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "You are sad.",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "You have a headache.",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

