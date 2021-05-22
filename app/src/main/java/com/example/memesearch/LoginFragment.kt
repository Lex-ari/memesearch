package com.example.memesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.memesearch.LoginActivity.Companion.EXTRA_PASSWORD
import com.example.memesearch.LoginActivity.Companion.EXTRA_USERNAME
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

        rootView.fragmentLogin_editText_username.setText(arguments?.getString("username"))
        rootView.fragmentLogin_editText_password.setText(arguments?.getString("password"))

        rootView.fragmentLogin_textView_firstTime.setOnClickListener {
            LoginActivity.EXTRA_USERNAME = rootView.fragmentLogin_editText_username.text.toString()
            Log.d(TAG, "onCreateView: EXTRA_USERNAME is " + LoginActivity.EXTRA_USERNAME)

            val bundle =  Bundle();
            bundle.putString("username", fragmentLogin_editText_username.text.toString())
            bundle.putString("password", fragmentLogin_editText_password.text.toString())
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registrationFragment, bundle)
        }

        rootView.fragmentLogin_textView_login.setOnClickListener {
            val username = rootView.fragmentLogin_editText_username.text.toString()
            val password = rootView.fragmentLogin_editText_password.text.toString()

            Backendless.UserService.login(username, password, object : AsyncCallback<BackendlessUser> {
                override fun handleResponse(response: BackendlessUser){
                    Toast.makeText(activity, "Welcome $username", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "handleResponse: User $username successfully logged in")

                    val userSpaceIntent = Intent(activity, UserSpaceActivity::class.java).apply {
                        putExtra(EXTRA_USERNAME, username)
                        putExtra(EXTRA_PASSWORD, password)
                    }
                    startActivity(userSpaceIntent)
                    activity?.finish()
                }
                override fun handleFault(fault: BackendlessFault?){
                    Toast.makeText(activity, "Something went wrong. Check logs.", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "handleFault: " + fault?.message)
                }
            })
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