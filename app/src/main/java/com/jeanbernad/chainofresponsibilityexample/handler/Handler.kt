package com.jeanbernad.chainofresponsibilityexample.handler

interface Handler {
    fun errorMessage(): String

    fun isCorrect(text: String): Boolean

    abstract class Base(private val errorMessage: String): Handler {
        override fun errorMessage() = errorMessage
    }
}