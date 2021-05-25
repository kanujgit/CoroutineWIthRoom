package com.kdroid.coroutinewithroom.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kdroid.coroutinewithroom.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupBtn.setOnClickListener { onSignup(it) }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            Toast.makeText(activity, "SignUp complete", Toast.LENGTH_LONG).show()
            val action = SignUpFragmentDirections.actionGoToMain()
            Navigation.findNavController(binding.signupUsername).navigate(action)

        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()

        })
    }

    private fun onSignup(v: View) {
        val userName: String = binding.signupUsername.text.toString()
        val password: String = binding.signupPassword.text.toString()
        val info: String = binding.otherInfo.text.toString()
        if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModel.signup(userName, password, info)
        } else {
            Toast.makeText(activity, "Please fill the detail", Toast.LENGTH_LONG).show()
        }
    }

    private fun onGotoLogin(v: View) {
        val action = SignUpFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}