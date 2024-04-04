package com.example.cis5120mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Top Box - Greeting
        Box(
            modifier = Modifier
                .height(126.dp)
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = "Good Afternoon, Sarina!\nHere is your health dashboard based on your health screening."
            )
        }

        // First Card
        HealthCard()

        // Spacer between the cards
        Spacer(modifier = Modifier.height(28.dp))

        // Second Card
        HealthCard()
    }
}

@Composable
fun HealthCard() {
    Card(
        modifier = Modifier
            .height(82.dp)
            .fillMaxWidth()
            .background(Color(0xFAF7EF)), // Using a placeholder color
        elevation = 4.dp
    ) {
        // Your card content here
        Text(text = "Card Content", modifier = Modifier.padding(16.dp))
    }
}
