package com.alokomkar.truecrawller.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.alokomkar.truecrawller.api.RequestService
import com.alokomkar.truecrawller.api.RequestServiceTask

class RequestRepositoryImpl : RequestRepository, RequestService {

    private lateinit var requestService: RequestService
    private val mutableContent = MutableLiveData<List<CharacterRequest>>()
    private val mutableError = MutableLiveData<String>()

    override fun onDataFetched( contentList: List<CharacterRequest> )
            = mutableContent.postValue( contentList )

    override fun onError(error: String)
            = mutableError.postValue( error )

    override fun fetchLiveData(): LiveData<List<CharacterRequest>>
            = mutableContent

    override fun fetchError(): LiveData<String>
            = mutableError

    @Synchronized
    override fun execute(url: String) {
        requestService = RequestServiceTask.getNewRequestService( this )
        requestService.execute( url )
    }

}