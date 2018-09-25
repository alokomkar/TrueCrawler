package com.alokomkar.truecrawller.data

interface RequestRepository : DataAPI {
    fun onError( error : String )
}