package com.toluleke.fetchtakehome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toluleke.fetchtakehome.data.network.APIService
import com.toluleke.fetchtakehome.data.responseModels.Items
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val apiService: APIService
) : ViewModel() {

    private val _dataFlow = MutableStateFlow<List<Items>>(emptyList())
    val dataFlow: StateFlow<List<Items>> get() = _dataFlow

    private val _group = MutableStateFlow<Map<String, List<Items>>>(emptyMap())
    val group: StateFlow<Map<String, List<Items>>> get() = _group


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = apiService.fetchItems()
                _group.value = data.filter { item -> item.name != null && item.name.isBlank() }
                    .groupBy { it.listId }
                    .toSortedMap()
                _dataFlow.value = data.filter { item -> item.name != null && item.name != "" }
                    .sortedBy { it.listId }

            }
        }
    }

//    fun filterItem()

}