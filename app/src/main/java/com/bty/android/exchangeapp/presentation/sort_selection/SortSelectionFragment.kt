package com.bty.android.exchangeapp.presentation.sort_selection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bty.android.exchangeapp.R
import com.bty.android.exchangeapp.databinding.FragmentSortSelectionBinding
import com.bty.android.exchangeapp.presentation.MainActivity
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SortSelectionFragment: Fragment(R.layout.fragment_sort_selection) {

    private var _binding: FragmentSortSelectionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pairsViewModel: PairsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainActivityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViews()
        prepareViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareViews() = with(binding) {
        sortSelectionAlphabetAsc.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) { onSortSelected(checkBox) }
        }
        sortSelectionAlphabetDesc.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) { onSortSelected(checkBox) }
        }
        sortSelectionValueAsc.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) { onSortSelected(checkBox) }
        }
        sortSelectionValueDesc.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) { onSortSelected(checkBox) }
        }
    }

    private fun prepareViewModel() = with(pairsViewModel) {
        lifecycleScope.launch {
            sortSelection.flowWithLifecycle(lifecycle).collect { selection ->
                when (selection) {
                    SortSelection.AlphabetAsc -> {
                        binding.sortSelectionAlphabetAsc.isChecked = true
                    }
                    SortSelection.AlphabetDesc -> {
                        binding.sortSelectionAlphabetDesc.isChecked = true
                    }
                    SortSelection.ValueAsc -> {
                        binding.sortSelectionValueAsc.isChecked = true
                    }
                    SortSelection.ValueDesc -> {
                        binding.sortSelectionValueDesc.isChecked = true
                    }
                }
            }
        }
    }

    private fun onSortSelected(view: View) = with(binding) {
        when (view) {
            sortSelectionAlphabetAsc -> {
                sortSelectionAlphabetDesc.isChecked = false
                sortSelectionValueAsc.isChecked = false
                sortSelectionValueDesc.isChecked = false
                pairsViewModel.onSortSelectionUpdate(SortSelection.AlphabetAsc)
            }
            sortSelectionAlphabetDesc -> {
                sortSelectionAlphabetAsc.isChecked = false
                sortSelectionValueAsc.isChecked = false
                sortSelectionValueDesc.isChecked = false
                pairsViewModel.onSortSelectionUpdate(SortSelection.AlphabetDesc)
            }
            sortSelectionValueAsc -> {
                sortSelectionAlphabetAsc.isChecked = false
                sortSelectionAlphabetDesc.isChecked = false
                sortSelectionValueDesc.isChecked = false
                pairsViewModel.onSortSelectionUpdate(SortSelection.ValueAsc)
            }
            sortSelectionValueDesc -> {
                sortSelectionAlphabetAsc.isChecked = false
                sortSelectionAlphabetDesc.isChecked = false
                sortSelectionValueAsc.isChecked = false
                pairsViewModel.onSortSelectionUpdate(SortSelection.ValueDesc)
            }
        }
    }
}