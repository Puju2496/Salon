package com.example.salon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.databinding.LayoutProfileItemBinding
import com.example.salon.databinding.LayoutServiceItemBinding
import timber.log.Timber

class CartItemAdapter<T> : RecyclerView.Adapter<CartItemAdapter.ItemViewHolder<T>>() {

    private var cartItemList = mutableListOf<T?>()

    fun setCartItemList(list: List<T?>?) {
        cartItemList.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        var binding: ViewDataBinding? = null
        when (viewType)  {
            EMPLOYEE -> binding = LayoutProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SERVICE -> binding = LayoutServiceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        holder.bind(cartItemList[position])
    }

    override fun getItemCount(): Int = cartItemList.size
    override fun getItemViewType(position: Int): Int = if (cartItemList[position] is Employee) EMPLOYEE else SERVICE

    class ItemViewHolder<T>(private val binding: ViewDataBinding?) :
        RecyclerView.ViewHolder(binding?.root!!) {

        fun bind(cart: T?) {
            when (cart) {
                is Employee -> (binding as LayoutProfileItemBinding).setVariable(BR.profile, cart)
                is Service -> (binding as LayoutServiceItemBinding).setVariable(BR.cartService, cart)
            }
            binding?.executePendingBindings()
        }
    }
    companion object {
        private const val EMPLOYEE = 1
        private const val SERVICE = 2
    }
}