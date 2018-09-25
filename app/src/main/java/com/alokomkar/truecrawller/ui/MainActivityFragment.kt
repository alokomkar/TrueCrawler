package com.alokomkar.truecrawller.ui

import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alokomkar.truecrawller.R
import com.alokomkar.truecrawller.data.CharacterRequest
import com.alokomkar.truecrawller.data.RequestType
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private val requestList = ArrayList<CharacterRequest>()
    private lateinit var requestsRvAdapter: RequestsRvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener { populateData() }
        rvContent.apply {
            layoutManager = LinearLayoutManager( context )
            requestsRvAdapter = RequestsRvAdapter( requestList )
            adapter = requestsRvAdapter
        }
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            fab.callOnClick()
        }
    }

    private fun populateData() {

        requestsRvAdapter.toggleProgress( true )
        requestList.clear()
        requestList.add( CharacterRequest(RequestType.TenthCharacter, "", "Detailed Description"))
        requestList.add( CharacterRequest(RequestType.EveryTenthCharacter, "", "10th : Hi\n20th : Engineer\n30th : Day"))
        requestList.add( CharacterRequest(RequestType.WordCounter, "", "Hi : 23\nHello : 25\nHow : 54\nYes : 34"))
        requestsRvAdapter.notifyDataSetChanged()

        Handler().postDelayed({
            requestsRvAdapter.toggleProgress( false )
            refreshLayout.isRefreshing = false
        }, 5000)

    }
}
