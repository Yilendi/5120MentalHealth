package com.example.cis5120mentalhealth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cis5120mentalhealth.ui.theme.CIS5120MentalHealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CIS5120MentalHealthTheme {
                Navigation()
            }
        }
    }


}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    mainAppGraph(navController = navController)
}


@Composable
fun mainAppGraph(navController: NavHostController) {
    // Observes changes in the back stack and gets the current back stack entry
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Function to toggle the popup visibility
    // Side-effect to log changes in navigation
    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.let { backStackEntry ->
            val currentRoute = backStackEntry.destination.route
            Log.d("NavMonitor", "Current Route: $currentRoute")
        }
    }
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        paddingValues ->
        NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(paddingValues)) {
            composable("home") {

                HomeScreenView(navController)
            }
            composable("summary") {
                MoodTrackerView()
            }
            composable("sharing") {
                SharingScreen()
            }

            composable("mood") {
//                MoodScreen(navController)
                MoodScreenWithOverlay(navController)
            }


        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem("home", "Home", painterResource(R.drawable.icon_home)),
        NavigationItem("summary", "Summary", painterResource(R.drawable.icon_summary)),
        NavigationItem("sharing", "Sharing", painterResource(R.drawable.icon_sharing))
    )

    BottomNavigation(
        modifier = Modifier
            .navigationBarsPadding() // Add padding for the navigation bar (home indicator)
            .height(67.dp),
        backgroundColor = Color.White,
        elevation = 0.dp // Remove shadow by setting elevation to 0
    ) {
        items.forEach { item ->
            val isSelected = navController.currentDestination?.hierarchy?.any { it.route == item.route } == true
            BottomNavigationItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp) // 8dp padding from the left and right edges of the navigation bar
                            .padding(top = 12.dp, bottom = 7.dp)
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){ // Stack the icon and text vertically
                            Icon(
                                painter = item.icon,
                                contentDescription = item.title,
                            )
                            Text(
                                text = item.title,
                                // Add styling for the text here (font size, color, etc.)
                            )
                        }
                    }
                },
                selected = isSelected,
                selectedContentColor = Color.Black,
                unselectedContentColor = colorScheme.secondary,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
//                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },

                )
        }
    }
}

data class NavigationItem(val route: String, val title: String, val icon: Painter)