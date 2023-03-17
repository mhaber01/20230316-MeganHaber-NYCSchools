package com.example.myapplication.school_data

import com.google.gson.annotations.SerializedName

/**
 * This data class defines a High School, which includes a school id and school name.
 */
data class HighSchool(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val name: String)

/**
 * This data class defines Average SAT Scores, which includes a school id and the math, reading, and writing average scores.
 */
data class AverageSatScores(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("sat_math_avg_score")
    val math: String,
    @SerializedName("sat_critical_reading_avg_score")
    val reading: String,
    @SerializedName("sat_writing_avg_score")
    val writing: String,
)
