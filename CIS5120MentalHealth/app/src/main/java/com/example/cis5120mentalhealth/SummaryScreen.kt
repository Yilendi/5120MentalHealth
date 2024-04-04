package com.example.cis5120mentalhealth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
fun MoodTrackerView() {
    val dataPoints = listOf(
        DataPoint(0f, 2f),
        DataPoint(3f, 6f),
        DataPoint(6f, 5f),
        DataPoint(9f, 8f),
        DataPoint(12f, 4f),
        DataPoint(15f, 6f)
    )
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        // Title Box
        Box(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
            Text(text = "Mood Track", style = MaterialTheme.typography.h5)
        }

        // Line Graph
        Box(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()) {
            LineChartView(dataPoints)
        }

        // Cards below the graph
        val cards = listOf("Card 1", "Card 2", "Card 3") // Example card data
        cards.forEachIndexed { index, card ->
            if (index > 0) Spacer(modifier = Modifier.height(26.dp)) // Add space between cards, but not before the first one
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(text = card, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

//@Composable
//fun LineGraph() {
//    val points = listOf(Offset(0f, 100f), Offset(100f, 50f), Offset(200f, 150f), Offset(300f, 100f)) // Example points
//    Canvas(modifier = Modifier.fillMaxSize()) {
//        val path = Path()
//        if (points.isNotEmpty()) {
//            path.moveTo(points.first().x, points.first().y)
//            for (point in points) {
//                path.lineTo(point.x, point.y)
//            }
//        }
//
//        drawPath(
//            path = path,
//            color = Color.Blue,
//            style = Stroke(width = 5f) // Specify the stroke width here
//        )
//    }
//}

@Composable
fun LineChartView(data: List<DataPoint>) {

    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                data,
                    LinePlot.Connection(color = Color.Red),
                    LinePlot.Intersection(color = Color.Red),
                    LinePlot.Highlight(color = Color.Blue),
                )
            ),
            grid = LinePlot.Grid(Color.Yellow, steps = 4),
        ),
        modifier = Modifier.fillMaxWidth().height(200.dp),
        onSelection = { xLine, points ->
            // Do whatever you want here
        }
    )
}

