package br.ifsp.edu.dmo1.noodle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.databinding.LessonItemBinding
import br.ifsp.edu.dmo1.noodle.ui.listeners.LessonItemListener

class LessonAdapter(private val listener: LessonItemListener) :
    RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    private var dataset: List<Lesson> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lesson_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonAdapter.ViewHolder, position: Int) {
        val lesson = dataset[position]

        holder.binding.lessonName.text = lesson.name
        holder.binding.lessondescription.text = lesson.description
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<Lesson>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): Lesson {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: LessonItemBinding = LessonItemBinding.bind(view)
    }
}