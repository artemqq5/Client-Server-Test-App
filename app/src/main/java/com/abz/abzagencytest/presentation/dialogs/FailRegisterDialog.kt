package com.abz.abzagencytest.presentation.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.abz.abzagencytest.databinding.FragmentFailRegisterDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailRegisterDialog : DialogFragment() {

    private lateinit var binding: FragmentFailRegisterDialogBinding

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
        binding = FragmentFailRegisterDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repeat.setOnClickListener {
            dialog?.dismiss()
        }

        binding.close.setOnClickListener {
            dialog?.cancel()
        }
    }

    companion object {
        private var instance: FailRegisterDialog? = null

        fun getInstance(): FailRegisterDialog {
            if (instance == null) {
                instance = FailRegisterDialog()
            }
            return instance!!
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        instance = null
    }
}