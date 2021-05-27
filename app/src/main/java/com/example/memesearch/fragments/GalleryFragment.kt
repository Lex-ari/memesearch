package com.example.memesearch.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
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
    private var searchingForTagsTitles : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false)

        userId = Backendless.UserService.CurrentUser().userId
        Log.d(TAG, "onCreateView : userId = $userId")


        loadAllMemes()
        rootView.fragmentGallery_multiAutoCompleteText_searchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    searchingForTagsTitles = rootView.fragmentGallery_multiAutoCompleteText_searchBar.text.toString()
                    loadAllMemes()

                    return true
                }
                return false
            }
        })


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
                memeList = foundMemes // At this point, Backendless gave all the meme objects from user ID
                var memesToShow = memeList.toMutableList()
                Log.d(TAG, "handleResponse: $foundMemes")
                Log.d(TAG, "getAllMemes: memeList size : ${memeList.size}")

                titleTagList = mutableListOf() // Clears the previous list of tags available to search for
                for (eachMeme in memeList){
                    var memeTags : List<String> = eachMeme.tags?.split(",")!!.map {it.trim()} // this is a list of tags now
                    for (tag in memeTags){
                        titleTagList.add(tag)
                    }
                    if (!eachMeme.title.isNullOrEmpty()){
                        titleTagList.add(eachMeme.title.toString())
                    }
                    // This adds the tags and title to the searchable list
                }
                for (eachTag in titleTagList){
                    Log.d(TAG, "generated tag from titleTagList: '$eachTag'")
                }
                updateSearchableList() // Updates the tags available to search for. Auto drop down menu

                if (!searchingForTagsTitles.isNullOrEmpty()){ // So the user is searching for something
                    memesToShow = mutableListOf()
                    for (meme in foundMemes){
                        var memeAddedToMemesToShow = false
                        var searchingTagList : List<String> = searchingForTagsTitles!!.split(",").map{it.trim()}
                        Log.d(TAG, "searching Tag List: ${searchingTagList}")
                        for (tag in searchingTagList){
                            if (meme.tags.toString().contains(tag) && !memeAddedToMemesToShow) {
                                memesToShow.add(meme)
                                memeAddedToMemesToShow = true
                            }
                        }
                    }
                }

                rootView.fragmentGallery_recyclerView_gallery.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(activity, 3)
                    adapter = MemeAdapter(memesToShow)
                }

                // Setting up search options for later


                //Log.d(TAG, "generated TitleTagList: $titleTagList, titleTagList size: ${titleTagList.size}")
            }
            override fun handleFault(fault: BackendlessFault?){
                Log.d(TAG, "handleFault: "  + fault?.message)
            }
        })
        return memeList
    }

    /*private fun loadSearchingMemes(searchBar : String) : List<MemeObject>  {
        var searchingTags : List<String> = searchBar.split(", ").map {it.trim()}
        var whereClause = "ownerID = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        Backendless.Data.of(MemeObject::class.java).find(queryBuilder, object : AsyncCallback<List<MemeObject>>{
            override fun handleResponse(foundMemes: List<MemeObject>){
                //memeList = foundMemes
                Log.d(TAG, "handleResponse: $foundMemes")
                Log.d(TAG, "loadSearchingMemes: memeList size : ${memeList.size}")

                //sort the data




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
    }*/

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