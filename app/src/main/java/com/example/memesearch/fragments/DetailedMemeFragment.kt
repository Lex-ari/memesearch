package com.example.memesearch.fragments

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.memesearch.R
import com.example.memesearch.models.MemeObject
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_meme.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class DetailedMemeFragment : Fragment() {

    val TAG = "DetailedMemeFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detailed_meme, container, false)
        val detailedMeme : MemeObject? = arguments?.getParcelable("meme")
        Log.d(TAG, "onCreateView: $detailedMeme")

        Picasso.get().load(detailedMeme?.source).into(rootView.fragmentDetailedMeme_imageView_meme)


        return rootView
    }

    // https://mobikul.com/android-chips-dynamicaly-add-remove-tags-chips-view/
    private fun setTag(tagList: List<String>, rootView: View) {
        var chipGroup = rootView.fragmentDetailedMeme_chipGroup_tags
        for (tag in tagList){
            var chip = Chip(activity)
            chip.text = tag
            chipGroup.addView(chip)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            DetailedMemeFragment()
    }
}