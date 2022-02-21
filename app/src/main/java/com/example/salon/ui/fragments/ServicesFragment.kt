package com.example.salon.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
class ServicesFragment : Fragment(R.layout.fragment_services), ServiceItemAdapter.OnItemClickListener, View.OnClickListener {

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentServicesBinding

    private lateinit var serviceDetailFragment: ServiceDetailFragment

    private val serviceItemAdapter = ServiceItemAdapter().apply {
        setOnClickListener(this@ServicesFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentServicesBinding.bind(view.findViewById(R.id.root))

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceItemAdapter
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.service_item_space)))
        }

        binding.toolbar.setNavigationOnClickListener {
            onRemoveFragment()
        }
        binding.error.setOnClickListener(this)

        viewModel.servicesLiveData.observe(viewLifecycleOwner, serviceObserver)
        viewModel.servicesErrorLiveData.observe(viewLifecycleOwner, serviceErrorObserver)
        viewModel.fetchServices(requireContext())
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (isAdded && !hidden)
            viewModel.fetchServices(requireContext())
    }

    private val serviceObserver = Observer<List<Service>> {
        serviceItemAdapter.setServiceList(it)
    }

    private val serviceErrorObserver = Observer<String> {
        binding.error.text = it
        binding.error.isVisible = it.isNotEmpty()
    }

    private fun updateToolBar(isServiceDetailScreen: Boolean, service: Service? = null) {
        val serviceTitle = service?.name?.split(" ")?.joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        val navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.back_icon)
        val servicePrice = getString(R.string.price, service?.price?.toDouble())

        binding.toolbar.title = if (isServiceDetailScreen) serviceTitle else getString(R.string.salon_title)
        binding.toolbar.navigationIcon = if (isServiceDetailScreen) navigationIcon else null
        binding.price.text = if (isServiceDetailScreen) servicePrice else ""

        binding.price.isVisible = isServiceDetailScreen
        binding.container.isVisible = isServiceDetailScreen
    }

    private fun onRemoveFragment() {
        childFragmentManager.commit {
            if (::serviceDetailFragment.isInitialized) {
                remove(serviceDetailFragment)
                updateToolBar(false)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.error -> {
                viewModel.fetchServices(requireContext())
            }
        }
    }

    override fun onItemClicked(service: Service) {
        childFragmentManager.commit {
            serviceDetailFragment = ServiceDetailFragment.newInstance(service)
            replace(R.id.container, serviceDetailFragment, SERVICE_DETAIL_TAG)
            updateToolBar(true, service)
        }
    }

    companion object {
        fun newInstance() = ServicesFragment()
        private const val SERVICE_DETAIL_TAG = "service detail"
    }
}