package lesson47.content.state02

import assertk.assertThat
import assertk.assertions.containsExactly
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class BuildingInspectionsTest {
    @Test
    fun `activity() logs when checking starts and passes`() = runTest {
        val inspections = BuildingInspections()

        inspections.activity("🧪", "tests", 5.seconds)

        assertThat(inspections.log)
            .containsExactly("🧪 Checking tests...", "🧪 The tests passed. ☑️")
    }
}
