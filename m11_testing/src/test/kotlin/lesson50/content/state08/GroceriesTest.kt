package lesson50.content.state08

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GroceriesTest {
    @Test
    fun `shops for groceries`() = runTest {
        groceries.test {
            assertThat(awaitItem())
                .isEqualTo(GroceryItem("🥛 Milk", 3_39))

            assertThat(awaitItem())
                .isEqualTo(GroceryItem("🍉 Watermelon", 5_99))

            awaitComplete()
        }
    }
}
