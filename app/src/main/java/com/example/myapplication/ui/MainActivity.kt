package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.school_data.HighSchoolViewModel

/** Activity to display each high school in the NYC area's average SAT scores. */
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
        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, schoolNameArrayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }

    private fun listenForSpinnerItemSelection() {
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                Toast.makeText(applicationContext, pos.toString(), Toast.LENGTH_SHORT).show()
                viewModel.scores.observe(this@MainActivity, Observer { scores ->
                    for (score in scores) {
                        if (score.id == schoolIdArrayList[pos]) {
                            binding.mathSatScore.text = score.reading
                            binding.readingSatScore.text = score.reading
                            binding.writingSatScore.text = score.writing
                            break
                        }
                    }
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
