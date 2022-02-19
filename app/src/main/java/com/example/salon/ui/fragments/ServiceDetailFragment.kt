package com.example.salon.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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

class ServiceDetailFragment : Fragment(R.layout.fragment_service_detail), View.OnClickListener, EmployeeItemAdapter.OnEmployeeSelectListener {

    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentServiceDetailBinding

    private var service: Service? = null

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

        binding.back.setOnClickListener(this)
        binding.addService.setOnClickListener(this)

        viewModel.employeesLiveData.observe(viewLifecycleOwner, observer)
        viewModel.fetchEmployees()
    }

    private val observer = Observer<List<Employee>> {
        employeeItemAdapter.setEmployeeList(it)
    }

    override fun onClick(v: View?) {

    }

    override fun onEmployeeSelected(employee: Employee) {

    }

    companion object {
        private const val SERVICE = "Service"
        fun newInstance(service: Service) = ServiceDetailFragment().apply {
            arguments = bundleOf(SERVICE to service)
        }
    }
}