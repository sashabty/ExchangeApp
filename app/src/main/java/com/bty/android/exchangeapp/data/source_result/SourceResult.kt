package com.bty.android.exchangeapp.data.source_result

sealed class SourceResult<out T>(val value: T? = null) {
    object Loading: SourceResult<Nothing>()
    object Empty: SourceResult<Nothing>()
    class Success<T>(value: T): SourceResult<T>(value)
    class Failure<T>(
        val status: SourceResultFailureHttpStatus? = null,
        val exception: Throwable? = null
    ): SourceResult<T>()

    fun <R> map(transformer: (T) -> R): SourceResult<R> {
        return when(this) {
            Loading -> Loading
            Empty -> Empty
            is Success -> Success(value = transformer.invoke(value!!))
            is Failure -> Failure(
                status = this.status,
                exception = this.exception
            )
        }
    }
}
