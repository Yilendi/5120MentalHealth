package com.example.cis5120mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.Card
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
import androidx.compose.ui.text.font.FontWeight
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
    val symptomsText = when {
        checkedSymptoms.isEmpty() -> ""
        checkedSymptoms.size <= 2 -> checkedSymptoms.joinToString(", ")
        else -> "${checkedSymptoms.take(2).joinToString(", ")} (+${checkedSymptoms.size - 2} more)"
    }


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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .size(width = 91.dp, height = 26.dp)
                    .background(Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2 OF 14 DAYS",
                    color = Color.White,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp)  // Smaller font size
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Edit",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(46.dp))
            // "mood" section
            Text("Mood", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))

            // Scrollable row of images
            ScrollableEmojisRow()

            Spacer(modifier = Modifier.height(28.dp))

            // "Symptoms" section
            Text("Other Symptoms", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))

            TextWithDynamicButton(
                buttonText = symptomsText,  // This is the input text that determines the button's state
                onTextClick= {navController.navigate("symptoms")}
            )



            Spacer(modifier = Modifier.height(28.dp))

            // "Notes" section
            Text("Add Notes", style = MaterialTheme.typography.subtitle1, color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = Color(0xFF07C0BA))
            Spacer(modifier = Modifier.height(8.dp))
            TextWithRoundIconButton(
                text = notesText,
                iconType = "write",
                onIconClick = {showNotesDialog = true}
            )

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
    val emojiList = listOf(
        "\uD83D\uDE42" to "Happy",
        "\uD83D\uDE03" to "Calm",
        "\uD83D\uDE10" to "Moody",
        "\uD83D\uDE28" to "Dizzy",
        "\uD83D\uDE29" to "Weary",
        "\uD83D\uDE41" to "Disturbed"

    )
    // State to track the selected emoji
    var selectedEmoji by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        emojiList.forEach { (emoji, description) ->

            Card(
                elevation = 6.dp,  // Apply elevation here
                shape = CircleShape,  // Ensures the card itself is also circular
                modifier = Modifier.size(88.dp)  // Size of the circle card
            ) {
                Box(
                    modifier = Modifier
                        .background(if (emoji == selectedEmoji) Color(0xFFD1FAF2) else Color(0xFFFAF7EF))  // Background color changes if selected
                        .clickable { selectedEmoji = emoji },  // Click action
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)  // Half of 8dp to get even spacing on both sides
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 28.sp,  // Font size for the emoji
                            color = Color.Black  // Ensuring the text color is specified
                        )
                        Text(
                            text = description,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
        }
//        // Add button to the row
//        IconButton(
//            onClick = { /* Define what happens when you click the add button */ },
//            modifier = Modifier
//                .size(26.dp)
//                .clip(CircleShape)
//                .background(Color(0xFFEFEBE1)),
//        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Add",
//                tint = Color.Black,
//                modifier = Modifier.size(18.dp)
//            )
//        }
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
fun TextWithDynamicButton(
    buttonText: String,  // This is the input text that determines the button's state
    onTextClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Symptoms",
            style = MaterialTheme.typography.subtitle1,
            color = Color.Black  // Customize color as needed
        )

        Spacer(modifier = Modifier.weight(1f)) // Space between text and button or clickable text

        if (buttonText.isNotEmpty()) {
            // If buttonText is non-empty, display it as clickable text
            Text(
                text = buttonText,
                style = MaterialTheme.typography.button,  // Use button style for clickable text
                color = Color.Black,  // Use theme's primary color for the text
                modifier = Modifier
                    .clickable(onClick = onTextClick)
                    .padding(vertical = 8.dp)  // Padding for clickable area
            )
        } else {
            // If buttonText is empty, show a default RoundIconButton
            RoundIconButton(
                iconType = "add",  // Assuming you have a default icon type
                onClick = onTextClick
            )
        }
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



