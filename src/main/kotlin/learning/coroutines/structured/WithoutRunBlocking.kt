package learning.coroutines.structured

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
   val job = GlobalScope.launch {
       println("Before launch")

       println("Global Launch $this")
       launch {
           println("1st Launch $this")
           delay(500)
           println("Structural integrity approved")
       }

       launch {
           println("2nd Launch $this")
           delay(1000)
           println("Electrical safety cleared")
       }

       launch {
           println("3rd Launch $this")
           delay(1500)
           println("Fire safety check passed")
       }
   }

    job.invokeOnCompletion {
        println("Completed")
    }

    Thread.sleep(2000)
}
