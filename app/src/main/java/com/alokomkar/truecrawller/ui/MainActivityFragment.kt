package com.alokomkar.truecrawller.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alokomkar.truecrawller.Injection
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
    private lateinit var viewModel: RequestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it, Injection.provideViewModelFactory(activity!!))
                    .get(RequestViewModel::class.java)
            fetchLiveData()
            fetchError()
        }

        fab.setOnClickListener {
            refreshLayout.isRefreshing = requestList.size == 0
            requestsRvAdapter.toggleProgress( !refreshLayout.isRefreshing )
            viewModel.execute("https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/" )
        }

        rvContent.apply {
            layoutManager = LinearLayoutManager( context )
            requestsRvAdapter = RequestsRvAdapter( requestList )
            adapter = requestsRvAdapter
            setHasFixedSize( true )
        }

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            fab.callOnClick()
        }

    }

    private fun fetchError() {
        viewModel.fetchError().observe( this , Observer<String> { t ->
            if( t != null && rvContent != null )
                Snackbar.make(rvContent, t, Snackbar.LENGTH_LONG).setAction(R.string.retry, {
                    fab.callOnClick()
                })
        })
    }

    private fun fetchLiveData() {
        viewModel.fetchLiveData().observe( this, Observer<List<CharacterRequest>> { t ->

            requestList.clear()
            requestList.addAll(t!!)
            //requestList.add( CharacterRequest(RequestType.TenthCharacter, "", "Detailed Description"))
            //requestList.add( CharacterRequest(RequestType.EveryTenthCharacter, "", "10th : Hi\n20th : Engineer\n30th : Day"))
            //requestList.add( CharacterRequest(RequestType.WordCounter, "", "Hi : 23\nHello : 25\nHow : 54\nYes : 34"))
            requestsRvAdapter.notifyDataSetChanged()

            Handler().postDelayed({
                requestsRvAdapter.toggleProgress( false )
                refreshLayout.isRefreshing = false
            }, 2000)
        })
    }

}
