package com.example.myapplication.school_data

class HighSchoolDomain (private val highSchoolService: HighSchoolService) {
    /** Returns [List] of [HighSchool] in NYC */
    fun getHighSchools() : List<HighSchool> {
        return highSchoolService.getHighSchools()
    }

    /** Returns [average SAT scores][AverageScores] for the school with [id]. */
    fun getAverageSatScores(id: HighSchoolId): AverageScores {
        return highSchoolService.getSatScores().first { it.id == id }
    }
}