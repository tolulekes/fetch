package com.toluleke.fetchtakehome.ui

import android.util.Log
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
    private val apiService: APIService //Providing the viewmodel with the api service using dependency injection
) : ViewModel() {

    /**
    * Serves as a viewState for the view, if this was a much larger view or that more
     *  variables were needed would have used a dataClass as the State for the view to update,
     *  This would also ensure that the view recomposes whenever there is a change in the state.
     *  @param group is a map of Items grouped by their itemId
     *  @param expand is a map of itemId and a Boolean to indicate if the specific grouping should be expanded
    **/
    private val _group = MutableStateFlow<Map<String, List<Items>>>(emptyMap())
    val group: StateFlow<Map<String, List<Items>>> get() = _group

    private val _expand = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val expand: StateFlow<Map<String, Boolean>> get() = _expand


    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = apiService.fetchItems() //Fetching data from the link provided using retrofit
                _group.value = data.filter { item -> item.name?.isNotEmpty() == true }
                    .sortedBy { it.listId } //Sort by listId
                    .sortedBy { it.name } //Sort by name
                    .groupBy { it.listId }
                    .toSortedMap()

            }
        }
    }

    fun expandGrouping(item: String, show: Boolean) {
        _expand.value = _expand.value.toMutableMap().also {
            if (it.containsKey(item)){
                if (it[item] == show) {
                    it[item] = !show
                } else {
                    it[item] = show }
            } else {
                it[item] = show
            }
        }
    }

    fun shouldExpand(item: String) = _expand.value[item]

}