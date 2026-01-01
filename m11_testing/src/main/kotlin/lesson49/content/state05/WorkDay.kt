package lesson49.content.state05

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

class WorkDay(
    private val day: String,
    private val log: (String) -> Unit,
    private val scope: CoroutineScope
) {
    fun keepTheGarageClean() = scope.launch {
        while (true) {
            delay(1.seconds)
            log("  🧹 $day: Hank is keeping the garage clean!")
        }
    }

    fun closeShop() {
        scope.cancel()
        log("🌙 $day: Closing shop for today.")
    }
}
