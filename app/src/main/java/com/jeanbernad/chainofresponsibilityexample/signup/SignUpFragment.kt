package com.jeanbernad.chainofresponsibilityexample.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jeanbernad.chainofresponsibilityexample.*
import com.jeanbernad.chainofresponsibilityexample.databinding.FragmentSignUpBinding
import com.jeanbernad.chainofresponsibilityexample.keyboard.KeyboardState

class SignUpFragment : Fragment(R.layout.fragment_sign_up), KeyboardState {

    private var binding by autoCleared<FragmentSignUpBinding>()
    private val viewModel by viewModels<SignUpViewModel>()
    private val listenKeyboard = ListenKeyboard.Base(this)


    private val loginWatcher by lazy {
        object : JeanTextWatcher({ login -> viewModel.checkLogin(login) }) {}
    }

    private val passwordWatcher by lazy {
        object : JeanTextWatcher({ password -> viewModel.checkPassword(password) }) {}
    }

    private val confirmPasswordWatcher by lazy {
        object : JeanTextWatcher({ password -> viewModel.checkConfirmPassword(password) }) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun down() {
        binding.signUpBtnKeyboard.isVisible = false
        binding.signUpBtn.isVisible = true
    }

    override fun up() {
        binding.signUpBtnKeyboard.isVisible = true
            binding.signUpBtn.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        listenKeyboard.init(requireActivity())
    }

    override fun onPause() {
        super.onPause()
        listenKeyboard.unregister()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginEt.addTextChangedListener(loginWatcher)

        viewModel.loginState.observe(viewLifecycleOwner) {
            binding.login.error = it
        }

        binding.passwordEt.addTextChangedListener(passwordWatcher)

        viewModel.passwordState.observe(viewLifecycleOwner) {
            binding.password.error = it
        }

        binding.confirmPasswordEt.addTextChangedListener(confirmPasswordWatcher)

        viewModel.confirmPasswordState.observe(viewLifecycleOwner) {
            binding.confirmPassword.error = it
        }

        binding.signUpBtn.setOnClickListener {
            viewModel.check()
        }

        viewModel.signUpState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT).show()
            binding.passwordEt.removeTextChangedListener(passwordWatcher)
            binding.loginEt.removeTextChangedListener(loginWatcher)
            binding.confirmPasswordEt.removeTextChangedListener(confirmPasswordWatcher)
            binding.loginEt.text = null
            binding.passwordEt.text = null
            binding.confirmPasswordEt.text = null
            binding.login.error = null
            binding.password.error = null
            binding.confirmPassword.error = null
            binding.passwordEt.addTextChangedListener(passwordWatcher)
            binding.loginEt.addTextChangedListener(loginWatcher)
            binding.confirmPasswordEt.addTextChangedListener(confirmPasswordWatcher)
            viewModel.reset()
        }
    }
}