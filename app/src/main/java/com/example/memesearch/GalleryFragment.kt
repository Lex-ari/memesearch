package com.example.memesearch

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder


class GalleryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var userId : String
    private var memeList : List<MemeObject?>? = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_gallery, container, false)

        userId = Backendless.UserService.CurrentUser().userId
        Log.d(TAG, "onCreateView : userId = ${userId.toString()}")
        getAllMemes()

        return rootView
    }

    private fun getAllMemes() : List<MemeObject?>? {
        val whereClause = "ownerID = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        Backendless.Data.of(MemeObject::class.java).find(queryBuilder, object : AsyncCallback<List<MemeObject?>?>{
            override fun handleResponse(foundMemes: List<MemeObject?>?){
                memeList = foundMemes
                Log.d(TAG, "handleResponse: ${foundMemes.toString()}")
            }
            override fun handleFault(fault: BackendlessFault?){
                Log.d(TAG, "handleFault: "  + fault?.message)
            }
        })
        return memeList
    }

    companion object {
        val TAG = "GalleryFragment"
        @JvmStatic
        fun newInstance() =
            GalleryFragment()
    }
}