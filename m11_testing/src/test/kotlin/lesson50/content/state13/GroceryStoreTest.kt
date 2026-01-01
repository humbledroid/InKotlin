package lesson50.content.state13

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GroceryStoreTest {
    @Test
    fun `purchases are split into stock and items`() = runTest {
        GroceryStore.stock.test {
            GroceryStore.shop("🥛", "🍉", "🍊")

            assertThat(awaitItem()).isEqualTo("🥛 Milk")
            assertThat(awaitItem()).isEqualTo("🍉 Watermelon")
            assertThat(awaitItem()).isEqualTo("🍊 Orange")
        }

        GroceryStore.income.test {
            GroceryStore.shop("🥛", "🍉", "🍊")

            assertThat(awaitItem()).isEqualTo(3_39)
            assertThat(awaitItem()).isEqualTo(5_99)
            assertThat(awaitItem()).isEqualTo(1_29)
        }
    }
}
