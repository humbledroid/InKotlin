package lesson50.content.state12

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GroceryStoreTest {
    @Test
    fun `purchases are split into stock and items`() = runTest {
        GroceryStore.shop("🥛", "🍉", "🍊")

        GroceryStore.stock.test {
            assertThat(awaitItem()).isEqualTo("🥛 Milk")
            assertThat(awaitItem()).isEqualTo("🍉 Watermelon")
            assertThat(awaitItem()).isEqualTo("🍊 Orange")
        }

        GroceryStore.income.test {
            assertThat(awaitItem()).isEqualTo(3_39)
            assertThat(awaitItem()).isEqualTo(5_99)
            assertThat(awaitItem()).isEqualTo(1_29)
        }
    }
}
