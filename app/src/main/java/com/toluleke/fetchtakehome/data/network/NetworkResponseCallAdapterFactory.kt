package com.toluleke.fetchtakehome.data.network

import android.content.Context
import android.content.Intent
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

//class NetworkResponseCallAdapterFactory() : CallAdapter.Factory() {
//    override fun get(
//        returnType: Type,
//        annotations: Array<out Annotation>,
//        retrofit: Retrofit
//    ): CallAdapter<*, *>? {
//
//        return when (getRawType(returnType)) {
//            Call::class.java -> {
//                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
//                NetworkResponseAdapter<Any>(callType)
//            }
//            else -> null
//        }
//    }
//}