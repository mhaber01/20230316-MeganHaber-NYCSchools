package com.example.myapplication.school_data

import androidx.lifecycle.*
import com.example.myapplication.R
import kotlinx.coroutines.launch

class HighSchoolViewModel(private val highSchoolDomain: HighSchoolDomain, private val savedState: SavedStateHandle = SavedStateHandle()) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _school = MutableLiveData<HighSchoolUi>()

    // The external immutable LiveData for the request status
    val school: LiveData<HighSchoolUi> = _school

    private var loadedHighSchoolList: List<HighSchool>? = null

    init {
        getSchoolList()
    }

    /** Get the list of schools and, if the state contains the school ID and name, then show the SAT scores. */
    private fun getSchoolList() {
        if (savedState.contains(SCHOOL_ID) && savedState.contains(SCHOOL_NAME)) {
            getScores(id = HighSchoolId(savedState[SCHOOL_ID]!!), name = savedState[SCHOOL_NAME]!!)
        } else {
            getSchools()
        }
    }

    private fun getSchools() {
        viewModelScope.launch {
            _school.value = HighSchoolUi.HighSchoolListUi(schools = loadHighSchools())
        }
    }

    private fun getScores(id: HighSchoolId, name: String) {
        viewModelScope.launch {
            _school.value = HighSchoolUi.HighSchoolListUi(schools = loadHighSchools(), message = R.string.loading)
            _school.value = loadScores(id, name)
        }
    }

    private fun loadHighSchools(): List<HighSchoolItem> {
        val schools = loadedHighSchoolList ?: highSchoolDomain.getHighSchools().sortedBy(HighSchool::name).also { loadedHighSchoolList = it }

        val id = savedState.get<String>(SCHOOL_ID)?.let(::HighSchoolId)
        return schools.map { HighSchoolItem(id = it.id, name = it.name, isSelected = it.id == id) }
    }

    private fun loadScores(id: HighSchoolId, name: String): HighSchoolUi.HighSchoolWithScoresUi {
        return highSchoolDomain.getAverageSatScores(id).let {
            HighSchoolUi.HighSchoolWithScoresUi(
                schools = loadHighSchools(),
                schoolName = name,
                scores = AverageSatScoresItem(
                    readingScore = SatScoreItem(R.string.sat_reading, it.reading),
                    writingScore = SatScoreItem(R.string.sat_writing, it.writing),
                    mathScore = SatScoreItem(R.string.sat_math, it.math),
                )
            )
        }
    }

    fun onHighSchoolClick(school: HighSchoolItem) {
        savedState[SCHOOL_ID] = school.id.id
        savedState[SCHOOL_NAME] = school.name
        getSchoolList()
    }

    companion object {
        private const val SCHOOL_ID = "id"
        private const val SCHOOL_NAME = "name"
    }
}