package com.bty.android.exchangeapp.presentation.pairs.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bty.android.exchangeapp.R
import com.bty.android.exchangeapp.data.source_result.SourceResult
import com.bty.android.exchangeapp.databinding.FragmentPairsListBinding
import com.bty.android.exchangeapp.domain.model.CurrencyPair
import com.bty.android.exchangeapp.presentation.MainActivity
import com.bty.android.exchangeapp.presentation.pairs.adapter.CurrencyPairAdapter
import com.bty.android.exchangeapp.presentation.pairs.adapter.CurrencyPairItemClickListener
import com.bty.android.exchangeapp.presentation.pairs.adapter.CurrencyPairItemDecoration
import kotlinx.coroutines.launch
import javax.inject.Inject

class PairsFragment : Fragment(R.layout.fragment_pairs_list),
    CurrencyPairItemClickListener, AdapterView.OnItemSelectedListener {

    // решил не тянуть fragmentBindingProperty, а сделать по дефолтному юзкейсу гугла
    private var _binding: FragmentPairsListBinding? = null
    private val binding get() = _binding!!

    private val currencyPairsAdapter = CurrencyPairAdapter(this)
    private val onSortClickListener = View.OnClickListener {
        (activity as MainActivity).navigateToSort()
    }
    private val onRefreshClickListener = View.OnClickListener {
        pairsViewModel.fetchCurrencies()
    }

    @Inject
    lateinit var pairsViewModel: PairsViewModel

    private val fragmentType: PairsFragmentType by lazy {
        requireArguments().get(FRAGMENT_TYPE) as PairsFragmentType
    }

    companion object {
        private const val FRAGMENT_TYPE = "fragment_type"

        fun asPopular() = PairsFragment().apply {
            arguments = bundleOf(FRAGMENT_TYPE to PairsFragmentType.POPULAR)
        }

        fun asFavourite() = PairsFragment().apply {
            arguments = bundleOf(FRAGMENT_TYPE to PairsFragmentType.FAVOURITE)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainActivityComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPairsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViews()
        prepareViewModel()
    }

    override fun onFavouriteClick(item: CurrencyPair) {
        pairsViewModel.onFavouriteClick(item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val currency = parent?.getItemAtPosition(position) as? String
        currency?.let { pairsViewModel.onCurrencySelected(it) }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareViews() = with(binding) {
        pairsActionButton.setOnClickListener(onSortClickListener)

        pairsListEmptyButton.setOnClickListener {
            pairsViewModel.onCurrencySelected(pairsCurrenciesSpinner.selectedItem.toString())
        }

        pairsCurrenciesSpinner.onItemSelectedListener = this@PairsFragment

        pairsList.adapter = currencyPairsAdapter
        pairsList.addItemDecoration(CurrencyPairItemDecoration())
    }

    private fun prepareViewModel() = with(pairsViewModel) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    currencies.collect { state ->
                        when (state) {
                            SourceResult.Loading -> onCurrenciesLoading()
                            SourceResult.Empty -> onCurrenciesEmpty()
                            is SourceResult.Failure -> onCurrenciesFailure()
                            is SourceResult.Success -> onCurrenciesSuccess(state.value)
                        }
                    }
                }

                if (fragmentType == PairsFragmentType.POPULAR) {
                    launch {
                        popularPairs.collect { state ->
                            when (state) {
                                SourceResult.Loading -> onPairsLoading()
                                SourceResult.Empty -> onPopularPairsEmpty()
                                is SourceResult.Failure -> onPairsFailure()
                                is SourceResult.Success -> onPairsSuccess(state.value)
                            }
                        }
                    }
                } else {
                    launch {
                        favouritePairs.collect { state ->
                            when (state) {
                                SourceResult.Loading -> onPairsLoading()
                                SourceResult.Empty -> onFavouritePairsEmpty()
                                is SourceResult.Failure -> onPairsFailure()
                                is SourceResult.Success -> onPairsSuccess(state.value)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onCurrenciesLoading() = with(binding) {
        pairsCurrenciesSpinner.isInvisible = true
        pairsCurrenciesMessage.isVisible = true
        pairsCurrenciesMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        pairsCurrenciesMessage.setText(R.string.popular_currencies_loading)
        pairsActionButton.isEnabled = false
    }

    private fun onCurrenciesEmpty() = with(binding) {
        pairsCurrenciesSpinner.isInvisible = true
        pairsCurrenciesMessage.isVisible = true
        pairsCurrenciesMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        pairsCurrenciesMessage.setText(R.string.popular_currencies_empty)
        pairsActionButton.isEnabled = true
        pairsActionButton.setImageResource(R.drawable.ic_refresh)
        pairsActionButton.setOnClickListener(onRefreshClickListener)
    }

    private fun onCurrenciesFailure() = with(binding) {
        pairsCurrenciesSpinner.isInvisible = true
        pairsCurrenciesMessage.isVisible = true
        pairsCurrenciesMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        pairsCurrenciesMessage.setText(R.string.popular_currencies_failed)
        pairsActionButton.isEnabled = true
        pairsActionButton.setImageResource(R.drawable.ic_refresh)
        pairsActionButton.setOnClickListener(onRefreshClickListener)
    }

    private fun onCurrenciesSuccess(currencyContext: CurrencySelectionContext?) = with(binding) {
        pairsCurrenciesSpinner.isVisible = true
        pairsCurrenciesMessage.isInvisible = true
        pairsActionButton.isEnabled = true
        pairsActionButton.setImageResource(R.drawable.ic_sort)
        pairsActionButton.setOnClickListener(onSortClickListener)

        currencyContext?.let {
            pairsCurrenciesSpinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                currencyContext.currencies.map { "${it.code} - ${it.name}" }
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            if (currencyContext.selectedIndex != -1) {
                pairsCurrenciesSpinner.setSelection(currencyContext.selectedIndex)
            }
        }
    }

    private fun onPairsLoading() = with(binding) {
        pairsList.isInvisible = true
        pairsListProgressBar.isVisible = true
        pairsListEmptyHolder.isInvisible = true
        pairsListEmptyButton.isInvisible = true
    }

    private fun onPopularPairsEmpty() = with(binding) {
        pairsList.isInvisible = true
        pairsListProgressBar.isInvisible = true
        pairsListEmptyButton.isVisible = true
        pairsListEmptyHolder.isVisible = true
        pairsListEmptyHolder.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        pairsListEmptyHolder.setText(R.string.pairs_list_empty_holder)
    }

    private fun onFavouritePairsEmpty() = with(binding) {
        pairsList.isInvisible = true
        pairsListProgressBar.isInvisible = true
        pairsListEmptyButton.isInvisible = true
        pairsListEmptyHolder.isVisible = true
        pairsListEmptyHolder.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        pairsListEmptyHolder.setText(R.string.pairs_list_favourites_empty_holder)
    }

    private fun onPairsFailure() = with(binding) {
        pairsList.isInvisible = true
        pairsListProgressBar.isInvisible = true
        pairsListEmptyButton.isVisible = true
        pairsListEmptyHolder.isVisible = true
        pairsListEmptyHolder.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        pairsListEmptyHolder.setText(R.string.pairs_list_failure_holder)
    }

    private fun onPairsSuccess(pairs: List<CurrencyPair>?) = with(binding) {
        pairsList.isVisible = true
        pairsListEmptyHolder.isInvisible = true
        pairsListEmptyButton.isInvisible = true
        pairsListProgressBar.isInvisible = true

        pairs?.let { currencyPairsAdapter.setData(pairs) }
    }
}