package com.abz.abzagencytest.domain.use_cases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkStatusUseCase @Inject constructor(
    private val context: Context
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Checks if there's an active network with internet capability
    fun isConnected(): Boolean {
        return connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } ?: false
    }

    // Observes network connectivity changes and emits current connection status as a flow
    fun observeNetworkStatus(): Flow<Boolean> = callbackFlow {
        // Sends the initial connection status
        trySend(isConnected())

        // BroadcastReceiver listens for network changes and updates connection status
        val networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val connected =
                    networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                trySend(connected)
            }
        }

        // Registers the receiver to listen for network connectivity changes
        context.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        // Unregisters the receiver when the flow is closed
        awaitClose {
            context.unregisterReceiver(networkReceiver)
        }
    }
}
