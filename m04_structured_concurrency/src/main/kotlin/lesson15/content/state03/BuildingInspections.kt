package lesson15.content.state03

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val job = GlobalScope.launch {
        println("Start")
        launch { delay(500); println("🔧 Structural integrity approved.") }
        launch { delay(1000); println("🔌 Electrical safety cleared.") }
        launch { delay(1500); println("🧯 Fire safety check passed.") }
    }

    job.invokeOnCompletion { println("Done") }

    Thread.sleep(2000)
}
