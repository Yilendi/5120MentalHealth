package com.example.cis5120mentalhealth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SharingScreen() {
    val cardSpacing = 24.dp
    val cardHeight = 60.dp

    val items = (0..10).toList() // Example list of items

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Sharing", style = TextStyle(fontSize = 24.sp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(items.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight),
                        elevation = 4.dp
                    ) {
                        // Content of the card
                        Text(text = "Item $index", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

