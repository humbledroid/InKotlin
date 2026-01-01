package lesson29.content.state04

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform

suspend fun main() {
    flowOf(
        "2025-06-05",
        "2025-06-06",
        "2025-06-07",
        "2025-06-08",
        "2025-06-09"
    )
        .transform {
            emit("\n$it\n-----------------")
            eventsFor(it).forEach { event -> emit(event) }
        }
        .collect { println(it) }
}

fun eventsFor(date: String): List<String> = when (date) {
    "2025-06-04" -> listOf("Stand-up", "Interview Candidate")
    "2025-06-05" -> listOf("Stand-up", "Sync-up with Jon")
    "2025-06-06" -> listOf("Stand-up", "Product Demos")
    "2025-06-07" -> listOf("Coffee at Jim's")
    "2025-06-08" -> listOf("Family Luncheon")
    "2025-06-09" -> listOf("Staff meeting", "Lunch with Libby", "Design Meeting")
    "2025-06-10" -> listOf("Stand-up", "Haircut at Bert's")
    "2025-06-11" -> listOf("Stand-up")
    "2025-06-12" -> listOf("Stand-up", "Call with Vendor")
    "2025-06-13" -> listOf("Stand-up")
    else         -> emptyList()
}
