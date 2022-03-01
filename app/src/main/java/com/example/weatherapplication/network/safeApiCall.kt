package com.example.weatherapplication.network

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

suspend fun<T: Any> safeApiCall(call: suspend ()-> Result<T>): Result<T>{
    return try {
        call()
    }catch (e: Exception){
        var errorMessage: String? = null
        when(e){
            is HttpException -> {
                val body = e.response()?.errorBody()
                errorMessage = getErrorMessage(body)
            }
        }
        Result.failure(Throwable(errorMessage, null))
    }
}

open class Event<out T>(private val content: T) {
    var consumed = false
        private set // Allow external read but not write

    /**
     * Consumes the content if it's not been consumed yet.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }

    /**
     * @return The content whether it's been handled or not.
     */
    fun peek(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}


fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has("message") -> jsonObject.getString("message")
            else -> "Something wrong happened"
        }
    } catch (e: Exception) {
        "Something wrong happened"
    }
}