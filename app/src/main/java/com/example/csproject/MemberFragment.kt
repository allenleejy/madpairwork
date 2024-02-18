package com.example.csproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.csproject.R.id.membername
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

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
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var membername : TextView
    private val membersList = ArrayList<Member>()
    private lateinit var logoutbutton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_member, container, false)

        val memberDatabase = MemberDatabase(requireContext())

        logoutbutton = view.findViewById(R.id.logoutbutton)
        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        membername = view.findViewById(R.id.membername)
        loginButton = view.findViewById(R.id.loginbutton)

        val signupButton = view.findViewById<Button>(R.id.signupButton)

        if (memberDatabase.isLoggedIn()) {
            membername.text = memberDatabase.returnLoggedIn()
            logoutbutton.visibility = View.VISIBLE
            usernameEditText.visibility = View.GONE
            passwordEditText.visibility = View.GONE
            loginButton.visibility = View.GONE
            signupButton.visibility = View.GONE

        }
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
                    passwordEditText.text.toString(), true
                )
                if (memberDatabase.checkIfMember(member) == -1 || memberDatabase.checkIfMember(member) == 1) {
                    Toast.makeText(requireActivity(), "Username Taken", Toast.LENGTH_LONG).show()
                }
                else {
                    memberDatabase.addMember(member)
                    signUp()
                }
            }
        }

        logoutbutton.setOnClickListener {
            memberDatabase.logout(membername.text.toString())
            logoutbutton.visibility = View.GONE
            usernameEditText.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            signupButton.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            membername.text = "Member Login"
        }


        loginButton.setOnClickListener{
            val member = Member(usernameEditText.text.toString(),
                passwordEditText.text.toString(),
            true)
            val checkResult = memberDatabase.checkIfMember(member)

            if (checkResult == 1){
                Toast.makeText(requireActivity(),"Login Successful",Toast.LENGTH_SHORT).show()
                memberDatabase.login(member.username)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WelcomeFragment()).commit()
            }
            else if (checkResult == -1){
                Toast.makeText(requireActivity(),"Incorrect password, try again",Toast.LENGTH_LONG).show()
                usernameEditText.text?.clear()
                passwordEditText.text?.clear()
            }
            else{
                Toast.makeText(requireActivity(),"You are not a member, please sign up",Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
    private fun signUp() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        val member = Member(username, password, true)
        membersList.add(member)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, WelcomeFragment()).commit()

    }
}