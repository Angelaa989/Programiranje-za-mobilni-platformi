package com.example.firstproject

import android.os.Bundle
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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

fun loadDictionary(context: Context): List<Pair<String,String>> {

    val dictionary = mutableListOf<Pair<String,String>>()

    val inputStream = context.assets.open("dictionary.txt")
    val lines = inputStream.bufferedReader().readLines()

    for(line in lines){
        val parts = line.split(",")
        if(parts.size == 2){
            dictionary.add(parts[0].trim() to parts[1].trim())
        }
    }

    return dictionary
}

@Composable
fun TaggedSearchScreen() {

    var searchQuery by remember { mutableStateOf("") }
    var tagQuery by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dictionary = remember { loadDictionary(context) }

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

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter word") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val found = dictionary.find {
                    it.first.equals(searchQuery, true) ||
                            it.second.equals(searchQuery, true)
                }

                result = if(found != null){

                    if(found.first.equals(searchQuery, true)){
                        "${found.first} = ${found.second}"
                    } else {
                        "${found.second} = ${found.first}"
                    }

                } else {
                    "Word not found"
                }

            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if(result.isNotEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3E5F5)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF6A1B9A)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = result,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                onClick = { }
            ) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Tagged Searches",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        tags.forEach { tag ->
            TagItem(tag)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(28.dp))

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