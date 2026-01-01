package lesson50.content.state14

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GroceryStoreTest {
    @Test
    fun `purchases are split into stock and items`() = runTest {
        turbineScope {
            val stock: ReceiveTurbine<String> = GroceryStore.stock.testIn(backgroundScope)
            val income: ReceiveTurbine<Int> = GroceryStore.income.testIn(backgroundScope)

            GroceryStore.shop("🥛", "🍉", "🍊")

            assertThat(stock.awaitItem()).isEqualTo("🥛 Milk")
            assertThat(income.awaitItem()).isEqualTo(3_39)

            assertThat(stock.awaitItem()).isEqualTo("🍉 Watermelon")
            assertThat(income.awaitItem()).isEqualTo(5_99)

            assertThat(stock.awaitItem()).isEqualTo("🍊 Orange")
            assertThat(income.awaitItem()).isEqualTo(1_29)
        }
    }
}
