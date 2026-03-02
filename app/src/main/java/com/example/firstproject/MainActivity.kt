package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaggedSearchScreen()
                }
            }
        }
    }
}

@Composable
fun TaggedSearchScreen() {

    var searchQuery by remember { mutableStateOf("") }
    var tagQuery by remember { mutableStateOf("") }

    val tags = listOf(
        "AndroidFP",
        "Deitel",
        "Google",
        "iPhoneFP",
        "JavaFP",
        "JavaHTP"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter search query") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tag Field + Save
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = tagQuery,
                onValueChange = { tagQuery = it },
                label = { Text("Tag your query") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { },
            ) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Section Title
        Text(
            text = "Tagged Searches",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tags List
        tags.forEach { tag ->
            TagItem(tag)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Clear Button
        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFEDE7F6),
                contentColor = Color(0xFF6A1B9A)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Clear Tags")
        }
    }
}

@Composable
fun TagItem(tagName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tagName,
                style = MaterialTheme.typography.bodyLarge
            )
            TextButton(onClick = { }) {
                Text("Edit")
            }
        }
    }
}