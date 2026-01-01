package lesson04.content.state02

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    GlobalScope.launch {
        nurseryRhyme()
    }
    // main() will finish without waiting for this coroutine to run.
    // You can uncomment the next line to force it to wait long enough.
    // Thread.sleep(6000)
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
