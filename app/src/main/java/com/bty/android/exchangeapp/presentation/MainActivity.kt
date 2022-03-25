package com.bty.android.exchangeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bty.android.exchangeapp.R
import com.bty.android.exchangeapp.core.ExchangeApp
import com.bty.android.exchangeapp.databinding.ActivityMainBinding
import com.bty.android.exchangeapp.di.component.MainActivityComponent
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsFragment
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsFragmentType
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsViewModel
import com.bty.android.exchangeapp.presentation.sort_selection.SortSelectionFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mainActivityComponent: MainActivityComponent

    @Inject
    lateinit var pairsViewModel: PairsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = ExchangeApp.instance.appComponent.mainActivityComponent().create()
        mainActivityComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            mainFragmentNavigationMenu.setOnItemSelectedListener { item ->
                return@setOnItemSelectedListener when (item.itemId) {
                    R.id.main_menu_popular -> {
                        navigateToPopular()
                        true
                    }
                    R.id.main_menu_favourite -> {
                        navigateToFavourite()
                        true
                    }
                    else -> false
                }
            }
        }

        navigateToPopular()
    }


    fun navigateToSort() {
        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, SortSelectionFragment())
            addToBackStack(SortSelectionFragment::class.simpleName)
        }
    }

    private fun navigateToPopular() {
        supportFragmentManager.commit {
            if (supportFragmentManager.findFragmentById(R.id.main_fragment_container) == null) {
                add(R.id.main_fragment_container, PairsFragment.asPopular())
            } else {
                replace(R.id.main_fragment_container, PairsFragment.asPopular())
            }
        }
    }

    private fun navigateToFavourite() {
        supportFragmentManager.commit {
            if (supportFragmentManager.findFragmentById(R.id.main_fragment_container) == null) {
                add(R.id.main_fragment_container, PairsFragment.asFavourite())
            } else {
                replace(R.id.main_fragment_container, PairsFragment.asFavourite())
            }
        }
    }
}