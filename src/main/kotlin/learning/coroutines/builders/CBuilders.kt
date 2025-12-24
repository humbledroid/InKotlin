package learning.coroutines.builders

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

fun main() {
    runBlocking {
        doSomething()
    }
}

suspend fun doSomething() {
    doSomethingSuspend()
}


suspend fun doSomethingSuspend() {
    suspendCoroutineUninterceptedOrReturn<Unit> { _ -> COROUTINE_SUSPENDED }
}