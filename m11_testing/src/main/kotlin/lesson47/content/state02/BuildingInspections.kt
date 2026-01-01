package lesson47.content.state02

import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration

class BuildingInspections {
    private val _log: MutableList<String> = Collections.synchronizedList(mutableListOf())
    val log: List<String> get() = _log

    private fun log(line: String) {
        println(line)
        _log.add(line)
    }

    suspend fun activity(emoji: String, description: String, duration: Duration) {
        log("$emoji Checking $description...")
        delay(duration)
        log("$emoji The $description passed. ☑️")
    }
}
