package com.alokomkar.truecrawller.data

import android.arch.lifecycle.LiveData
import com.alokomkar.truecrawller.base.BaseAPI

interface DataAPI : BaseAPI<CharacterRequest> {
    fun fetchLiveData() : LiveData<List<CharacterRequest>>
    fun fetchError() : LiveData<String>
    fun execute( url : String )
}