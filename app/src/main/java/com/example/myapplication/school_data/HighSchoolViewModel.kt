package com.example.myapplication.school_data

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

/** The [ViewModel] that sends the state of [HighSchool] and [AverageSatScores] to the UI. */
class HighSchoolViewModel() : ViewModel() {

    private val tag = "HighSchoolViewModel"

    // The internal MutableLiveData that stores the high schools
    private val _schools = MutableLiveData<List<HighSchool>>()
    // The external immutable LiveData for the request schools
    val schools: LiveData<List<HighSchool>> = _schools

    // The internal MutableLiveData that stores the average scores
    private val _scores = MutableLiveData<List<AverageSatScores>>()
    // The external immutable LiveData for the request scores
    val scores: LiveData<List<AverageSatScores>> = _scores

    init {
        getSchools()
        getScores()
    }

    private fun getSchools() {
        viewModelScope.launch {
            try {
                val schoolListResult: List<HighSchool> = HighSchoolApi.retrofitService.getHighSchools()
                Log.d(tag, "Success: ${schoolListResult.size} SAT scores retrieved")
                _schools.value = schoolListResult
            } catch (e: java.lang.Exception) {
                Log.d(tag, "Failure: ${e.message}")
            }
        }
    }

    private fun getScores() {
        viewModelScope.launch {
            try {
                val satScoresListResult = HighSchoolApi.retrofitService.getSatScores()
                Log.d(tag, "Success: ${satScoresListResult.size} SAT scores retrieved")
                _scores.value = satScoresListResult
            } catch (e: java.lang.Exception) {
                Log.d(tag, "Failure: ${e.message}")
            }
        }
    }
}