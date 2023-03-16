package com.example.myapplication.school_data

import androidx.annotation.StringRes
import com.example.myapplication.R

data class HighSchoolItem(val id: HighSchoolId, val name: String, val isSelected: Boolean)

data class SatScoreItem(@StringRes val testType: Int, val score: SatScore)

data class AverageSatScoresItem(val readingScore: SatScoreItem, val writingScore: SatScoreItem, val mathScore: SatScoreItem)

open class HighSchoolUi {
    data class HighSchoolListUi(
        @StringRes val schoolsLabel: Int = R.string.schools,
        val schools: List<HighSchoolItem>,
        @StringRes val message: Int = R.string.prompt_select_a_school
    ) : HighSchoolUi()

    data class HighSchoolWithScoresUi(
        @StringRes val schoolsLabel: Int = R.string.schools,
        val schools: List<HighSchoolItem>,
        @StringRes val scoresLabel: Int = R.string.average_scores,
        val schoolName: String,
        val scores: AverageSatScoresItem
    ) : HighSchoolUi()
}