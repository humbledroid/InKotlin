package lesson08.content.state03

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.seconds

fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        nurseryRhyme()
    }
}

suspend fun nurseryRhyme() {
    printAndPause("Twinkle, twinkle, little var")
    printAndPause("How I wonder what you are")
    printAndPause("Changing when I look away")
    printAndPause("Maybe not... it's hard to say")
    printAndPause("Twinkle, twinkle, little var")
    printAndPause("Should've used a val by far")
}

suspend fun printAndPause(line: String) {
    println(line)
    delay(1.seconds)
}
