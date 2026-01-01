package lesson53.state08

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.debug.CoroutinesBlockHoundIntegration
import kotlinx.coroutines.debug.junit5.CoroutinesTimeout
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import reactor.blockhound.BlockHound

class RestaurantTest {
    @Test
    @CoroutinesTimeout(4000)
    fun `restaurant completes quickly`() = runBlocking {
        startRestaurant()
    }

    @Test
    fun `restaurant does not block`() = runTest {
        BlockHound.install(CoroutinesBlockHoundIntegration())
        withContext(Dispatchers.Default) { startRestaurant() }
    }
}
