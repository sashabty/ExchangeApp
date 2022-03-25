package com.bty.android.exchangeapp.di.component

import com.bty.android.exchangeapp.di.module.active.MainActivityModule
import com.bty.android.exchangeapp.di.module.active.DaoModule
import com.bty.android.exchangeapp.di.module.active.RepositoryModule
import com.bty.android.exchangeapp.di.module.active.ViewModelModule
import com.bty.android.exchangeapp.di.scope.MainActivityScope
import com.bty.android.exchangeapp.presentation.MainActivity
import com.bty.android.exchangeapp.presentation.pairs.fragment.PairsFragment
import com.bty.android.exchangeapp.presentation.sort_selection.SortSelectionFragment
import dagger.Subcomponent

@MainActivityScope
@Subcomponent(
    modules = [
        MainActivityModule::class,
        ViewModelModule::class,
        DaoModule::class,
        RepositoryModule::class
    ]
)
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun inject(activity: MainActivity)
    fun inject(pairsFr: PairsFragment)
    fun inject(sortFr: SortSelectionFragment)
}