package br.ifsp.edu.dmo1.noodle.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.ui.listeners.WorkItemListener
import br.ifsp.edu.dmo1.noodle.databinding.WorkItemBinding
import br.ifsp.edu.dmo1.noodle.ui.view.HomeActivity

class WorkAdapter(private val listener: WorkItemListener) :
    RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    private var dataset: List<Work> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.work_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val work = dataset[position]

        holder.binding.taskName.text = work.name
        holder.binding.taskDeadline.text = work.deadLine

        holder.binding.viewWork.setOnLongClickListener {
            val homeActivity = holder.itemView.context as HomeActivity
            homeActivity.showWorksFragment()

            listener.click(position)
            true
        }
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
    fun submitDataset(data: List<Work>) {
        dataset = data
        this.notifyDataSetChanged()
    }
    fun getDatasetItem(position: Int): Work {
        return dataset[position]
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: WorkItemBinding = WorkItemBinding.bind(view)
    }
}