package br.ifsp.edu.dmo1.noodle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.model.auxiliar.AddUser
import br.ifsp.edu.dmo1.noodle.databinding.AddUserItemBinding
import br.ifsp.edu.dmo1.noodle.ui.listeners.CourseUserItemListener

class AddUserAdapter(private val listener: CourseUserItemListener) :
    RecyclerView.Adapter<AddUserAdapter.ViewHolder>() {

    private var dataset: List<AddUser> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddUserAdapter.ViewHolder, position: Int) {
        val user = dataset[position]

        holder.binding.userName.text = user.name
        holder.binding.userRecord.text = user.record
        holder.binding.UserRole.isChecked = user.professor
        holder.binding.cbAddUser.isChecked = user.added

        holder.binding.UserRole.setOnCheckedChangeListener { _, isChecked ->
            user.professor = isChecked
        }
        holder.binding.cbAddUser.setOnCheckedChangeListener { _, isChecked ->
            user.added = isChecked
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<AddUser>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): AddUser {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: AddUserItemBinding = AddUserItemBinding.bind(view)
    }
    fun getSelectedUsers(): List<AddUser> {
        return dataset.filter { it.added }
    }
}