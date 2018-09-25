package com.alokomkar.truecrawller.ui

import android.arch.lifecycle.LiveData
import com.alokomkar.truecrawller.base.BaseViewModel
import com.alokomkar.truecrawller.data.CharacterRequest
import com.alokomkar.truecrawller.data.DataAPI
import com.alokomkar.truecrawller.data.RequestRepository

class RequestViewModel( private val repository : RequestRepository ) : BaseViewModel(), DataAPI {

    override fun execute(url: String)
        = repository.execute( url )

    override fun fetchLiveData(): LiveData<List<CharacterRequest>>
        = repository.fetchLiveData()

    override fun fetchError(): LiveData<String>
        = repository.fetchError()

}