package com.jeanbernad.chainofresponsibilityexample.handler

class HandlerChain(
    private val actual: Handler,
    private val next: Handler
) : Handler {

    private var isActualCorrect = false

    override fun isCorrect(text: String): Boolean {
        isActualCorrect = actual.isCorrect(text)
        return if (isActualCorrect) next.isCorrect(text) else false
    }

    override fun errorMessage() =
        if (isActualCorrect) next.errorMessage() else actual.errorMessage()
}