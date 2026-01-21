package learning.coroutines.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
//    sequenceOf(
//        "2025-06-05",
//        "2025-06-06",
//        "2025-06-07",
//        "2025-06-08",
//        "2025-06-09",
//    ).forEach { println(it) }

    val dates = flowOf(
        "2025-06-05",
        "2025-06-06",
        "2025-06-07",
        "2025-06-08",
        "2025-06-09",
    )

    dates.collect(::println)
    println("--------->adding another collect<-----------")
    dates
        .collect {
            // this gives us suspending block
            delay(1000)
            println(it)
        }
}
