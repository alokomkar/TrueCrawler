package com.alokomkar.truecrawller.api

import com.alokomkar.truecrawller.data.CharacterRequest

interface RequestService {
    fun onDataFetched( contentList : List<CharacterRequest> )
    fun onError( error : String )
    fun execute( url : String )
}