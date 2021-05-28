package com.example.memesearch.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.memesearch.R
import com.example.memesearch.models.MemeObject
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_meme.*
import kotlinx.android.synthetic.main.fragment_detailed_meme.view.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*


@Suppress("UNNECESSARY_SAFE_CALL")
class DetailedMemeFragment : Fragment() {

    val TAG = "DetailedMemeFragment"

    private lateinit var detailedMeme : MemeObject
    private lateinit var rootView : View
    private var tagList : MutableList<String>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detailed_meme, container, false)
        detailedMeme = arguments?.getParcelable("meme")!!
        Log.d(TAG, "onCreateView: $detailedMeme")

        Picasso.get().load(detailedMeme.source).into(rootView.fragmentDetailedMeme_imageView_meme)
        rootView.fragmentDetailedMeme_textView_title.text = detailedMeme.title
        rootView.fragmentDetailedMeme_editText_title.setText(detailedMeme.title)
        rootView.fragmentDetailedMeme_textView_source.text = detailedMeme.source
        rootView.fragmentDetailedMeme_textView_uploaded.text = "uploaded: ${detailedMeme.created.toString()}"
        //rootView.fragmentDetailedMeme_editText_source.setText(detailedMeme.source)


        tagList = (detailedMeme.tags?.split(",")?.map {it.trim()})?.toMutableList() // turning the String from Backendless into a list of strings for tags.
        setTags()

        rootView.fragmentDetailedMeme_textView_editMode.setOnClickListener { // make changes enabled be careful here
            rootView.fragmentDetailedMeme_editText_title.visibility = View.VISIBLE
            rootView.fragmentDetailedMeme_textView_title.visibility = View.GONE
            rootView.fragmentDetailedMeme_textView_editMode.text = "save changes"
            rootView.fragmentDetailedMeme_textView_deleteMeme.visibility = View.VISIBLE
            rootView.fragmentDetailedMeme_editText_source.visibility = View.VISIBLE
            rootView.fragmentDetailedMeme_textView_source.visibility = View.INVISIBLE

            setDeletableTags() // Clears the old tags in the chip group and replaces them with deletable ones

            rootView.fragmentDetailedMeme_editText_newTag.visibility = View.VISIBLE

            rootView.fragmentDetailedMeme_editText_newTag.setOnKeyListener(object : View.OnKeyListener { // enters in a new tag when the user hits enter
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER && !rootView.fragmentDetailedMeme_editText_newTag.text.isNullOrEmpty()){
                        if (tagList.isNullOrEmpty())
                            tagList = mutableListOf()
                        tagList?.add(rootView.fragmentDetailedMeme_editText_newTag.text.toString())
                        rootView.fragmentDetailedMeme_editText_newTag.text = null
                        setDeletableTags() // Basically updates the tags so the new one that the user inputted shows
                        return true
                    }
                    return false
                }
            })

            rootView.fragmentDetailedMeme_editText_source.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        detailedMeme.source = fragmentDetailedMeme_editText_source.text.toString()
                        Picasso.get().load(detailedMeme.source).into(rootView.fragmentDetailedMeme_imageView_meme)
                        return true
                    }
                    return false
                }
            })

            rootView.fragmentDetailedMeme_textView_deleteMeme.setOnClickListener { // starts deletion process when the user presses deleteMeme
                val builder = AlertDialog.Builder(activity)
                builder.setCancelable(true)
                builder.setMessage("Are you sure? You will permanently loose your meme")
                    .setPositiveButton("DELETE MEME", DialogInterface.OnClickListener { dialog, which ->
                        // Delete the meme
                        deleteMeme()
                    }).setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
                        //DO NOT DELETE THIS MEME
                        dialog.cancel()
                    })
                builder.create().show()

            }

            rootView.fragmentDetailedMeme_textView_editMode.setOnClickListener { // saving changes
                detailedMeme.title = rootView.fragmentDetailedMeme_editText_title.text.toString()
                updateMeme()
                //detailedMeme.source = rootView.fragmentDetailedMeme_textView_source.text.toString()
                setTags() // Updates tags so they look nice
            }
        }






        return rootView
    }

    private fun updateMeme(){
        // Putting any changes to the mutable list of tags into the string tags
        detailedMeme.tags = tagList.toString().substring(1, tagList.toString().length - 1)


        Backendless.Data.of(MemeObject::class.java).save(detailedMeme, object : AsyncCallback<MemeObject?> {
            override fun handleResponse(response: MemeObject?){
                Toast.makeText(activity, "Successfully updated meme",Toast.LENGTH_SHORT).show()

                // Converting everything back to what it was previously
                rootView.fragmentDetailedMeme_editText_title.visibility = View.GONE
                rootView.fragmentDetailedMeme_textView_title.visibility = View.VISIBLE
                rootView.fragmentDetailedMeme_textView_editMode.text = "enable editmode"
                rootView.fragmentDetailedMeme_textView_title.text = rootView.fragmentDetailedMeme_editText_title.text.toString()
                rootView.fragmentDetailedMeme_textView_deleteMeme.visibility = View.GONE
                rootView.fragmentDetailedMeme_textView_editMode.isClickable = true
                rootView.fragmentDetailedMeme_editText_newTag.visibility = View.INVISIBLE
                rootView.fragmentDetailedMeme_editText_source.visibility = View.GONE
                rootView.fragmentDetailedMeme_textView_source.visibility = View.VISIBLE
                rootView.fragmentDetailedMeme_textView_source.text = detailedMeme.source.toString()
                rootView.fragmentDetailedMeme_textView_uploaded.text = "uploaded: ${detailedMeme.created.toString()}"

            }
            override fun handleFault(fault: BackendlessFault){
                Toast.makeText(activity, "Meme was not successfully updated.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "handleFault: ${fault.message}")
            }
        })
    }

    private fun deleteMeme(){
        Backendless.Data.of(MemeObject::class.java).remove(detailedMeme, object : AsyncCallback<Long?>{
            override fun handleResponse(response: Long?){
                //rip meme
                view?.findNavController()?.navigate(R.id.action_detailedMemeFragment_to_gallaryFragment)
                Toast.makeText(activity, "Meme successfully deleted.",Toast.LENGTH_SHORT).show()

            }
            override fun handleFault(fault: BackendlessFault){
                Toast.makeText(activity, "Unsuccessful Deletion", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "handleFault: ${fault.message}")
            }
        })
    }


    // https://mobikul.com/android-chips-dynamicaly-add-remove-tags-chips-view/
    private fun setTags() { // clears the chipgroup and puts nice looking tags
        var chipGroup = rootView.fragmentDetailedMeme_chipGroup_tags
        chipGroup.removeAllViews()
        if (tagList != null) {
            for (tag in tagList!!){
                var chip = Chip(activity)
                chip.text = tag
                chipGroup.addView(chip)
            }
        }
    }

    private fun setDeletableTags() { // clears the chip group and replaces tags that are deletable
        var chipGroup = rootView.fragmentDetailedMeme_chipGroup_tags
        chipGroup.removeAllViews()
        if (tagList != null) {
            for (tag in tagList!!) {
                var chip = Chip(activity)
                chip.text = tag
                chip.isCloseIconVisible = true
                chipGroup.addView(chip)
                chip.setOnCloseIconClickListener {
                    tagList!!.remove(tag)
                    setDeletableTags()
                }
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            DetailedMemeFragment()
    }
}