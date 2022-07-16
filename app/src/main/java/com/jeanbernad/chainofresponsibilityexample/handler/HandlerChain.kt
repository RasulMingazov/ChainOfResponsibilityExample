package com.jeanbernad.chainofresponsibilityexample.handler

class HandlerChain(
    private val actual: Handler,
    private val next: Handler
) : Handler {

    private var isActialCorrect = false

    override fun isCorrect(text: String): Boolean {
        isActialCorrect = actual.isCorrect(text)
        return if (isActialCorrect) next.isCorrect(text) else false
    }

    override fun errorMessage() =
        if (isActialCorrect) next.errorMessage() else actual.errorMessage()
}