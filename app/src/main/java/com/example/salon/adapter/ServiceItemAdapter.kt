package com.example.salon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.BR
import com.example.salon.data.Service
import com.example.salon.databinding.LayoutServicesItemBinding

class ServiceItemAdapter() : RecyclerView.Adapter<ServiceItemAdapter.ItemViewHolder>() {

    private val serviceList = mutableListOf<Service>()
    private var itemClickListener: OnItemClickListener? = null

    fun setServiceList(list: List<Service>) {
        serviceList.clear()
        serviceList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(clickListener: OnItemClickListener) {
        itemClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutServicesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(serviceList[position])
    }

    override fun getItemCount(): Int = serviceList.size

    class ItemViewHolder(private val binding: LayoutServicesItemBinding, private val itemClickListener: OnItemClickListener?): RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.setVariable(BR.service, service)
            binding.executePendingBindings()

            binding.next.setOnClickListener {
                itemClickListener?.onItemClicked(service)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(service: Service)
    }
}