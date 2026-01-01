package lesson02.content

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

fun main() {
    runBlocking {
        doStuff()
    }
}

suspend fun doStuff() {
    goWorkOnSomethingElse()
}

suspend fun goWorkOnSomethingElse() {
    suspendCoroutineUninterceptedOrReturn<Unit> { _ -> COROUTINE_SUSPENDED }
}
