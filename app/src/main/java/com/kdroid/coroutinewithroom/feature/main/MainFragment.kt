package com.kdroid.coroutinewithroom.feature.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kdroid.coroutinewithroom.data.LoginState
import com.kdroid.coroutinewithroom.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signoutBtn.setOnClickListener { onSignOut() }
        binding.deleteUserBtn.setOnClickListener { onDelete() }
        binding.usernameTV.text = LoginState.user?.userName

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signOut.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "SignOut", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })

        viewModel.userDeleted.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, "User is deleted", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
    }

    private fun onSignOut() {
        viewModel.onSignOut()
    }

    private fun goToSignUpScreen() {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(binding.usernameTV).navigate(action)
    }

    private fun onDelete() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("Are you sure want to delete the user")
                .setPositiveButton("Yes") { p0, p1 -> viewModel.onDeleteUser() }
                .setNegativeButton("Cancel", null)
                .create()
                .show()

        }
    }
}