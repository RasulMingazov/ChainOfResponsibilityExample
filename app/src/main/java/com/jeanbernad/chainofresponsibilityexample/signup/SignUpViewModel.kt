package com.jeanbernad.chainofresponsibilityexample.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeanbernad.chainofresponsibilityexample.handler.*

class SignUpViewModel : ViewModel() {

    private var isLoginSuccessful = false
    private var isPasswordSuccessful = false
    private var isConfirmPasswordSuccessful = false

    val signUpState = MutableLiveData<Boolean>()
    val loginState = MutableLiveData<String?>()
    val passwordState = MutableLiveData<String?>()
    val confirmPasswordState = MutableLiveData<String?>()

    private val loginHandler by lazy {
        HandlerChain(
            EmptinessHandler("Login can't be empty"),
            HandlerChain(
                MinLengthHandler("Minimum login length is", MIN_LENGTH_LOGIN),
                HandlerChain(
                    DigitContainsHandler("Login must contain a digit"),
                    LetterHandler("Login must contain only latin characters")
                )
            )
        )
    }

    private val passwordHandler by lazy {
        HandlerChain(
            EmptinessHandler("Password can't be empty"),
            HandlerChain(
                MinLengthHandler("Minimum password length is", MIN_LENGTH_PASSWORD),
                HandlerChain(
                    DigitContainsHandler("Password must contain a digit"),
                    UpperCaseHandler("Password must contain a upper case symbol")
                )
            )
        )
    }

    fun check() {
        signUpState.value = isLoginSuccessful && isPasswordSuccessful && isConfirmPasswordSuccessful
    }

    fun checkLogin(login: String) {
        if (loginHandler.isCorrect(login)) {
            isLoginSuccessful = true
            loginState.value = null
        } else {
            loginState.value = loginHandler.errorMessage()
            isLoginSuccessful = false
        }
    }

    fun checkPassword(password: String) {
        if (passwordHandler.isCorrect(password)) {
            isPasswordSuccessful = true
            passwordState.value = null
        } else {
            passwordState.value = passwordHandler.errorMessage()
            isPasswordSuccessful = false
        }
    }

    fun checkConfirmPassword(confirmPassword: String, password: String) {
        val equalityHandler = EqualityHandler("Passwords must match", password)
        if (equalityHandler.isCorrect(confirmPassword)) {
            isConfirmPasswordSuccessful = true
            confirmPasswordState.value = null
        } else {
            confirmPasswordState.value = equalityHandler.errorMessage()
            isConfirmPasswordSuccessful = false
        }
    }

    companion object {
        private const val MIN_LENGTH_PASSWORD = 4
        private const val MIN_LENGTH_LOGIN = 6
    }
}