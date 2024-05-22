package com.example.mentalhealthapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cis5120mentalhealth.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoodScreenWithOverlay(viewModel: SymptomsViewModel, navController: NavController) {

    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            BottomSheetContent(onDoneClicked = {
                scope.launch {
                    state.hide() // Use hide() to completely hide the bottom sheet
                }
            })
        },
        scrimColor = Color.Transparent
    ) {
        MoodScreen(viewModel, navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(onDoneClicked: () -> Unit) {

    var currentIndex by remember { mutableStateOf(0) }

    // Arrays for texts based on index
    val smallTexts = arrayOf(
        "Based on your recent medicine intake updates, we recommend recording your mood. Adding notes while journaling helps on reflecting back.\n\nThis will aid share information with your doctor.",
        "To get started add the number of times you want to check-in throughout the day.",
        "Let’s continue setting–up. For how long do you want to track your mood?",
        "You are so close to finish setting up your mood tracker. Would you like to get reminders to fill in your mood tracker?"
    )

    val buttonTexts = arrayOf("Get Started", "Number of times a day", "Number of days", "Enable Notifications")

    // Gesture detection
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { 707.dp.toPx() }
    val anchors = mapOf(-sizePx to -1, 0f to 0, sizePx to 1)

    Box(
        modifier = Modifier
            .height(707.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            // Detect swipe direction and change index
            LaunchedEffect(swipeableState.currentValue) {
                when (swipeableState.currentValue) {
                    -1 -> if (currentIndex < 3) currentIndex++
                    1 -> if (currentIndex > 0) currentIndex--
                }
                swipeableState.snapTo(0) // Reset swipeable state to center
            }

            Icon(
                painter = painterResource(id = R.drawable.img_mood_track), // Example icon, replace with your choice
                contentDescription = "Icon",
                modifier = Modifier
                    .size(112.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Color.Unspecified // Use actual tint/color for your icon
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Mood Tracking for Gaining Insights!",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                smallTexts[currentIndex],
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            MoodCustomButton(
                currentIndex = currentIndex, // Assuming you have a currentIndex state in your composable
                updateIndex = { newIndex ->
                    currentIndex = newIndex // Update your state with the new index
                },
                buttonTexts = buttonTexts,
                onDoneClicked = onDoneClicked
            )

//            Spacer(modifier = Modifier.height(121.dp))
            Spacer(modifier = Modifier.weight(1f))

            // Three small dots
            // Dots
            if (currentIndex > 0) {
                NavigationDots(currentIndex = currentIndex, total = 3)
            }
            // Bottom navigation arrows and texts
//            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentIndex in 1..3) {
                    IconButton(onClick = {
                        if (currentIndex > 0) currentIndex -= 1
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp)) // Keep layout balanced
                }

                if (currentIndex in 1..2) {
                    Text(
                        text = "Next",
                        color = Color(0xFF07C0BA),
                        modifier = Modifier
                            .clickable { if (currentIndex < 3) currentIndex += 1 }
                            .padding(end = 16.dp),
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Text(
            text = "Close",
            modifier = Modifier
                .padding(top = 12.dp, end = 16.dp)
                .clickable { onDoneClicked() },
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MoodCustomButton(
    currentIndex: Int,
    updateIndex: (Int) -> Unit,
    buttonTexts: Array<String>,
    onDoneClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Select") }

    Column(modifier = Modifier.padding(16.dp)) {
        if (currentIndex == 0) {
            Button(
                onClick = { updateIndex(currentIndex + 1) },
                modifier = Modifier
                    .size(width = 293.dp, height = 52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF07C0BA))
            ) {
                Text(buttonTexts[currentIndex], color = Color.White, fontWeight = FontWeight.Bold)
            }
        } else if (currentIndex < 3){
            Text(
                text = buttonTexts[currentIndex],
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 25.dp, bottom = 8.dp)
            )

            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .size(width = 293.dp, height = 52.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(selectedText, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(293.dp)
                    .padding(top = 4.dp)
            ) {
                (1..5).forEach { index ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = "$index"
                            expanded = false
                        },
                        text = {Text("$index")}
                    )
                }
            }
        } else {
            Button(
                onClick = {  },
                modifier = Modifier
                    .size(width = 293.dp, height = 52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD1FAF2))
            ) {
                Text(buttonTexts[currentIndex], color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Text(
                text = "Skip",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp)
                    .clickable {
                        onDoneClicked()
                    }
            )

        }
    }
}


@Composable
fun NavigationDots(currentIndex: Int, total: Int) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        repeat(total) { index ->
            Dot(isActive = currentIndex == index + 1)
            if (index < total - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Composable
fun Dot(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (isActive) Color(0xFF07C0BA) else Color.LightGray)
    )
}





