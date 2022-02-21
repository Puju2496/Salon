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
            add(R.id.container, cartFragment, CART_TAG)
            add(R.id.container, servicesFragment, SERVICE_TAG)

            hide(cartFragment)
        }

        binding.bottomNav.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.service -> {
                    supportFragmentManager.commit {
                        hide(cartFragment)
                            .show(servicesFragment)
                    }
                    true
                }
                R.id.cart -> {
                    supportFragmentManager.commit {
                        hide(servicesFragment)
                            .show(cartFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val SERVICE_TAG = "service"
        private const val CART_TAG = "cart"
    }
}