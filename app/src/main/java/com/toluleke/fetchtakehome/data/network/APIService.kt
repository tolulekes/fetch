package com.toluleke.fetchtakehome.data.network

import com.toluleke.fetchtakehome.data.responseModels.Items
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("hiring.json")
    suspend fun fetchItems() : List<Items>
}
