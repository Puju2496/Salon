package com.example.salon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.salon.R
import com.example.salon.databinding.ActivityHomeBinding
import com.example.salon.ui.fragments.CartFragment
import com.example.salon.ui.fragments.ServicesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private lateinit var binding: ActivityHomeBinding
    private val servicesFragment = ServicesFragment.newInstance()
    private val cartFragment = CartFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.bind(findViewById(R.id.root))

        supportFragmentManager.commit {
            add(R.id.container, servicesFragment, SERVICE_TAG)
        }

        binding.bottomNav.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.service -> {
                    supportFragmentManager.commit {
                        replace(R.id.container, servicesFragment, SERVICE_TAG)
                    }
                    true
                }
                R.id.cart -> {
                    supportFragmentManager.commit {
                        replace(R.id.container, cartFragment, CART_TAG)
                    }
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val SERVICE_TAG = "service"
        private const val SERVICE_DETAIL_TAG = "service detail"
        private const val CART_TAG = "cart"
    }
}