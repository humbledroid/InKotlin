package lesson48.content.state01

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class BuildingInspections {
    private val _log: MutableList<String> = Collections.synchronizedList(mutableListOf())
    val log: List<String> get() = _log

    private fun log(line: String) {
        _log.add(line)
        println(line)
    }

    suspend fun checkFireSafety() {
        coroutineScope {
            launch(Dispatchers.Default) { activity("🧯", "smoke detector batteries", 5.seconds) }
            launch(Dispatchers.Default) { activity("🧯", "fire extinguisher", 6.seconds) }
        }
        log("🧯 Fire safety check passed. ✅")
    }

    suspend fun activity(emoji: String, description: String, duration: Duration) {
        log("$emoji Checking $description...")
        delay(duration)
        log("$emoji The $description passed. ☑️")
    }
}
