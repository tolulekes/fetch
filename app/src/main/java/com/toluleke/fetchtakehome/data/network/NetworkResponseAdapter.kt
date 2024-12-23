package com.toluleke.fetchtakehome.data.network

import android.content.Context
import android.content.Intent
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

//
//class NetworkResponseAdapter<T: Any>(
//    private val successType: Type,
//) : CallAdapter<T, Call<T>> {
//    override fun responseType(): Type {
//        return successType
//    }
//
//    override fun adapt(call: Call<T>): Call<T> {
//        return NetworkResponseCall(call)
//    }
//}