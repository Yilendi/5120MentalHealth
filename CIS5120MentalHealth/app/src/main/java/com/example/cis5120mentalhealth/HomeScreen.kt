package com.example.cis5120mentalhealth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cis5120mentalhealth.ui.theme.CIS5120MentalHealthTheme

@Composable
fun HomeScreenView(navController: NavController, viewModel: SymptomsViewModel) {

    val item = ItemDetails(
        title = "Record Medicine Intake",
        description = "Keep a track of the medicines you take throughout the day.",
        imageName = "\uD83D\uDDD2\uFE0F", // Assuming this is the name of an image in your drawable resources,
        action = "add"
    )

    val item2 = ItemDetails(
        title = "Track your Mood",
        description = "Keep a track of your mood by journaling your experience to reflect back.",
        imageName = "\uD83D\uDC8A", // Assuming this is the name of an image in your drawable resources
        action = "go"
    )

    val showCard = viewModel.showCard


    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Makes the Column scrollable
            .padding(24.dp)  // Apply padding to the overall Column
    ) {
        // Top Box - Greeting
        Box(
            modifier = Modifier
                .height(126.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Good Afternoon, ",
                    fontSize = 28.sp,
                    color = Color.Black // Keeps the default color for the initial part
                )
                Text(
                    text = "Sarina!",
                    fontSize = 28.sp,
                    color = Color(0xFF07C0BA) // Changes Sarina's name to green
                )
                Spacer( Modifier.weight(1f))

                Text(
                    text = "Here is your health dashboard based on your health screening.",
                    fontSize = 18.sp,
                    color = Color.Black, // Keeps the color for smaller text
                )
            }

        }
        Spacer(Modifier.height(40.dp))

        if (showCard.value) {
             ProgressCard(
                onClose = {
                    // Set the state to false when the close icon is clicked
                    viewModel.setShowCard(false)
                }
            )
        }

        Spacer(Modifier.height(32.dp))
        // First Card
        HealthCard(item, navController)

        // Spacer between the cards
        Spacer(modifier = Modifier.height(28.dp))

        // Second Card
        HealthCard(item2, navController)
    }
}


@Composable
fun ProgressCard(onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(12.dp), // Rounded corners
        elevation = 2.dp,
        backgroundColor = Color(0xFFFAF7EF)
    ) {
        Box {
            // Blue square with text
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 12.dp)
                    .size(width = 81.dp, height = 26.dp)
                    .background(Color(0xFFD1FAF2), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Progress", color = Color.Black, style = MaterialTheme.typography.body2)
            }

            // Icon for closing the card
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .size(24.dp)
                    .clickable { onClose() }
            )

            // Image vector with text below it
            Image(
                painter = painterResource(id = R.drawable.encouragement_notification), // Replace with your actual drawable ID
                contentDescription = "Encouragement",
                modifier = Modifier
                    .padding(start = 128.dp, top = 50.dp)
                    .size(width = 98.dp, height = 98.dp)
            )

            // Text below the image vector
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Well done!",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center  // Ensures text is centered if it wraps to a new line
                )

                Text(
                    "One more mood tracking check-in complete!",
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}


@Composable
fun HealthCard(item: ItemDetails, navController: NavController) {
    // Assuming the context is available to resolve the drawable ID from the name
    // This is a simplified way to map a string name to a drawable resource.
    // In a real app, especially for more complex or dynamic image loading,
    // consider using a library like Coil which can handle resource loading more gracefully.

    var isExpanded by remember { mutableStateOf(false) }

    val iconImage = when {
        item.action == "add" && isExpanded -> Icons.Default.Remove
        item.action == "add" && !isExpanded -> Icons.Default.Add
        else -> Icons.Default.ChevronRight
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(if (isExpanded) 232.dp else 94.dp), // Adjust height based on expanded state
        elevation = 2.dp,
        backgroundColor = Color(0xFFFAF7EF)
    ) {
        Column {
            Row(modifier = Modifier.height(94.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, top = 12.dp, bottom = 12.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.imageName,
                                style = MaterialTheme.typography.body1
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.body1.copy(
                                    fontSize = 20.sp              // Increases the font size; adjust as needed
                                ),
                            )
                        }
                        // Assuming item.description might contain multiple lines.
                        // Splitting by newline character to treat as separate texts.
                        item.description.split("\n").forEach { line ->
                            Text(
                                text = line,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(start = 30.dp)
                            )
                        }
                    }
                }

                Icon(
                    imageVector = iconImage,
                    contentDescription = if (item.action == "add") {
                        if (isExpanded) "Collapse" else "Expand"
                    } else "Navigate",
                    modifier = Modifier
                        .padding(end = 12.dp) // Padding from the end of the Card
                        .size(26.dp) // Size of the icon
                        .clip(CircleShape) // Clip to make the icon round
                        .clickable {
                            if (item.action == "add") {
                                isExpanded = !isExpanded // Toggle expandable state for "add" action
                            } else {
                                navController.navigate("mood") // Navigate for other actions
                            }
                        }
                )
            }
            if (isExpanded) {
                // Expanded content
                ExpandableContent()
            }
        }
    }
}

@Composable
fun ExpandableContent() {
    var morningChecked by remember { mutableStateOf(false) }
    var nightChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(start = 40.dp, end = 12.dp)) {
        Text(
            "Morning",
            color = Color(0xFF07C0BA)
        )
        Divider(
            modifier = Modifier
                .width(291.dp)
                .padding(vertical = 4.dp),
            color = Color(0xFF07C0BA)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Nexito 10mg", style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold  // Sets the text to be bold
            ) )
            Spacer(Modifier.weight(1f))
            Checkbox(
                checked = morningChecked,
                onCheckedChange = { morningChecked = it }
            )
        }
        Text(
            "Night",
            color = Color(0xFF07C0BA)
        )
        Divider(
            modifier = Modifier
                .width(291.dp)
                .padding(vertical = 4.dp),
            color = Color(0xFF07C0BA)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Escitalopram 15mg", style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold  // Sets the text to be bold
            ) )
            Spacer(Modifier.weight(1f))
            Checkbox(
                checked = nightChecked,
                onCheckedChange = { nightChecked = it } // Update state when checkbox is toggled
            )
        }
    }
}

data class ItemDetails(
    val title: String,
    val description: String,
    val imageName: String,
    val action: String // Assuming this refers to a resource name or a file name
)