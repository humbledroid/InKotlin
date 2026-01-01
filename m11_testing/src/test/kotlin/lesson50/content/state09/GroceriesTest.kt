package lesson50.content.state09

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
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

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `third item is out of stock`() = runTest {
        groceries.test {
            skipItems(2)
            assertThat(awaitError())
                .isInstanceOf(OutOfStockException::class)
                .hasMessage("🍊 is out of stock.")
        }
    }
}
