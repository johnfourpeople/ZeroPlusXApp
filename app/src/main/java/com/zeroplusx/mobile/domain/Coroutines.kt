package com.zeroplusx.mobile.domain

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException

suspend fun <R> runCatchingWithoutCancellation(block: suspend () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: TimeoutCancellationException) {
        Result.failure(e)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
