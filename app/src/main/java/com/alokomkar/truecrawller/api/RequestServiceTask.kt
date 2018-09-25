package com.alokomkar.truecrawller.api

import android.os.AsyncTask
import com.alokomkar.truecrawller.data.CharacterRequest
import org.jsoup.Jsoup
import java.util.HashSet

@Suppress("PrivatePropertyName")
class RequestServiceTask
private constructor(requestService: RequestService) :
        AsyncTask<String, Void, List<CharacterRequest>>(), RequestService {

    private val CONTENT_TAG = "content"
    private var requestService : RequestService ?= requestService

    override fun onDataFetched(contentList: List<CharacterRequest>) {
        requestService?.onDataFetched( contentList )
    }

    override fun onError(error: String) {
        requestService?.onError(error)
    }

    override fun execute(url: String) {
        this.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR, url )
    }

    override fun doInBackground(vararg params: String?): List<CharacterRequest> {

        val contentList = ArrayList<CharacterRequest>()
        if( params.isNotEmpty() ) {
            try {
                val url = params[0]

                val document =
                        Jsoup.connect( url )
                                .get()
                val paragraphs = document.getElementsContainingText(CONTENT_TAG)
                val hashSet = HashSet<String>()

                //Eliminate duplicate sentences
                for( para in paragraphs )
                    hashSet.add(para.text())

                //TODO : Complete this
                //TODO : 1. Find the 10th character and display it on the screen

                //TODO : 2. Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the
                //screen

                //TODO : 3. Split the text into words using whitespace characters (i.e. space, tab, line break, etc.),
                // count the occurrence of every word (case insensitive) and display the output on the screen

            } catch ( e: Exception ) {
                e.printStackTrace()
            }
        }
        return contentList
    }

    override fun onPostExecute(result: List<CharacterRequest>?) {
        super.onPostExecute(result)
        if( result != null ) onDataFetched( result )
        else onError("Unable to fetch data" )
    }

    companion object {
        fun getNewRequestService( requestService: RequestService ): RequestService {
            return RequestServiceTask( requestService )
        }
    }
}