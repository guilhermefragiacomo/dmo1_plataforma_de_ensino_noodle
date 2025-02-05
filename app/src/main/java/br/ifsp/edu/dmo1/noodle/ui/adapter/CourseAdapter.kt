package br.ifsp.edu.dmo1.noodle.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.databinding.CourseItemBinding
import br.ifsp.edu.dmo1.noodle.ui.listeners.CourseItemListener

class CourseAdapter(private val listener: CourseItemListener) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private var dataset: List<Course> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = dataset[position]

        holder.binding.tvName.text = course.name
        holder.binding.tvDescription.text = course.description

        holder.itemView.setOnClickListener {
            listener.onCourseClick(course)
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<Course>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): Course {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: CourseItemBinding = CourseItemBinding.bind(view)
    }
}