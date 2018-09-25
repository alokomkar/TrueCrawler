package com.alokomkar.truecrawller.ui

import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.alokomkar.truecrawller.R
import com.alokomkar.truecrawller.data.CharacterRequest

class RequestsRvAdapter( private val requests : ArrayList<CharacterRequest> ) : RecyclerView.Adapter<RequestsRvAdapter.ViewHolder>() {

    private var isProgressShown = true

    override fun onCreateViewHolder( parent : ViewGroup, viewType : Int): ViewHolder
        = ViewHolder( LayoutInflater.from(parent.context).inflate( R.layout.item_request, null, false) )


    override fun getItemCount(): Int
            = requests.size

    override fun onBindViewHolder( holder : ViewHolder, position : Int )
            = holder.bindData( requests[position] )

    fun toggleProgress( progressShown : Boolean) {
        isProgressShown = progressShown
        notifyDataSetChanged()
    }

    inner class ViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView ) {

        private val tvTitle = itemView.findViewById<TextView>( R.id.tvTitle )
        private val tvDetails = itemView.findViewById<TextView>( R.id.tvDetails )
        private val pbContent = itemView.findViewById<ContentLoadingProgressBar>( R.id.pbContent )

        fun bindData(characterRequest: CharacterRequest) {

            tvTitle.text = characterRequest.requestType.toString()
            tvDetails.text = characterRequest.details

            if( isProgressShown ) {
                pbContent.visibility = View.VISIBLE
                tvDetails.visibility = View.INVISIBLE
            }
            else {
                pbContent.visibility = View.INVISIBLE
                tvDetails.visibility = View.VISIBLE
            }


        }

    }
}