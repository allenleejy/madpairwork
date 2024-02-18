package com.example.csproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemberFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private val membersList = ArrayList<Member>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member, container, false)

        val memberDatabase = MemberDatabase()

        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)

        val signupButton = view.findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            if (usernameEditText.text.toString() == "" || passwordEditText.text.toString() == "") {
                if (usernameEditText.text.toString() == "") {
                    usernameEditText.error = "Invalid username"
                }
                if (passwordEditText.text.toString() == "") {
                    passwordEditText.error = "Invalid password"
                }
            }
            else {
                val member = Member(usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                memberDatabase.addMember(member)
                signUp()
            }
        }

        val loginButton = view.findViewById<Button>(R.id.loginbutton)
        loginButton.setOnClickListener{
            val member = Member(usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
            val checkResult = memberDatabase.checkIfMember(member)

            if (checkResult == "successful"){
                Toast.makeText(requireActivity(),"Login Successful!",Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WelcomeFragment()).commit()
            }
            else if (checkResult == "wrong passwd"){
                Toast.makeText(requireActivity(),"Wrong password, try again",Toast.LENGTH_LONG).show()
                usernameEditText.text?.clear()
                passwordEditText.text?.clear()
            }
            else{
                Toast.makeText(requireActivity(),"You are not a member. Pls sign up!",Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
    private fun signUp() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        val member = Member(username, password)
        membersList.add(member)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WelcomeFragment()).commit()

    }
}