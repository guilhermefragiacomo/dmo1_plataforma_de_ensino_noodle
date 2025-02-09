package br.ifsp.edu.dmo1.noodle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.databinding.LessonItemBinding
import br.ifsp.edu.dmo1.noodle.databinding.UserItemBinding
import br.ifsp.edu.dmo1.noodle.ui.listeners.LessonItemListener
import br.ifsp.edu.dmo1.noodle.ui.listeners.UserItemListener

class UserAdapter(private val listener: UserItemListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var dataset: List<User> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = dataset[position]

        holder.binding.userName.text = user.name
        holder.binding.userRecord.text = user.record
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<User>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): User {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: UserItemBinding = UserItemBinding.bind(view)
    }
}