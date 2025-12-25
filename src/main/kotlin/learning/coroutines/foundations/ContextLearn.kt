package learning.coroutines.foundations

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking(context = CoroutineName("Sample")) {
        println(coroutineContext)
        //[CoroutineName(Sample), BlockingCoroutine{Active}@11028347, BlockingEventLoop@14899482]
        launch {
            println(coroutineContext)
            // [CoroutineName(Sample), StandaloneCoroutine{Active}@77afea7d, BlockingEventLoop@14899482]
            /**
             * Inherits the name and dispatcher from the parent, but will have its own job
             */
        }
    }
}