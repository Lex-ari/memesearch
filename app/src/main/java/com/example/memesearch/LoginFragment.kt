package com.example.memesearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        val TAG = "LoginFragment"

                //if (arguments != null) {
            rootView.fragmentLogin_editText_username.setText(arguments?.getString("username"))
            rootView.fragmentLogin_editText_password.setText(arguments?.getString("password"))
                    //}

        rootView.fragmentLogin_textView_firstTime.setOnClickListener {
            LoginActivity.EXTRA_USERNAME = fragmentLogin_editText_username.text.toString()
            Log.d(TAG, "onCreateView: EXTRA_USERNAME is " + LoginActivity.EXTRA_USERNAME)

            val bundle =  Bundle();
            bundle.putString("username", fragmentLogin_editText_username.text.toString())
            bundle.putString("password", fragmentLogin_editText_password.text.toString())
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registrationFragment, bundle)
        }
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }

}