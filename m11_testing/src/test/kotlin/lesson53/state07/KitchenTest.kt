package lesson53.state07

import kotlinx.coroutines.debug.junit5.CoroutinesTimeout
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class RestaurantTest {
    @Test
    @CoroutinesTimeout(4000)
    fun `restaurant completes quickly`() = runBlocking {
        startRestaurant()
    }
}
