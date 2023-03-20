package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.school_data.HighSchool

/**
 * The [RecyclerView.Adapter] for displaying a list of [HighSchool]'s in the NYC area.
 * @property context passes the application context
 * @property schoolArrayList holds an [ArrayList] of [HighSchool] data
 * @property onClick listens for item click behavior
 */
class HighSchoolAdapter(
    private val context: Context,
    private val schoolArrayList: ArrayList<HighSchool>,
    private val onClick: (HighSchool) -> Unit) :
    RecyclerView.Adapter<HighSchoolAdapter.HighSchoolViewHolder>() {

    /** ViewHolder for the [HighSchoolAdapter] that takes an inflated view and click behavior. */
    class HighSchoolViewHolder(itemView: View, val onClick: (HighSchool) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val schoolNameTextView: TextView
        private var currentSchool: HighSchool? = null

        init {
            schoolNameTextView = itemView.findViewById(R.id.school_name)

            itemView.setOnClickListener {
                currentSchool?.let {
                    onClick(it)
                }
            }
        }

        /** Binds the [HighSchool] name to the view. */
        fun bind (highSchool: HighSchool) {
            currentSchool = highSchool
            schoolNameTextView.text = highSchool.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighSchoolViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.school_row_item, parent, false)
        return HighSchoolViewHolder(view, onClick)
    }

    override fun getItemCount(): Int {
        return schoolArrayList.size
    }

    override fun onBindViewHolder(holder: HighSchoolViewHolder, position: Int) {
        val highSchool = schoolArrayList[position]
        holder.bind(highSchool)
    }
}
