package com.jeanbernad.chainofresponsibilityexample.handler

class EqualityHandler(
    errorMessage: String,
    private val anotherText: String
) : Handler.Base(errorMessage) {

    override fun isCorrect(text: String) =
        text == anotherText
}