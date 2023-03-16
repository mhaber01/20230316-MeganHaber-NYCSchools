package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.school_data.HighSchoolViewModel

/** Activity to display each high schools in NYC area's average SAT scores. */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinner: Spinner

    private val viewModel: HighSchoolViewModel by viewModels()
    private val schoolNameArrayList = ArrayList<String>()
    private val schoolIdArrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinner = binding.spinner

        initializeUi()
    }

    private fun initializeUi() {
        viewModel.schools.observe(this, Observer { schools ->
            schools.forEach { school ->
                schoolNameArrayList.add(school.name)
                schoolIdArrayList.add(school.id)
            }
        })
        setSpinnerAdapter()
        listenForSpinnerItemSelection()
    }

    private fun setSpinnerAdapter() {
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schoolNameArrayList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dataAdapter.notifyDataSetChanged();
        spinner.adapter = dataAdapter
    }

    private fun listenForSpinnerItemSelection() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                Toast.makeText(applicationContext, pos.toString(), Toast.LENGTH_SHORT).show()
                viewModel.scores.observe(this@MainActivity, Observer { scores ->
                    for (score in scores) {
                        if (score.id == schoolIdArrayList[pos]) {
                            binding.mathSatScore.text = score.math
                            binding.readingSatScore.text = score.reading
                            binding.writingSatScore.text = score.writing
                        }
                    }
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}