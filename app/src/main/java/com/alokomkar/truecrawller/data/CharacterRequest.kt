package com.alokomkar.truecrawller.data

data class CharacterRequest( val requestType : RequestType,
                             val url : String,
                             var details : String = "",
                             var error : String = "" )