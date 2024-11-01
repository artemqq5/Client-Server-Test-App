package com.abz.abzagencytest.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abz.abzagencytest.databinding.FragmentUsersListBinding
import com.abz.abzagencytest.presentation.adapters.UsersAdapter
import com.abz.abzagencytest.presentation.vm.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : Fragment() {

    companion object {
        const val MAIN_TAG = "sfroghw98op"
    }

    var currentPage = 1

    private lateinit var binding: FragmentUsersListBinding
    private val usersViewModel: UsersViewModel by viewModels()

    // Lazy initialization of the adapter to ensure it's created only when needed
    private val userAdapter by lazy {
        UsersAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sets up RecyclerView with adapter and LinearLayoutManager for vertical scrolling
        binding.recyclerPosition.adapter = userAdapter
        binding.recyclerPosition.layoutManager = LinearLayoutManager(requireContext())

        // Adds a scroll listener to trigger pagination when the user scrolls to the end
        binding.recyclerPosition.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == userAdapter.itemCount - 1) {
                    currentPage++ // Increment page to load the next set of users
                    usersViewModel.fetchUsers(currentPage)
                }
            }
        })

        // Observes the ViewModel's flow of user data
        lifecycleScope.launch {
            usersViewModel.users.collect { result ->
                result.onSuccess { response ->
                    Log.i(MAIN_TAG, "$response")
                    if (response == null) return@collect

                    // Check for successful response and update adapter with new users
                    if (response.isSuccessful) {
                        Log.i(MAIN_TAG, "load users successful: ${response.body()}")
                        userAdapter.addUsers(response.body()?.users)
                    } else {
                        Log.i(MAIN_TAG, "load users failed: ${response.errorBody()?.string()}")
                    }

                }.onFailure { error ->
                    Log.i(MAIN_TAG, "load users ERROR $error") // Log any errors during data fetching
                }
            }
        }
    }
}
