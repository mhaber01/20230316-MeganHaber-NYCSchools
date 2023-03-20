package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivitySchoolListBinding
import com.example.myapplication.school_data.HighSchool
import com.example.myapplication.school_data.HighSchoolViewModel

const val SCHOOL_ID = "school_id"

/** Activity to display each high school in the NYC area in a [RecyclerView] format. */
class HighSchoolListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolListBinding
    private val highSchoolViewModel: HighSchoolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySchoolListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val schoolArrayList: ArrayList<HighSchool> = ArrayList()
        highSchoolViewModel.schools.observe(this, Observer { schools ->
            schools.forEach { school ->
                schoolArrayList.add(school)
            }

            val recyclerView: RecyclerView = binding.schoolListRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
            recyclerView.adapter =
                HighSchoolAdapter(this.applicationContext, schoolArrayList) { school ->
                    showScoresOnClick(school)
                }
        })
    }

    private fun showScoresOnClick(highSchool: HighSchool) {
        val intent = Intent(this, HighSchoolScoresActivity()::class.java)
        intent.putExtra(SCHOOL_ID, highSchool.id)
        startActivity(intent)
    }
}
