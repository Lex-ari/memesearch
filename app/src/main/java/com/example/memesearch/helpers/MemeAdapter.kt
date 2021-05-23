package com.example.memesearch.helpers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Gallery
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.memesearch.models.MemeObject
import com.example.memesearch.R
import com.example.memesearch.fragments.DetailedMemeFragment
import com.example.memesearch.fragments.GalleryFragment
import com.squareup.picasso.Picasso

class MemeAdapter (private val memeList: List<MemeObject>) : RecyclerView.Adapter<MemeAdapter.ViewHolder>(){
    private lateinit var rootView : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        rootView = LayoutInflater.from(parent.context).inflate(R.layout.meme_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${memeList.size}")
        //Log.d("Response", "memeList[position]: ${memeList[position]}")
        //Log.d("Response", "memeList: ${memeList[0]}")
        return holder.bind(memeList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.memeItem_imageView_memeImage)

        fun bind(meme: MemeObject){
            Picasso.get().load(meme.source).into(imageView)
            Log.d("ViewHolder", "meme image source : ${meme.source}")
            imageView.setOnClickListener {
                var bundle = Bundle()
                bundle.putParcelable("meme", meme)
                imageView?.findNavController()?.navigate(R.id.action_gallaryFragment_to_detailedMemeFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return memeList.size
    }
}