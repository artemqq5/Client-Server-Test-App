package com.abz.abzagencytest.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.abz.abzagencytest.R
import com.abz.abzagencytest.databinding.FragmentMainBinding
import com.abz.abzagencytest.presentation.dialogs.LostInternetConnectionDialog
import com.abz.abzagencytest.presentation.vm.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observes network status, shows dialog if disconnected
        viewLifecycleOwner.lifecycleScope.launch {
            networkViewModel.isNetworkConnected.collect { isConnected ->
                if (!isConnected) {
                    LostInternetConnectionDialog.getInstance()
                        .show(childFragmentManager, "LostInternetConnectionDialog")
                }
            }
        }

        reStyleBottomNav("users")

        binding.bottomNavigation.navItemUsers.setOnClickListener {
            // Navigates to UsersListFragment if not currently on it
            val navHostFragment =
                childFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
            val navController = navHostFragment.navController
            if (navController.currentDestination?.id != R.id.usersListFragment) {
                navController.navigate(R.id.action_registerFragment_to_usersListFragment)
                reStyleBottomNav("users")
            }
        }

        binding.bottomNavigation.navItemSignup.setOnClickListener {
            // Navigates to RegisterFragment if not currently on it
            val navHostFragment =
                childFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
            val navController = navHostFragment.navController
            if (navController.currentDestination?.id != R.id.registerFragment) {
                navController.navigate(R.id.action_usersListFragment_to_registerFragment)
                reStyleBottomNav("signup")
            }
        }
    }

    // Updates navigation bar style based on selected item
    private fun reStyleBottomNav(selected: String) {
        val colorActive = ContextCompat.getColor(requireContext(), R.color.active)
        val colorInactive = ContextCompat.getColor(requireContext(), R.color.inactive)

        binding.bottomNavigation.apply {
            iconUsers.setColorFilter(if (selected == "users") colorActive else colorInactive)
            textUsers.setTextColor(if (selected == "users") colorActive else colorInactive)

            iconSignup.setColorFilter(if (selected == "signup") colorActive else colorInactive)
            textSignup.setTextColor(if (selected == "signup") colorActive else colorInactive)
        }
    }
}
