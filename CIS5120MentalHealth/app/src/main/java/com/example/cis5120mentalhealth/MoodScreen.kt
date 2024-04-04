package com.example.cis5120mentalhealth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoodScreenWithOverlay(navController: NavController) {

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
        MoodScreen(navController)
    }
}

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

    val buttonTexts = arrayOf("Get Started", "Select", "Input Number of Weeks", "Enable Notifications")

    Box(
        modifier = Modifier
            .height(707.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(72.dp))

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
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(80.dp))

            CustomButton(
                currentIndex = currentIndex, // Assuming you have a currentIndex state in your composable
                updateIndex = { newIndex ->
                    currentIndex = newIndex // Update your state with the new index
                },
                buttonTexts = buttonTexts // Example button texts
            )

            Spacer(modifier = Modifier.height(121.dp))

            // Three small dots
            // Dots
            if (currentIndex > 0) {
                NavigationDots(currentIndex = currentIndex, total = 3)
            }
            // Bottom navigation arrows and texts
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
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

                if (currentIndex in 0..2) {
                    Text(
                        text = if (currentIndex < 3) "Next" else "",
                        color = Color(0xFF07C0BA),
                        modifier = Modifier
                            .clickable { if (currentIndex < 3) currentIndex += 1 }
                            .padding(16.dp),
                        textAlign = TextAlign.Right
                    )
                }
            }
        }

        Text(
            text = "Done",
            modifier = Modifier
                .padding(top = 12.dp , end = 16.dp)
                .clickable { onDoneClicked() },
            color = Color.Black
        )
    }
}

@Composable
fun CustomButton(
    currentIndex: Int,
    updateIndex: (Int) -> Unit,
    buttonTexts: Array<String>
) {
    if (currentIndex == 0) {
        Button(
            onClick = { updateIndex(currentIndex + 1) },
            modifier = Modifier
                .size(width = 241.dp, height = 46.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF07C0BA))
        ) {
            Text(buttonTexts[currentIndex], color = Color.White)
        }
    } else {
        OutlinedButton(
            onClick = {
                if (currentIndex < buttonTexts.size - 1) updateIndex(currentIndex + 1)
                // You might want to handle the action for the last index differently
                else updateIndex(currentIndex) // Or any other final action
            },
            modifier = Modifier
                .size(width = 241.dp, height = 46.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(buttonTexts[currentIndex], color = Color.Black)
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




