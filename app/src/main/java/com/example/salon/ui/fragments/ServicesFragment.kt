package com.example.salon.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon.R
import com.example.salon.adapter.ServiceItemAdapter
import com.example.salon.data.Service
import com.example.salon.databinding.FragmentServicesBinding
import com.example.salon.itemdecoration.SpaceItemDecoration
import com.example.salon.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ServicesFragment : Fragment(R.layout.fragment_services), ServiceItemAdapter.OnItemClickListener {

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentServicesBinding
    private val serviceItemAdapter = ServiceItemAdapter().apply {
        setOnClickListener(this@ServicesFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentServicesBinding.bind(view.findViewById(R.id.root))

        viewModel.servicesLiveData.observe(viewLifecycleOwner, observer)
        viewModel.fetchServices()


        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceItemAdapter
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.service_item_space)))
        }
    }

    private val observer = Observer<List<Service>> {
        serviceItemAdapter.setServiceList(it)
    }

    override fun onItemClicked(service: Service) {
        childFragmentManager.commit {
            replace(R.id.container, ServiceDetailFragment.newInstance(service), SERVICE_DETAIL_TAG)
            binding.container.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = ServicesFragment()
        private const val SERVICE_DETAIL_TAG = "service detail"
    }
}