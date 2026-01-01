@file:OptIn(ExperimentalCoroutinesApi::class)

package lesson48.content.state05

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsAtLeast
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
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

        advanceTimeBy(1.seconds)

        assertThat(inspections.log)
            .containsOnly(
                "🧯 Checking smoke detector batteries...",
                "🧯 Checking fire extinguisher...",
            )

        advanceTimeBy(4.5.seconds)

        assertThat(inspections.log)
            .contains("🧯 The smoke detector batteries passed. ☑️")

        advanceUntilIdle()

        assertThat(inspections.log)
            .containsAtLeast(
                "🧯 The fire extinguisher passed. ☑️",
                "🧯 Fire safety check passed. ✅",
            )
    }
}
