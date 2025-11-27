package com.example.mynotesapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp2.databinding.ItemMainBinding

class DataAdapter(private val data: List<Data>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(binding);
    }

    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int
    ) {
        val currentItem = data[position]
//        holder.binding.apply {
//            txtUseId.text = "UserId : ${currentItem.userId}"
//            txtId.text = "Id : ${currentItem.id}"
//            txtTitle.text = "Title : ${currentItem.title}"
//            txtBody.text = "Body : ${currentItem.body}"
//        }
        holder.binding.data = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class DataViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root){}
}