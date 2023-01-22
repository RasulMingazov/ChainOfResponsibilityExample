package com.jeanbernad.chainofresponsibilityexample.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeanbernad.chainofresponsibilityexample.handler.*

class SignUpViewModel() : ViewModel() {

    private var isLoginSuccessful = false
    private var isPasswordSuccessful = false

    val signUpState = MutableLiveData<Boolean>()
    val loginState = MutableLiveData<String?>()
    val passwordState = MutableLiveData<String?>()

    private var actualLogin = ""
    private var actualPassword = ""

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
        if (isLoginSuccessful && isPasswordSuccessful) {
            signUpState.value = true
        } else {
            checkLogin(actualLogin)
            checkPassword(actualPassword)
        }
    }

    fun checkLogin(login: String) {
        actualLogin = login
        if (!loginHandler.isCorrect(actualLogin)) {
            loginState.value = loginHandler.errorMessage()
            isLoginSuccessful = false
        } else {
            isLoginSuccessful = true
            loginState.value = null
        }
    }

    fun checkPassword(password: String) {
        actualPassword = password
        if (!passwordHandler.isCorrect(actualPassword)) {
            passwordState.value = passwordHandler.errorMessage()
            isPasswordSuccessful = false
        } else {
            isPasswordSuccessful = true
            passwordState.value = null
        }
    }

    fun reset() {
        actualLogin = ""
        actualPassword = ""
    }

    companion object {
        private const val MIN_LENGTH_PASSWORD = 8
        private const val MIN_LENGTH_LOGIN = 6
    }
}