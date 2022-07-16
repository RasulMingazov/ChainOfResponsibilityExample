package com.jeanbernad.chainofresponsibilityexample.handler

class DigitContainsHandler(
    errorMessage: String
) : Handler.Base(errorMessage) {

    override fun isCorrect(text: String) =
        text.matches(Regex(".*\\d.*"))
}