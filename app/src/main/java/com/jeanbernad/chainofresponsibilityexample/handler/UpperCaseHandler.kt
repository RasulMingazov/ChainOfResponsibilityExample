package com.jeanbernad.chainofresponsibilityexample.handler

class UpperCaseHandler(
    errorMessage: String
) : Handler.Base(errorMessage) {

    override fun isCorrect(text: String) =
        text.matches(Regex(".*[A-Z].*"))
}