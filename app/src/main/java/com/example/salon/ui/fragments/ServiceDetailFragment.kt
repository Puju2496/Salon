package com.example.salon.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salon.BR
import com.example.salon.R
import com.example.salon.adapter.EmployeeItemAdapter
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.databinding.FragmentServiceDetailBinding
import com.example.salon.itemdecoration.SpaceItemDecoration
import com.example.salon.viewmodel.HomeViewModel
import java.util.stream.Collectors

class ServiceDetailFragment : Fragment(R.layout.fragment_service_detail), View.OnClickListener,
    EmployeeItemAdapter.OnEmployeeSelectListener {

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentServiceDetailBinding

    private var service: Service? = null
    private var selectedEmployeeList: LinkedHashMap<Service, ArrayList<Employee>> = linkedMapOf()

    private val employeeItemAdapter = EmployeeItemAdapter().apply {
        setOnClickListener(this@ServiceDetailFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        service = arguments?.getParcelable(SERVICE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentServiceDetailBinding.bind(view.findViewById(R.id.root))
        binding.setVariable(BR.service, service)

        binding.employeeList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = employeeItemAdapter
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.employee_space)))
        }

        binding.addService.setOnClickListener(this)
        binding.error.setOnClickListener(this)

        viewModel.employeesLiveData.observe(viewLifecycleOwner, employeeListObserver)
        viewModel.employeesErrorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.fetchEmployees(requireContext()).also {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private val employeeListObserver = Observer<List<Employee>> {
        binding.progressBar.visibility = View.GONE
        employeeItemAdapter.setEmployeeList(it)
    }

    private val errorObserver = Observer<String> {
        binding.progressBar.visibility = View.GONE
        binding.error.text = it
        binding.employeeList.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.error.isVisible = it.isNotEmpty()
    }

    private fun updateAddServiceToggle() {
        binding.addService.isEnabled = !selectedEmployeeList.isNullOrEmpty()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addService -> {
                val mappedData = selectedEmployeeList.keys.stream()
                    .collect(Collectors.groupingBy { selectedEmployeeList[it] })

                viewModel.addToCart(
                    requireContext(),
                    mappedData.keys.first(),
                    mappedData.values.first() as ArrayList<Service>
                )

            }
            R.id.error -> {
                binding.error.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                viewModel.fetchEmployees(requireContext())
            }
        }
    }

    override fun onEmployeeSelected(employee: Employee) {
        service?.apply {
            if (!selectedEmployeeList.containsKey(this))
                selectedEmployeeList[this] = arrayListOf()
            selectedEmployeeList[this]?.add(employee)
        }
        updateAddServiceToggle()
    }

    override fun onEmployeeUnselected(employee: Employee) {
        selectedEmployeeList[service]?.apply {
            remove(employee)
            if (count() == 0)
                selectedEmployeeList.remove(service)
        }
        updateAddServiceToggle()
    }

    companion object {
        private const val SERVICE = "Service"
        fun newInstance(service: Service) = ServiceDetailFragment().apply {
            arguments = bundleOf(SERVICE to service)
        }
    }
}