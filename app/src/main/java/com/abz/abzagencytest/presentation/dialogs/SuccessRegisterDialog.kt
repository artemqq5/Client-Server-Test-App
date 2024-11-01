package com.abz.abzagencytest.presentation.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.abz.abzagencytest.databinding.FragmentSuccessRegisterDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessRegisterDialog : DialogFragment() {

    private lateinit var binding: FragmentSuccessRegisterDialogBinding

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
        binding = FragmentSuccessRegisterDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.complete.setOnClickListener {
            dialog?.dismiss()
        }

        binding.close.setOnClickListener {
            dialog?.cancel()
        }
    }

    companion object {
        private var instance: SuccessRegisterDialog? = null

        fun getInstance(): SuccessRegisterDialog {
            if (instance == null) {
                instance = SuccessRegisterDialog()
            }
            return instance!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        instance = null
    }
}