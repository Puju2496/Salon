package com.example.salon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.BR
import com.example.salon.adapter.EmployeeItemAdapter.ItemViewHolder
import com.example.salon.data.Employee
import com.example.salon.databinding.LayoutEmployeesItemBinding

class EmployeeItemAdapter: RecyclerView.Adapter<ItemViewHolder>() {

    private val employeeList = mutableListOf<Employee>()
    private var onEmployeeSelectListener: OnEmployeeSelectListener? = null

    fun setEmployeeList(list: List<Employee>) {
        employeeList.clear()
        employeeList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClickListener(clickListener: OnEmployeeSelectListener) {
        onEmployeeSelectListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutEmployeesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onEmployeeSelectListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(employeeList[position])
    }

    override fun getItemCount(): Int = employeeList.size

    class ItemViewHolder(private val binding: LayoutEmployeesItemBinding, private val clickListener: OnEmployeeSelectListener?): RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee) {
            binding.setVariable(BR.employee, employee)
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                binding.root.isSelected = !binding.root.isSelected
                binding.selected.isVisible = binding.root.isSelected
            }
        }
    }

    interface OnEmployeeSelectListener {
        fun onEmployeeSelected(employee: Employee)
    }
}