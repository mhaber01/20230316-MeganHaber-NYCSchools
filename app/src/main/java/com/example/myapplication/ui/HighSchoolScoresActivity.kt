package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySchoolSatBinding
import com.example.myapplication.school_data.AverageSatScores
import com.example.myapplication.school_data.HighSchool
import com.example.myapplication.school_data.HighSchoolViewModel

/** Activity to display the average SAT scores given the NYC high school ID. */
class HighSchoolScoresActivity : AppCompatActivity() {

    private val highSchoolViewModel: HighSchoolViewModel by viewModels()

    private var currentHighSchoolId: String? = null
    private lateinit var binding: ActivitySchoolSatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySchoolSatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentHighSchoolId = bundle.getString(SCHOOL_ID)
        }

        currentHighSchoolId?.let { id ->
            setSchoolById(id)
            setScoresById(id)
        }

        binding.backButton.setOnClickListener {
            sendBackToSchoolList()
        }
    }

    private fun sendBackToSchoolList() {
        val intent = Intent(this, HighSchoolListActivity()::class.java)
        startActivity(intent)
    }

    /**  [HighSchool] given the id. */
    private fun setSchoolById(id: String) {
        var currentHighSchool: HighSchool? = null
        highSchoolViewModel.schools.observe(this, Observer { schools ->
            schools.forEach { school ->
                if (school.id == id) {
                    currentHighSchool = school
                }
            }

            binding.schoolSatTitle.text = currentHighSchool?.name
        })
    }

    /** Finds the [AverageSatScores] given the id and sets the layout. */
    private fun setScoresById(id: String) {
        var currentHighSchoolScores: AverageSatScores? = null
        highSchoolViewModel.scores.observe(this, Observer { scores ->
            scores.forEach { score ->
                if (score.id == id) {
                    currentHighSchoolScores = score
                }
            }

            if (currentHighSchoolScores == null) {
                binding.schoolNoScores.visibility = VISIBLE
                binding.schoolNoScores.text = getString(R.string.no_average_sat_scores)
            } else {
                binding.schoolNoScores.visibility = GONE
                val mathScore = currentHighSchoolScores?.math
                val readingScore = currentHighSchoolScores?.reading
                val writingScore = currentHighSchoolScores?.writing
                binding.schoolSatMathScore.text = buildString {
                    append(getString(R.string.sat_math))
                    append(" ")
                    append(mathScore)
                }
                binding.schoolSatReadingScore.text = buildString {
                    append(getString(R.string.sat_reading))
                    append(" ")
                    append(readingScore)
                }
                binding.schoolSatWritingScore.text = buildString {
                    append(getString(R.string.sat_writing))
                    append(" ")
                    append(writingScore)
                }
            }
        })
    }
}