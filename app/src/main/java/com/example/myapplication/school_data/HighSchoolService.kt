package com.example.myapplication.school_data

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

/** Provide a singleton [Converter.Factory]. */
private val GsonFactory: Converter.Factory by lazy {
    GsonConverterFactory.create()
}

/** Create a [Retrofit] factory instance for [HighSchoolService]. */
private val RetrofitFactory: (String, Converter.Factory) -> Retrofit = { baseUrl, factory ->
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(factory)
        .build()
}

object HighSchoolApi {
    /** Provide a singleton [HighSchoolService]. */
    val retrofitService : HighSchoolService by lazy {
        RetrofitFactory(BASE_URL, GsonFactory).create()
    }
}

/** Interface to get the High Schools and SAT Scores */
interface HighSchoolService {
    @GET("s3k6-pzi2.json")
    suspend fun getHighSchools(): List<HighSchool>

    @GET("f9bf-2cp4.json")
    suspend fun getSatScores(): List<AverageSatScores>
}
