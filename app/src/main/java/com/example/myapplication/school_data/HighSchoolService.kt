package com.example.myapplication.school_data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object HighSchoolApi {
    val retrofitService: HighSchoolService by lazy { retrofit.create(HighSchoolService::class.java) }
}

interface HighSchoolService {
    @GET("s3k6-pzi2.json")
    fun getHighSchools(): List<HighSchool>

    @GET("f9bf-2cp4.json")
    fun getSatScores(): List<AverageScores>
}
