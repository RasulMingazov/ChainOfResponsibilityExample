package com.jeanbernad.chainofresponsibilityexample.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeanbernad.chainofresponsibilityexample.handler.*

class SignUpViewModel : ViewModel() {

    private var isLoginSuccessful = false
    private var isPasswordSuccessful = false
    private var isConfirmPasswordSuccessful = false

    private val _signUpState = MutableLiveData<Boolean>()
    val signUpState: LiveData<Boolean>
        get() = _signUpState

    private val _loginState = MutableLiveData<String?>()
    val loginState: LiveData<String?>
        get() = _loginState

    private val _passwordState = MutableLiveData<String?>()
    val passwordState: LiveData<String?>
        get() = _passwordState

    private val _confirmPasswordState = MutableLiveData<String?>()
    val confirmPasswordState: LiveData<String?>
        get() = _confirmPasswordState

    private var actualLogin = ""
    private var actualPassword = ""
    private var actualConfirmPassword = ""

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
        if (isLoginSuccessful && isPasswordSuccessful && isConfirmPasswordSuccessful) {
            _signUpState.value = true
        } else {
            checkLogin(actualLogin)
            checkPassword(actualPassword)
            checkConfirmPassword(actualConfirmPassword)
        }
    }

    fun checkLogin(login: String) {
        actualLogin = login
        if (loginHandler.isCorrect(actualLogin)) {
            isLoginSuccessful = true
            _loginState.value = null
        } else {
            _loginState.value = loginHandler.errorMessage()
            isLoginSuccessful = false
        }
    }

    fun checkPassword(password: String) {
        actualPassword = password
        if (passwordHandler.isCorrect(actualPassword)) {
            isPasswordSuccessful = true
            _passwordState.value = null
        } else {
            _passwordState.value = passwordHandler.errorMessage()
            isPasswordSuccessful = false
        }
    }

    fun checkConfirmPassword(confirmPassword: String) {
        actualConfirmPassword = confirmPassword
        val equalityHandler = EqualityHandler("Passwords must match", actualPassword)
        if (equalityHandler.isCorrect(confirmPassword)) {
            isConfirmPasswordSuccessful = true
            _confirmPasswordState.value = null
        }
        if (!equalityHandler.isCorrect(confirmPassword) && passwordHandler.isCorrect(actualPassword)) {
            _confirmPasswordState.value = equalityHandler.errorMessage()
            isConfirmPasswordSuccessful = false
        }
    }

    fun reset() {
        actualConfirmPassword = ""
        actualLogin = ""
        actualPassword = ""
    }

    companion object {
        private const val MIN_LENGTH_PASSWORD = 8
        private const val MIN_LENGTH_LOGIN = 6
    }
}