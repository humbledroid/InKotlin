package learning.coroutines.structured

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    lateinit var scope: CoroutineScope

    val job = GlobalScope.launch {
        scope = this
        scope.launch {
            println("1st Launch $this")
            delay(500)
            println("Structural integrity approved")
        }

        scope.launch {
            println("2nd Launch $this")
            delay(1000)
            println("Electrical safety cleared")
        }

        scope.launch {
            println("3rd Launch $this")
            delay(1500)
            println("Fire safety check passed")
        }
    }

    Thread.sleep(2000)
    println(job === scope)
}