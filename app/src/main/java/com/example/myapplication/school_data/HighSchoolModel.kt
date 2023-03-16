package com.example.myapplication.school_data

import com.squareup.moshi.Json

@JvmInline
value class HighSchoolId(val id: String)

/**
 * This data class defines a High School which includes a school name.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class HighSchool(
    @Json(name = "dbn")
    val id: HighSchoolId,
    @Json(name = "school_name")
    val name: String)

@JvmInline
value class SatScore(private val score: String) {
    fun value(): Int? = score.toIntOrNull()
}

data class AverageScores(
    @Json(name = "dbn")
    val id: HighSchoolId,
    @Json(name = "sat_math_avg_score")
    val math: SatScore,
    @Json(name = "sat_critical_reading_avg_score")
    val reading: SatScore,
    @Json(name = "sat_writing_avg_score")
    val writing: SatScore,
)
