package com.toluleke.fetchtakehome.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toluleke.fetchtakehome.data.responseModels.Items
import com.toluleke.fetchtakehome.ui.theme.FetchTakeHomeTheme
import com.toluleke.fetchtakehome.ui.theme.PurpleGrey40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeTheme {
                val data by viewModel.dataFlow.collectAsState()
                val group by viewModel.group.collectAsState()
                var expandState by remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->

                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        items(group.keys.toList()) { item ->

                            GroupItem(modifier = Modifier, itemId = item) {
                                expandState = !expandState
                            }
                            if (expandState) {
                                val cardMod = Modifier
                                    .padding(horizontal = 5.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp)
                                ElevatedCard(
                                    modifier = cardMod,
                                    onClick = { expandState = !expandState },
                                    shape = RoundedCornerShape(15.dp)
                                ) {
                                    data.filter { it.listId == item }.forEach {
                                        RowItem(item = it)
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupItem(modifier: Modifier, itemId: String, onItemClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Grouped By: ")

        Text(
            modifier = modifier,
            text = itemId
        )
    }
}

@Composable
fun RowItem(item: Items, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = item.id)
        Spacer(modifier = modifier.weight(1f))

        Text(
            text = item.name,
        )

        Spacer(modifier = modifier.weight(1f))

        Text(text = item.listId)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchTakeHomeTheme {
    }
}