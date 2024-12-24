package com.toluleke.fetchtakehome.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toluleke.fetchtakehome.R
import com.toluleke.fetchtakehome.data.responseModels.Items
import com.toluleke.fetchtakehome.ui.theme.FetchTakeHomeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTakeHomeTheme {
                val group by viewModel.group.collectAsState()
                val expand by viewModel.expand.collectAsState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->

                    LazyColumn(
                        contentPadding = innerPadding,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        items(group.keys.toList()) { item ->
                            GroupItem(
                                items = group[item]?.filter { it.listId == item } ?: emptyList(),
                                modifier = Modifier,
                                itemId = item,
                                expandState = expand[item] ?: false
                            ) {
                                viewModel.expandGrouping(
                                    item,
                                    !(viewModel.shouldExpand(item) ?: false)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupItem(
    expandState: Boolean,
    items: List<Items>,
    modifier: Modifier,
    itemId: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.grouped_by),
            style = MaterialTheme.typography.titleLarge)

        Text(
            modifier = modifier,
            text = itemId,
            style = MaterialTheme.typography.titleLarge
        )
    }
    if (expandState) {
        ElevatedCard(
            modifier = modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            onClick = { onItemClick() },
            shape = RoundedCornerShape(15.dp)
        ) {
            GroupTitle(modifier = modifier.padding(top = 8.dp))
            items.forEach {
                RowItem(item = it)
            }
        }
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
            text = item.name ?: "",
        )

        Spacer(modifier = modifier.weight(1f))

        Text(text = item.listId)
    }
}


@Composable
private fun GroupTitle(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.id),
            style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = modifier.weight(1f))
        Text(text = stringResource(R.string.name),
            style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = modifier.weight(1f))
        Text(text = stringResource(R.string.item_id),
            style = MaterialTheme.typography.titleMedium)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchTakeHomeTheme {
    }
}