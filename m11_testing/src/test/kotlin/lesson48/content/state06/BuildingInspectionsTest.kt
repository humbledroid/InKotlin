@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson48.content.state06

import assertk.assertThat
import assertk.assertions.containsExactly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class BuildingInspectionsTest {
    @Test
    fun `activity() logs checks and successes`() = runTest {
        val inspections = BuildingInspections()

        inspections.activity("🧪", "tests", 5.seconds)

        assertThat(inspections.log)
            .containsExactly("🧪 Checking tests...", "🧪 The tests passed. ☑️")
    }

    @Test
    fun `fireSafety() checks batteries and fire extinguisher`() = runTest {
        val dispatcher = StandardTestDispatcher(this.testScheduler)
        val inspections = BuildingInspections(dispatcher)

        launch { inspections.checkFireSafety() }

        println("${inspections.log.size} logs at $currentTime milliseconds")
        runCurrent()
        println("${inspections.log.size} logs at $currentTime milliseconds")

        advanceTimeBy(5.seconds)
        println("${inspections.log.size} logs at $currentTime milliseconds")
        runCurrent()
        println("${inspections.log.size} logs at $currentTime milliseconds")

        advanceTimeBy(1.seconds)
        println("${inspections.log.size} logs at $currentTime milliseconds")
        runCurrent()
        println("${inspections.log.size} logs at $currentTime milliseconds")
    }
}
