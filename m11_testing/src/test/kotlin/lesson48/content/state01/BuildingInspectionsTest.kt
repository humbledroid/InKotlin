package lesson48.content.state01

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
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
        val inspections = BuildingInspections()

        inspections.checkFireSafety()

        assertThat(inspections.log)
            .containsOnly(
                "🧯 Checking smoke detector batteries...",
                "🧯 Checking fire extinguisher...",
                "🧯 The smoke detector batteries passed. ☑️",
                "🧯 The fire extinguisher passed. ☑️",
                "🧯 Fire safety check passed. ✅",
            )
    }
}
