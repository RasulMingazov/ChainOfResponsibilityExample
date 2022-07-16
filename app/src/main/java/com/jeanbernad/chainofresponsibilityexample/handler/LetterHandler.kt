package com.jeanbernad.chainofresponsibilityexample.handler

class LetterHandler(
    errorMessage: String
) : Handler.Base(errorMessage) {

    override fun isCorrect(text: String) =
        text.matches(Regex("[a-zA-Z0-9]*"))
}