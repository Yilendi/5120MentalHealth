package com.example.cis5120mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp

@Composable
fun MoodScreen(viewModel: SymptomsViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = { Text("Mood Tracking") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                elevation = 0.dp
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)  // Apply the padding passed to the function
                .fillMaxWidth()  // Ensure the column takes up the full width available
                .verticalScroll(rememberScrollState()),  // Allow the column to scroll vertically
            horizontalAlignment = Alignment.CenterHorizontally  // Center children horizontally
        ) {
                // Your mood tracking screen content goes here
                Box(
                    modifier = Modifier
                        .size(width = 344.dp, height = 78.dp)
                ) {
                    Column {
                        Text("How are you feeling?", style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp)) // Space between large and small text
                        Text(
                            "Record your mood and add notes. " +
                                    "You can use this to share information with your doctor. ",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp)) // Space between the text box and the next box

                MoodOverview(viewModel = viewModel, navController = navController)

                Spacer(modifier = Modifier.height(40.dp)) // Space between boxes

                // Another box that is 437 high, max width
//            InsightOverview()
                SummaryCard(
                    item = ItemDetails(
                        title = "Summary",
                        description = "Insights on the medication cycle.",
                        imageName = "\uD83D\uDCCB", // Assuming this is the name of an image in your drawable resources,
                        action = "go"
                    )
                )
            }

    }
}

@Composable
fun MoodOverview(viewModel: SymptomsViewModel, navController: NavController) {
    // Combine both groups into one list of pairs for easier manipulation
    val combinedSymptoms = (viewModel.symptomsGroup1 zip viewModel.checkedStatesGroup1.values) +
            (viewModel.symptomsGroup2 zip viewModel.checkedStatesGroup2.values)

    // Filter out the checked symptoms and take only the first three
    val checkedSymptoms = combinedSymptoms.filter { it.second }.map { it.first }.take(3)

    // Create a string that contains the names of up to three checked symptoms
    val symptomsText = if (checkedSymptoms.isEmpty()) "Symptoms" else checkedSymptoms.joinToString(", ")

//    var showSymptomsDialog by remember { mutableStateOf(false) }

    var notesText by remember { mutableStateOf("Your Notes Here") }
    var showNotesDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(345.dp)
            .wrapContentHeight()
            .background(Color(0xFFFAF7EF)) // Hex color code for background
            .clip(RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // "mood" section
            Text("Mood", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))

            // Scrollable row of images
            ScrollableEmojisRow()

            Spacer(modifier = Modifier.height(28.dp))

            // "Symptoms" section
            Text("Symptoms ... ", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))
            TextWithRoundIconButton(
                text = symptomsText,
                iconType = "add",
                onIconClick = {navController.navigate("symptoms")}
            )



            Spacer(modifier = Modifier.height(28.dp))

            // "Notes" section
            Text("Notes", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))
            TextWithRoundIconButton(
                text = notesText,
                iconType = "write",
                onIconClick = {showNotesDialog = true}
            )

//            if (showSymptomsDialog) {
//                InputDialog(
//                    initialText = symptomsText,
//                    onDismiss = { showSymptomsDialog = false }, // Hide the dialog on dismiss
//                    onConfirm = { newText ->
//                        symptomsText = newText // Update the symptoms text
//                        showSymptomsDialog = false // Hide the dialog after confirming
//                    }
//                )
//            }

            if (showNotesDialog) {
                InputDialog(
                    initialText = notesText,
                    onDismiss = { showNotesDialog = false }, // Hide the dialog on dismiss
                    onConfirm = { newText ->
                        notesText = newText // Update the symptoms text
                        showNotesDialog = false // Hide the dialog after confirming
                    }
                )
            }
        }
    }
}

@Composable
fun ScrollableEmojisRow() {
    val emojiList = listOf("\uD83D\uDE28", "\uD83D\uDE29", "\uD83D\uDE41", "\uD83D\uDE10", "\uD83D\uDE42", "\uD83D\uDE03")
    // State to track the selected emoji
    var selectedEmoji by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        emojiList.forEach { emoji ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    // Change the background color if the emoji is selected
                    .background(if (emoji == selectedEmoji) Color(0xFFD1FAF2) else Color.Transparent)
                    .clickable { selectedEmoji = emoji } // Update the selected emoji on click
                    .shadow(4.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp
                )
            }
        }
        // Add button to the row
        IconButton(
            onClick = { /* Define what happens when you click the add button */ },
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(Color(0xFFEFEBE1)),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


@Composable
fun InsightOverview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFFD1FAF2)) // Customizable background color
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Insights",
                style = MaterialTheme.typography.h5.copy(color = Color.Black) // Large, black text
            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between the title and small text

            Text(
                text = "Here are some insights based on your tracking.",
                style = MaterialTheme.typography.body2 // Small text
            )
            Spacer(modifier = Modifier.height(28.dp)) // Space before the white box

            insightDetails()
        }
    }
}

@Composable
fun insightDetails() {
    Box(
        modifier = Modifier
            .fillMaxSize() // Takes the remaining space
            .clip(RoundedCornerShape(8.dp)) // Rounded corners
            .background(Color.White) // White background
    ) {
        var showDialog by remember { mutableStateOf(false) }
        SummaryDialog(showDialog = showDialog, onDismiss = { showDialog = false })

        Column(modifier = Modifier.padding(16.dp)) { // Add padding inside the box
            // Highlights
            Text("Highlights", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Divider()

            // Summary
            Spacer(modifier = Modifier.height(8.dp))
//            Text("Summary", style = MaterialTheme.typography.subtitle1)
            TextWithRoundIconButton( "Summary", "right", { showDialog = true })
            Divider()

            // Activity
            Spacer(modifier = Modifier.height(8.dp))
            TextWithRoundIconButton( "Activity", "right", {})

            // Other Data
            Spacer(modifier = Modifier.height(8.dp))
            Text("Other Data", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Divider()

            // Factors
            Spacer(modifier = Modifier.height(8.dp))
            TextWithRoundIconButton( "Factors", "right", {})

            // Detailed text
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "You usually start feeling low in the afternoon. You stop feeling hungry if you skip your medicine.",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }


}

@Composable
fun TextWithRoundIconButton(
    text: String,
    iconType: String,
    onIconClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp) // Adjust padding as needed
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1 // You can make this color customizable by passing it as a parameter too
        )

        Spacer(modifier = Modifier.weight(1f)) // Space between text and button

        // RoundIconButton reused here
        RoundIconButton(
            iconType = iconType,
            onClick = onIconClick
        )
    }
}

@Composable
fun RoundIconButton(iconType: String, onClick: () -> Unit) {
    val icon = when (iconType.lowercase()) {
        "add" -> Icons.Default.Add
        "write" -> Icons.Default.Edit // Assuming "write" refers to an edit action
        "right" -> Icons.Default.ArrowForward
        else -> Icons.Default.Add // Default or fallback icon
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .size(26.dp) // Specify the desired size for the icon button.
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFEBE1)),
        contentPadding = PaddingValues(0.dp),// Set the background color
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconType,
            tint = Color.Black, // Adjust the icon color as needed
            modifier = Modifier.size(16.dp) // Adjust the icon size within the button if needed
        )
    }
}



