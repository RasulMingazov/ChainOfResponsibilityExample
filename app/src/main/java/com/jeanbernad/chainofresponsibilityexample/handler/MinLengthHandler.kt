package com.jeanbernad.chainofresponsibilityexample.handler

class MinLengthHandler(
    private val errorMessage: String,
    private val minLength: Int
) : Handler {
    override fun errorMessage() = "$errorMessage $minLength"

    override fun isCorrect(text: String) = text.length >= minLength
}