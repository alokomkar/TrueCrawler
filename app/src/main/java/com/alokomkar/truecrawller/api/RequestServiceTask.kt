package com.alokomkar.truecrawller.api

import android.os.AsyncTask
import android.util.Log
import com.alokomkar.truecrawller.data.CharacterRequest
import com.alokomkar.truecrawller.data.RequestType
import org.jsoup.Jsoup
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.isNotEmpty
import kotlin.collections.set

@Suppress("PrivatePropertyName", "StaticFieldLeak")
class RequestServiceTask
private constructor(requestService: RequestService) :
        AsyncTask<String, Void, ArrayList<String>?>(), RequestService {

    private val CONTENT_TAG = "content"
    private var requestService : RequestService ?= requestService
    private val contentList = ArrayList<CharacterRequest>()
    private var currentUrl : String = ""

    override fun onDataFetched(contentList: List<CharacterRequest>) {
        requestService?.onDataFetched( contentList )
    }

    override fun onError(error: String) {
        requestService?.onError(error)
    }

    override fun execute(url: String) {
        this.currentUrl = url
        this.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR, url )
    }

    override fun doInBackground(vararg params: String?): ArrayList<String>? {

        return if( params.isNotEmpty() ) {
            try {
                val url = params[0]

                val document =
                        Jsoup.connect( url )
                                .get()
                val paragraphs = document.getElementsContainingText(CONTENT_TAG)
                Log.d( CONTENT_TAG, paragraphs.toString() )

                val paraList = ArrayList<String>()

                for( para in paragraphs )
                    paraList.addAll(ArrayList(para.text()
                            .replace("[-+.^:,;']","")
                            .replace("  ", " ")
                            .split(" ")))

                paraList

            } catch ( e: Exception ) {
                e.printStackTrace()
                null
            }
        }
        else null

    }

    override fun onPostExecute( paragraphs : ArrayList<String>? ) {
        super.onPostExecute(paragraphs)
        contentList.clear()
        if( paragraphs != null ) {
            wordOperation( RequestType.WordCounter, paragraphs )
            wordOperation( RequestType.TenthCharacter, paragraphs )
            wordOperation( RequestType.EveryTenthCharacter, paragraphs )
        }
        else onError("Unable to fetch data" )

    }

    private fun wordOperation( requestType : RequestType, paragraphs: ArrayList<String> ) {

        object : AsyncTask<Void, Void, CharacterRequest>() {
            override fun doInBackground(vararg p0: Void?): CharacterRequest {
                val characterRequest = CharacterRequest( requestType, currentUrl )

                when( requestType ) {
                    RequestType.EveryTenthCharacter -> {

                        if( paragraphs.size > 10 ) {
                            var index = 10
                            while( index < paragraphs.size ) {
                                //Replace this with String builder
                                characterRequest.details += index.toString() + "th Word : " + paragraphs[index]
                                index += 10
                            }
                        }
                        else
                            characterRequest.error = "Less than 10 words"

                    }
                    RequestType.WordCounter -> {

                        val countMap = HashMap<String, Int>()
                        for( word in paragraphs )
                            countMap[word] = if( countMap.containsKey( word ))  countMap[word]!! + 1 else 1

                        if( countMap.isEmpty() )
                            characterRequest.error = "No words"
                        else
                            characterRequest.details = "All Words Count : \n $countMap"

                    }
                    RequestType.TenthCharacter -> {

                        if( paragraphs.size > 10 )
                            characterRequest.details = "10th Word : " + paragraphs[10]
                        else
                            characterRequest.error = "Less than 10 words"

                    }
                }

                return characterRequest
            }

            override fun onPostExecute(result: CharacterRequest) {
                super.onPostExecute(result)
                contentList.add( result )
                onDataFetched( contentList )
            }
        }.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR )
    }

    companion object {
        fun getNewRequestService( requestService: RequestService ): RequestService {
            return RequestServiceTask( requestService )
        }
    }
}