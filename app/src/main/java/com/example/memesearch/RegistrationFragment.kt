package com.example.memesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val TAG = "LoginActivity"
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_registration, container, false)
        //Log.d(TAG, "onCreateView: username:" + arguments?.getString("username"))
        val bundle = Bundle()


        rootView.fragmentRegistration_editText_username.setText(arguments?.getString("username"))
        rootView.fragmentRegistration_editText_password.setText(arguments?.getString("password"))

        rootView.fragmentRegistration_textView_register.setOnClickListener{
            if (rootView.fragmentRegistration_editText_username.text.toString() == ""){
                Toast.makeText(activity, "Please enter a username", Toast.LENGTH_SHORT).show()
                fragmentRegistration_editText_username.setHintTextColor(android.graphics.Color.RED)
                fragmentRegistration_editText_username.requestFocus()
            } else if(rootView.fragmentRegistration_editText_name.text.toString() == ""){
                Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show()
                fragmentRegistration_editText_name.setHintTextColor(android.graphics.Color.RED)
                fragmentRegistration_editText_name.requestFocus()
            }

        }
        rootView.fragmentRegistration_textView_cancel.setOnClickListener {
            bundle.putString("username", fragmentRegistration_editText_username.text.toString())
            bundle.putString("password", fragmentRegistration_editText_password.text.toString())
            view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment2, bundle)
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
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            RegistrationFragment()
    }
}