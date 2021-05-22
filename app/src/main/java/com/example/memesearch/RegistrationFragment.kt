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
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
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

        val registrationUsername = rootView.fragmentRegistration_editText_username
        val registrationPassword = rootView.fragmentRegistration_editText_password
        val registrationName = rootView.fragmentRegistration_editText_name
        val registrationConfirmPassword = rootView.fragmentRegistration_editText_confirmPassword
        val registrationEmail = rootView.fragmentRegistration_editText_email


        registrationUsername.setText(arguments?.getString("username"))
        registrationPassword.setText(arguments?.getString("password"))

        rootView.fragmentRegistration_textView_register.setOnClickListener{
            if (registrationUsername.text.toString() == ""){
                Toast.makeText(activity, "Please enter a username", Toast.LENGTH_SHORT).show()
                registrationUsername.setHintTextColor(android.graphics.Color.RED)
                registrationUsername.requestFocus()
            } else if(registrationName.text.toString() == ""){
                Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show()
                registrationName.setHintTextColor(android.graphics.Color.RED)
                registrationName.requestFocus()
            } else if(registrationPassword.text.toString() == ""){
                Toast.makeText(activity, "Please enter a password", Toast.LENGTH_SHORT).show()
                registrationPassword.setHintTextColor(android.graphics.Color.RED)
                registrationName.requestFocus()
            } else if(registrationConfirmPassword.text.toString() != registrationPassword.text.toString()){
                Toast.makeText(activity, "Passwords need to match", Toast.LENGTH_SHORT).show()
                registrationConfirmPassword.setHintTextColor(android.graphics.Color.RED)
                registrationConfirmPassword.requestFocus()
            } else if(registrationEmail.text.toString() == ""){
                Toast.makeText(activity, "Please enter an email", Toast.LENGTH_SHORT).show()
                registrationEmail.setHintTextColor(android.graphics.Color.RED)
                registrationEmail.requestFocus()
            } else {
                //Registering with Backendless
                registerUser(registrationUsername.text.toString(), registrationName.text.toString(), registrationPassword.text.toString(), registrationEmail.text.toString())
            }
        }

        registrationName.setOnClickListener{
            registrationName.setHintTextColor(android.graphics.Color.GRAY)
        }
        registrationUsername.setOnClickListener {
            registrationUsername.setHintTextColor(android.graphics.Color.GRAY)
        }
        registrationPassword.setOnClickListener{
            registrationPassword.setHintTextColor(android.graphics.Color.GRAY)
        }
        registrationConfirmPassword.setOnClickListener {
            registrationConfirmPassword.setHintTextColor(android.graphics.Color.GRAY)
        }
        registrationEmail.setOnClickListener {
            registrationEmail.setHintTextColor(android.graphics.Color.GRAY)
        }

        rootView.fragmentRegistration_textView_cancel.setOnClickListener {
            bundle.putString("username", fragmentRegistration_editText_username.text.toString())
            bundle.putString("password", fragmentRegistration_editText_password.text.toString())
            view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment2, bundle)
        }

        return rootView
    }

    private fun registerUser(username : String, name : String, password : String, email : String){
        val user = BackendlessUser()
        user.setProperty("username", username)
        user.setProperty("name", name)
        user.setProperty("email", email)
        user.password = password

        Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?>{
            override fun handleResponse(registeredUser: BackendlessUser?) {
                val bundle = Bundle()
                bundle.putString("username", fragmentRegistration_editText_username.text.toString())
                bundle.putString("password", fragmentRegistration_editText_password.text.toString())
                view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment2, bundle)
                // Navigates to login activity with username and password ready to go
            }

            override fun handleFault(fault: BackendlessFault) {
                Toast.makeText(activity, fault.message, Toast.LENGTH_SHORT).show()
            }

        })
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