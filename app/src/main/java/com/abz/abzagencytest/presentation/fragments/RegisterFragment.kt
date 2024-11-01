package com.abz.abzagencytest.presentation.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.abz.abzagencytest.R
import com.abz.abzagencytest.databinding.FragmentRegisterBinding
import com.abz.abzagencytest.presentation.dialogs.FailRegisterDialog
import com.abz.abzagencytest.presentation.dialogs.SuccessRegisterDialog
import com.abz.abzagencytest.presentation.fragments.UsersListFragment.Companion.MAIN_TAG
import com.abz.abzagencytest.presentation.vm.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var photoPart: MultipartBody.Part? = null
    private val maxPhotoSize = 5 * 1024 * 1024 // Limit photo size to 5 MB

    private val registerViewModel: RegisterViewModel by viewModels()

    // Launches activity for selecting or capturing an image
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    processImage(uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe and handle token generation result
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.generateTokenResult.collect { result ->
                result.onSuccess { response ->
                    Log.i(MAIN_TAG, "$response")
                    if (response.isSuccessful) {
                        makeRequestRegister(response.body()?.token ?: "")
                    } else {
                        showErrorDialog()
                    }
                }.onFailure {
                    showErrorDialog()
                }
            }
        }

        // Observe and handle registration result
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.registerResult.collect { result ->
                result.onSuccess { response ->
                    if (response.isSuccessful && response.body()?.success == true) {
                        SuccessRegisterDialog.getInstance()
                            .show(childFragmentManager, "SuccessRegisterDialog")
                    } else {
                        showErrorDialog()
                    }
                }.onFailure {
                    showErrorDialog()
                }
                binding.btnSignUp.isEnabled = true
            }
        }

        // Set up image picker with options for gallery or camera
        binding.tvPhoto.setEndIconOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooserIntent = Intent.createChooser(galleryIntent, "Upload Photo").apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            }
            imagePickerLauncher.launch(chooserIntent)
        }

        // Sign up button click listener with validation checks
        binding.btnSignUp.setOnClickListener {
            if (validateInputs()) {
                binding.btnSignUp.isEnabled = false
                registerViewModel.generateToken()
            }
        }
    }

    // Triggers user registration after token generation
    private fun makeRequestRegister(token: String) {
        val selectedPositionID = binding.rgPosition
            .findViewById<RadioButton>(binding.rgPosition.checkedRadioButtonId)
            ?.tag
            ?.toString()
            ?.toInt() ?: -1

        registerViewModel.registerUser(
            token = token,
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString(),
            positionID = selectedPositionID,
            photo = photoPart!!,
        )
    }

    // Processes image from URI, checks size, and converts to MultipartBody.Part
    private fun processImage(uri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val imageBytes = inputStream?.readBytes()

        if (imageBytes != null && imageBytes.size > maxPhotoSize) {
            Snackbar.make(binding.root, "Image size exceeds 5 MB limit", Snackbar.LENGTH_LONG).show()
            return
        }

        val tempFile = createTempFileFromUri(requireContext(), uri)
        if (tempFile == null) {
            Log.i(MAIN_TAG, "Failed to create a temp file from the selected image.")
            return
        }

        val photoRequestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        photoPart = MultipartBody.Part.createFormData("photo", tempFile.name, photoRequestBody)

        binding.tvPhoto.editText!!.setText(uri.toString())
    }

    // Creates a temporary file from the selected image URI
    private fun createTempFileFromUri(context: Context, uri: Uri): File? {
        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return tempFile
    }

    // Validates input fields before proceeding
    private fun validateInputs(): Boolean {
        var isValid = true

        if (binding.etName.text.isNullOrBlank()) {
            binding.tilName.error = "Required field"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
            binding.tilEmail.error = "Invalid email format"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (binding.etPhone.text.isNullOrBlank()) {
            binding.tilPhone.error = "Required field"
            isValid = false
        } else {
            binding.tilPhone.error = null
        }

        if (photoPart == null) {
            binding.tvPhoto.error = "Photo is required"
            binding.tvPhoto.setEndIconDrawable(R.drawable.upload)
            binding.tvPhoto.errorIconDrawable = null
            isValid = false
        } else {
            binding.tvPhoto.error = null
        }

        return isValid
    }

    // Shows error dialog on registration failure
    private fun showErrorDialog() {
        FailRegisterDialog.getInstance().show(childFragmentManager, "FailRegisterDialog")
        binding.btnSignUp.isEnabled = true
    }
}
