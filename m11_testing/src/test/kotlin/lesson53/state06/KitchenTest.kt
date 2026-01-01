package lesson53.state06

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class RestaurantTest {
    @Test
    fun `restaurant completes quickly`() = runTest {
        withTimeout(4.seconds) {
            startRestaurant()
        }
    }
}
