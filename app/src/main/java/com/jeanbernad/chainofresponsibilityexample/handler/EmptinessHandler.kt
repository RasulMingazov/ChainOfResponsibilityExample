package com.jeanbernad.chainofresponsibilityexample.handler

class EmptinessHandler(
    errorMessage: String
) : Handler.Base(errorMessage) {

    override fun isCorrect(text: String) =
        text != ""
}