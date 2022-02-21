package com.example.salon.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon.R
import com.example.salon.adapter.CartAdapter
import com.example.salon.databinding.FragmentCartBinding
import com.example.salon.itemdecoration.SpaceItemDecoration
import com.example.salon.data.Cart
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.viewmodel.CartViewModel
import com.example.salon.viewmodel.HomeViewModel
import timber.log.Timber

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel by activityViewModels<CartViewModel>()

    private val cartAdapter = CartAdapter()
    private lateinit var binding: FragmentCartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCartBinding.bind(view.findViewById(R.id.root))

        binding.cartList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.cart_item_space)))
        }
        viewModel.cartListLiveData.observe(viewLifecycleOwner, observer)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (isAdded && !hidden) {
            binding.cartList.removeAllViewsInLayout()
            viewModel.getCartList()
        }
    }

    private val observer = Observer<List<Cart>> {
        cartAdapter.setCartList(it)
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}