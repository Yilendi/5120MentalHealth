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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

    val showCard = viewModel.showCard.value
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) { // Enclosing Box to hold both Column and FAB
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(134.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Good Afternoon, ",
                        fontSize = 28.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Sarina!",
                        fontSize = 28.sp,
                        color = Color(0xFF07C0BA)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "Here is your health dashboard based on your health screening.",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            if (showCard) {
                ProgressCard(onClose = { viewModel.setShowCard(false) })
            }
            Spacer(Modifier.height(32.dp))
            HealthCard(item = item, navController = navController)
            Spacer(Modifier.height(28.dp))
            HealthCard(item = item2, navController = navController)
        }

        FloatingActionButton(
            onClick = { viewModel.reset() },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Aligns FAB to the bottom end within the Box
                .padding(16.dp), // Adds padding from the edges
            backgroundColor = Color(0xFF07C0BA)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
        }
    }
}

@Composable
fun ProgressCard(onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(12.dp), // Rounded corners
//        elevation = 2.dp,
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
                Text("PROGRESS", color = Color.Black, style = MaterialTheme.typography.body2)
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
            .height(if (isExpanded) 274.dp else 94.dp), // Adjust height based on expanded state
//        elevation = 2.dp,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableContent() {
    var morningChecked by remember { mutableStateOf(false) }
    var nightChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 26.dp)
                    .background(Color(0xFFD1FAF2), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("MEDICATION", color = Color.Black, style = MaterialTheme.typography.body2)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Edit",
                fontWeight = FontWeight.Medium

            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            "MORNING",
            color = Color(0xFF07C0BA)
        )
        Divider(
            modifier = Modifier
                .width(339.dp),
            color = Color(0xFF07C0BA)
        )
        Spacer(Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Nexito 10mg", style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold  // Sets the text to be bold
            ) )
            Spacer(Modifier.weight(1f))
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Checkbox(
                    checked = morningChecked,
                    onCheckedChange = { morningChecked = it },
                    modifier = Modifier.padding(0.dp)  // Explicitly setting padding to zero
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Text(
            "NIGHT",
            color = Color(0xFF07C0BA)
        )
        Divider(
            modifier = Modifier
                .width(339.dp),
            color = Color(0xFF07C0BA)
        )
        Spacer(Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Escitalopram 15mg", style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold  // Sets the text to be bold
            ) )
            Spacer(Modifier.weight(1f))
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Checkbox(
                    checked = nightChecked,
                    onCheckedChange = { nightChecked = it },
                    modifier = Modifier.padding(0.dp)  // Explicitly setting padding to zerod
                )
            }

        }
        Spacer(Modifier.weight(1f))
    }
}

data class ItemDetails(
    val title: String,
    val description: String,
    val imageName: String,
    val action: String // Assuming this refers to a resource name or a file name
)