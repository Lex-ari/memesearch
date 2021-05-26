package com.example.memesearch.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.memesearch.R
import com.example.memesearch.models.MemeObject
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detailed_meme.view.*


@Suppress("UNNECESSARY_SAFE_CALL")
class DetailedMemeFragment : Fragment() {

    val TAG = "DetailedMemeFragment"

    private lateinit var detailedMeme : MemeObject
    private lateinit var rootView : View

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

        rootView.fragmentDetailedMeme_textView_editMode.setOnClickListener { // make changes enabled
            rootView.fragmentDetailedMeme_editText_title.visibility = View.VISIBLE
            rootView.fragmentDetailedMeme_textView_title.visibility = View.GONE
            rootView.fragmentDetailedMeme_textView_editMode.text = "save changes"
            rootView.fragmentDetailedMeme_textView_deleteMeme.visibility = View.VISIBLE

            rootView.fragmentDetailedMeme_textView_deleteMeme.setOnClickListener {
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
                updateMeme()
                detailedMeme.title = rootView.fragmentDetailedMeme_editText_title.text.toString()
                detailedMeme.source = rootView.fragmentDetailedMeme_textView_source.text.toString()
            }
        }
        return rootView
    }

    private fun updateMeme(){
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