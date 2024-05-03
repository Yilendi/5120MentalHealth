package com.example.cis5120mentalhealth
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SymptomsScreen(viewModel: SymptomsViewModel, navController: NavController) {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    val formattedDate = currentDate.format(formatter)
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = { Text("Symptom Tracking") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF7EF)) // Apply the custom background color here
                .padding(innerPadding) // Apply the padding from Scaffold
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 12.dp)
                    .padding(horizontal = 24.dp)
                    .background(Color(0xFFFAF7EF))
            ) {
                Text(
                    text = "Today, $formattedDate",
                    style = MaterialTheme.typography.h6, // large text
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = "Add your symptoms to record how you are feeling. You can select all that apply. ",
                    style = MaterialTheme.typography.body2, // small text
                    modifier = Modifier.align(Alignment.Start)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 91.dp, height = 26.dp)
                                    .background(Color(0xFFEFEBE1), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("SYMPTOMS", color = Color.Black, style = MaterialTheme.typography.body2)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Create your own",
                                fontWeight = FontWeight.Medium

                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "RECENT",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color(0xFF07C0BA)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(color = Color(0xFF07C0BA))
                        Spacer(modifier = Modifier.height(8.dp))

                        SymptomsList(
                            symptoms = viewModel.symptomsGroup1,
                            checkedStates = viewModel.checkedStatesGroup1,
                            onCheckedChange = { symptom, isChecked ->
                                viewModel.checkedStatesGroup1[symptom] = isChecked
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "ADDITIONAL SYMPTOMS",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color(0xFF07C0BA)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(color = Color(0xFF07C0BA))
                        Spacer(modifier = Modifier.height(8.dp))

                        SymptomsList(
                            symptoms = viewModel.symptomsGroup2,
                            checkedStates = viewModel.checkedStatesGroup2,
                            onCheckedChange = { symptom, isChecked ->
                                viewModel.checkedStatesGroup2[symptom] = isChecked
                            }
                        )

                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center, // Centers the contents within the Box
                    modifier = Modifier.fillMaxWidth() // Ensures the Box fills the available space
                ) {
                    OutlinedButton(
                        onClick = {
                            if (viewModel.isAnySymptomChecked()) {
                                viewModel.setShowCard(true)
                                navController.navigate("home") {
                                    // Pop up to the start destination of the NavHost (clearing the entire back stack)
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    // Avoid multiple copies of the same destination when reselecting the same item
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigateUp()
                            }

                        },
                        modifier = Modifier
                            .size(width = 241.dp, height = 46.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White, // Adjust as necessary
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text("Done", color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }

    }
}

@Composable
fun SymptomsList(symptoms: List<String>, checkedStates: Map<String, Boolean>, onCheckedChange: (String, Boolean) -> Unit) {
    Column {
        symptoms.forEach { symptom ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(symptom, modifier = Modifier.weight(1f))
                Checkbox(
                    checked = checkedStates[symptom] ?: false,
                    onCheckedChange = { newValue -> onCheckedChange(symptom, newValue) }
                )
            }
            Divider()
        }
    }
}


@Composable
fun SummaryCard(item: ItemDetails) {
    // Assuming the context is available to resolve the drawable ID from the name
    // This is a simplified way to map a string name to a drawable resource.
    // In a real app, especially for more complex or dynamic image loading,
    // consider using a library like Coil which can handle resource loading more gracefully
    var showDialog by remember { mutableStateOf(false) }
    SummaryDialog(showDialog = showDialog, onDismiss = { showDialog = false })

    Card(
        modifier = Modifier
            .width(345.dp)
            .padding(vertical = 8.dp)
            .height( 82.dp), // Adjust height based on expanded state
        elevation = 0.dp,
        backgroundColor = Color(0xFFFAF7EF)
    ) {
        Column {
            Row(modifier = Modifier.height(82.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp, top = 12.dp, end = 54.dp, bottom = 12.dp)
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
                                    fontSize = 22.sp              // Increases the font size; adjust as needed
                                ),
                            )
                        }
                        // Assuming item.description might contain multiple lines.
                        // Splitting by newline character to treat as separate texts.
                        item.description.split("\n").forEach { line ->
                            Text(
                                text = line,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(start = 30.dp, top = 4.dp)
                            )
                        }
                    }
                }

                Icon(
                    imageVector = if (item.action == "add") Icons.Default.Add else Icons.Default.ChevronRight, // Plus sign icon
                    contentDescription = "Add",
                    modifier = Modifier
                        .padding(end = 12.dp) // Padding from the end of the Card
                        .size(26.dp) // Size of the icon
                        .clip(CircleShape) // Clip to make the icon round
                        .clickable {
                            showDialog = true
                        }
                )
            }

        }
    }
}



