package com.abz.abzagencytest.presentation.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.abz.abzagencytest.databinding.FragmentLostInternetConnectionDialogBinding
import com.abz.abzagencytest.domain.use_cases.NetworkStatusUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LostInternetConnectionDialog : DialogFragment() {

    private lateinit var binding: FragmentLostInternetConnectionDialogBinding

    @Inject
    lateinit var networkStatusUseCase: NetworkStatusUseCase

    private val timer = object : CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.tryAgain.apply {
                isEnabled = false
                text = "Try again in ${millisUntilFinished / 1000}s"
            }
        }

        override fun onFinish() {
            binding.tryAgain.apply {
                isEnabled = true
                text = "Try Again"
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLostInternetConnectionDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startRetryCountdown()

        binding.tryAgain.setOnClickListener {
            if (networkStatusUseCase.isConnected()) {
                dialog?.dismiss()
            } else {
                startRetryCountdown()
            }
        }
    }

    private fun startRetryCountdown() {
        timer.start()
    }

    companion object {
        private var instance: LostInternetConnectionDialog? = null

        fun getInstance(): LostInternetConnectionDialog {
            if (instance == null) {
                instance = LostInternetConnectionDialog()
            }
            return instance!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        instance = null
        timer.cancel() // Stop timer when the view is destroyed
    }
}
