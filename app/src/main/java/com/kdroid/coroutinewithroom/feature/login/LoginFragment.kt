package com.kdroid.coroutinewithroom.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kdroid.coroutinewithroom.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel


    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener { onLogin() }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            val action = LoginFragmentDirections.actionGoToMain()
            Navigation.findNavController(binding.loginUsername).navigate(action)
            Toast.makeText(activity, "Login is done", Toast.LENGTH_SHORT).show()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun onLogin() {
        val userName: String = binding.loginUsername.text.toString()
        val password: String = binding.loginPassword.text.toString()
        if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModel.login(userName, password)
        } else {
            Toast.makeText(activity, "Please fill the details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onGotoSignup(v: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}