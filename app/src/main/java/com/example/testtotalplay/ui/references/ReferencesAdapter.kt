package com.example.testtotalplay.ui.references

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtotalplay.databinding.ItemReferencesBinding
import com.example.testtotalplay.domain.model.References

class ReferencesAdapter : RecyclerView.Adapter<ReferencesAdapter.ViewHolder>()  {

    private val list : ArrayList<References> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items : List<References>){
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferencesAdapter.ViewHolder {
        val itemBinding = ItemReferencesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ReferencesAdapter.ViewHolder, position: Int) {
        val item  = list[position]

        holder.binding.tvReferences.text = item.reference
        holder.binding.imgLogoBank
        Glide
            .with(holder.itemView)
            .load(item.reference)
            .centerCrop()
            .into(holder.binding.imgLogoBank)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding : ItemReferencesBinding,) : RecyclerView.ViewHolder(binding.root)
}