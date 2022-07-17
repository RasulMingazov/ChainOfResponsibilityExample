package com.jeanbernad.chainofresponsibilityexample.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jeanbernad.chainofresponsibilityexample.R
import com.jeanbernad.chainofresponsibilityexample.autoCleared
import com.jeanbernad.chainofresponsibilityexample.databinding.FragmentSignUpBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var binding by autoCleared<FragmentSignUpBinding>()
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var unregistrar: Unregistrar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unregistrar = KeyboardVisibilityEvent.registerEventListener(requireActivity()) {
            if (it) {
                binding.signUpBtnKeyboard.visibility = View.VISIBLE
                binding.signUpBtn.visibility = View.GONE
            } else {
                binding.signUpBtnKeyboard.visibility = View.GONE
                binding.signUpBtn.visibility = View.VISIBLE
            }
        }

        binding.signUpBtn.setOnClickListener {
            viewModel.check()
        }

        binding.signUpBtnKeyboard.setOnClickListener {
            viewModel.check()
        }

        viewModel.signUpState.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT).show()
                binding.loginEt.text = null
                binding.passwordEt.text = null
                binding.confirmPasswordEt.text = null
                binding.login.error = null
                binding.password.error = null
                binding.confirmPassword.error = null
            } else {
                viewModel.checkLogin(binding.loginEt.text.toString().trim())
                viewModel.checkPassword(binding.passwordEt.text.toString().trim())
                viewModel.checkConfirmPassword(
                    binding.confirmPasswordEt.text.toString().trim(),
                    binding.passwordEt.text.toString().trim()
                )
            }
        }

        binding.loginEt.doOnTextChanged { text, _, _, _ ->
            viewModel.checkLogin(text.toString().trim())
        }

        viewModel.loginState.observe(viewLifecycleOwner) {
            binding.login.error = it
        }

        binding.passwordEt.doOnTextChanged { text, _, _, _ ->
            if (binding.confirmPassword.isEnabled) {
                viewModel.checkConfirmPassword(
                    binding.confirmPasswordEt.text.toString(),
                    text.toString()
                )
            }
            viewModel.checkPassword(text.toString().trim())
        }

        viewModel.passwordState.observe(viewLifecycleOwner) {
            binding.password.error = it
            binding.confirmPassword.isEnabled = it == null
        }

        binding.confirmPasswordEt.doOnTextChanged { text, _, _, _ ->
            viewModel.checkConfirmPassword(
                text.toString().trim(),
                binding.passwordEt.text.toString()
            )
        }

        viewModel.confirmPasswordState.observe(viewLifecycleOwner) {
            binding.confirmPassword.error = it
        }
    }
}