package com.example.salon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.databinding.LayoutCartItemBinding
import com.example.salon.itemdecoration.SpaceItemDecoration
import com.example.salon.data.Cart
import okhttp3.internal.notifyAll
import timber.log.Timber

class CartAdapter() : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    private var cartList: MutableList<Cart> = mutableListOf()

    fun setCartList(list: List<Cart>) {
        cartList.clear()
        cartList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            LayoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int = cartList.size

    class ItemViewHolder(private val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: Cart) {
            binding.employees.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = CartItemAdapter<Employee>().apply {
                    setCartItemList(cart.employees)
                }
                addItemDecoration(SpaceItemDecoration(context.resources.getDimensionPixelOffset(R.dimen.profile_item_space)))
            }

            binding.services.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CartItemAdapter<Service>().apply {
                    setCartItemList(cart.services)
                }
                addItemDecoration(SpaceItemDecoration(context.resources.getDimensionPixelOffset(R.dimen.cart_services_item_space)))
            }
        }
    }
}