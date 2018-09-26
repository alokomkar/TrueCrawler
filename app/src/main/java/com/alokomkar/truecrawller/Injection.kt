package com.alokomkar.truecrawller

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.alokomkar.truecrawller.data.RequestRepository
import com.alokomkar.truecrawller.data.RequestRepositoryImpl
import com.alokomkar.truecrawller.ui.ViewModelFactory

//Manual DI
object Injection {

    /**
     * Creates an instance of [RequestRepositoryImpl] based on the [WeatherService]
     */
    private fun provideRequestRepository(context: Context): RequestRepository {
        return RequestRepositoryImpl( )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideRequestRepository(context))
    }
}