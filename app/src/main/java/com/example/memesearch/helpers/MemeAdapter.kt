package com.example.memesearch.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.memesearch.models.MemeObject
import com.example.memesearch.R
import com.squareup.picasso.Picasso

class MemeAdapter (private val memeList: List<MemeObject>) : RecyclerView.Adapter<MemeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meme_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${memeList}")
        //Log.d("Response", "memeList[position]: ${memeList[position]}")
        Log.d("Response", "memeList: ${memeList[0]}")
        return holder.bind(memeList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.memeItem_imageView_memeImage)

        fun bind(meme: MemeObject){
            Picasso.get().load(meme.source).into(imageView)
            Log.d("ViewHolder", "meme image source : ${meme.source}")
        }
    }

    override fun getItemCount(): Int {
        return memeList.size
    }
}