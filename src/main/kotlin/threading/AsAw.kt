package threading

import java.util.concurrent.Executors
import java.util.concurrent.Future

fun parse(input:String) : Future<Int>{
    val executor = Executors.newSingleThreadExecutor()
    return executor.submit<Int> {
        Thread.sleep(2000)
        return@submit input.toInt()
    }
}


fun main(){
    val parser : Future<Int> = parse("310")
    val result = parser.get()
    print(result)
    println("Started")
}
