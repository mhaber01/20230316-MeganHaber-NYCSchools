package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.school_data.HighSchoolViewModel
import com.example.myapplication.databinding.ActivityMainBinding

/** Activity for NYC high schools' average SAT scores. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HighSchoolViewModel by viewModels()
    private val schoolNameArrayList = ArrayList<String>()
    private val schoolIdArrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeUi()
        setSpinner(binding.spinner)

    }

    private fun initializeUi() {
        viewModel.schools.observe(this, Observer { schools ->
            schools.forEach { school ->
                schoolNameArrayList.add(school.name)
                schoolIdArrayList.add(school.id)
            }
        })
    }

    private fun setSpinner(spinner: Spinner) {
        var dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schoolNameArrayList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = dataAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                viewModel.getScoresFromSchool(schoolIdArrayList[pos])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}