package learning.coroutines.foundations

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext


fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        nurseryRhyme()
    }
}


suspend fun nurseryRhyme() {
    printAndPause("Twinkle, twinkle little var")
    printAndPause("Twinkle, 1")
    printAndPause("Twinkle, 2")
    printAndPause("Twinkle, 3")
    printAndPause("Twinkle, 4")
    printAndPause("Twinkle, 5")
    printAndPause("Twinkle, 6")
    printAndPause("Twinkle, 7")
}

suspend fun printAndPause(s: String) {
    println(s)
    delay(1000L)
}