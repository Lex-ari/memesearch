package com.example.memesearch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.memesearch.R
import com.example.memesearch.helpers.MemeAdapter
import com.example.memesearch.models.MemeObject
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*


class GalleryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var userId : String
    private var memeList : List<MemeObject> = listOf()
    private lateinit var rootView : View
    private var titleTagList : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false)

        userId = Backendless.UserService.CurrentUser().userId
        Log.d(TAG, "onCreateView : userId = $userId")


        loadAllMemes()





        return rootView
    }

    private fun updateSearchableList(){
        var adapter = ArrayAdapter<String>(rootView.context, android.R.layout.simple_dropdown_item_1line, titleTagList)
        rootView.fragmentGallery_multiAutoCompleteText_searchBar.setAdapter(adapter)
        rootView.fragmentGallery_multiAutoCompleteText_searchBar.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }

    /*(private fun loadAllMemes(memeList : List<MemeObject>){
        var loadedMemeList = getAllMemes()
        Log.d("loadAllMemes", "memeList size : ${loadedMemeList.size}")
        rootView.fragmentGallery_recyclerView_gallery.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
            adapter = MemeAdapter(loadedMemeList)
        }
    }*/

    private fun loadAllMemes() : List<MemeObject> {
        val whereClause = "ownerID = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        Backendless.Data.of(MemeObject::class.java).find(queryBuilder, object : AsyncCallback<List<MemeObject>>{
            override fun handleResponse(foundMemes: List<MemeObject>){
                memeList = foundMemes
                Log.d(TAG, "handleResponse: $foundMemes")
                Log.d(TAG, "getAllMemes: memeList size : ${memeList.size}")
                rootView.fragmentGallery_recyclerView_gallery.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(activity, 3)
                    adapter = MemeAdapter(memeList)
                }

                // Setting up search options for later
                titleTagList = mutableListOf()
                for (eachMeme in memeList){
                    var memeTags : List<String> = eachMeme.tags?.split(",")!!.map {it.trim()} // this is a list of tags now
                    for (tag in memeTags){
                        titleTagList.add(tag)
                    }
                    if (!eachMeme.title.isNullOrEmpty()){
                        titleTagList.add(eachMeme.title.toString())
                    }
                }
                for (eachTag in titleTagList){
                    Log.d(TAG, "generated tag from titleTagList: '$eachTag'")
                }
                updateSearchableList()
                //Log.d(TAG, "generated TitleTagList: $titleTagList, titleTagList size: ${titleTagList.size}")
            }
            override fun handleFault(fault: BackendlessFault?){
                Log.d(TAG, "handleFault: "  + fault?.message)
            }
        })
        return memeList
    }

    private fun loadSearchingMemes(searchBar : String) : List<MemeObject>  {
        val whereClause = "ownerID = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        Backendless.Data.of(MemeObject::class.java).find(queryBuilder, object : AsyncCallback<List<MemeObject>>{
            override fun handleResponse(foundMemes: List<MemeObject>){
                //memeList = foundMemes
                Log.d(TAG, "handleResponse: $foundMemes")
                Log.d(TAG, "getAllMemes: memeList size : ${memeList.size}")
                rootView.fragmentGallery_recyclerView_gallery.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(activity, 3)
                    adapter = MemeAdapter(memeList)
                }
            }
            override fun handleFault(fault: BackendlessFault?){
                Log.d(TAG, "handleFault: "  + fault?.message)
            }
        })
        return memeList
    }

    /*fun goToDetailedMeme(meme : MemeObject){
        var bundle = Bundle()
        bundle.putParcelable("meme", meme)

        view?.findNavController()?.navigate(R.id.action_gallaryFragment_to_detailedMemeFragment, bundle)
    }*/

    companion object {
        val TAG = "GalleryFragment"
        @JvmStatic
        fun newInstance() =
            GalleryFragment()
    }
}