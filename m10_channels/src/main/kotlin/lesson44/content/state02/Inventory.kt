@file:OptIn(ObsoleteCoroutinesApi::class, ExperimentalAtomicApi::class)

package lesson44.content.state02

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.minusAssign
import kotlin.concurrent.atomics.plusAssign
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val boxes = AtomicInt(50)
val hasOpenOrder = AtomicBoolean(false)

class Boxer(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Box>,
    val factory: SendChannel<BoxOrder>
) {
    suspend fun start() {
        for (cupcake in inbound) {
            println("🎁 Boxing $cupcake")
            simulateWork(250.milliseconds)
            val box = Box(cupcake)
            boxes -= 1
            if (boxes.load() < 15 && !hasOpenOrder.load()) {
                factory.send(BoxOrder(25))
                hasOpenOrder.store(true)
            }
            outbound.send(box)
        }
    }
}

class BoxFactory(
    val inbound: ReceiveChannel<BoxOrder>
) {
    suspend fun start() {
        for (order in inbound) {
            println("🏭 Box factory is fulfilling an order for ${order.count} boxes.")
            delay(1.seconds)
            boxes += order.count
            println("🏭 Box factory has delivered ${order.count} boxes.")
            hasOpenOrder.store(false)
            println("🧮 Inventory: Boxes=$boxes")
        }
    }
}

fun main(): Unit = runBlocking(Dispatchers.Default) {
    val rng = Random(0)
    val cupcakeChannel = Channel<Cupcake>()
    val boxChannel = Channel<Box>()
    val boxOrderChannel = Channel<BoxOrder>()

    launch { BoxFactory(boxOrderChannel).start() }
    launch { Boxer(cupcakeChannel, boxChannel, boxOrderChannel).start() }
    launch { Boxer(cupcakeChannel, boxChannel, boxOrderChannel).start() }
    launch { Boxer(cupcakeChannel, boxChannel, boxOrderChannel).start() }

    launch {
        repeat(45) { id ->
            cupcakeChannel.send(
                Cupcake(
                    id,
                    Batter.entries.random(rng),
                    Frosting.entries.random(rng)
                )
            )
        }
    }

    for (box in boxChannel) {
        println("✅ $box")
    }
}

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}

data class Cupcake(
    val id: Int,
    val batter: Batter,
    val frosting: Frosting? = null,
    val decoration: Decoration? = null
)

class BoxOrder(val count: Int)
data class Box(val cupcake: Cupcake)

enum class Batter { VANILLA, CHOCOLATE, ALMOND }
enum class Frosting { VANILLA, CHOCOLATE }
enum class Decoration { SPRINKLES, CHOCOLATE_DRIZZLE, CARAMEL_DRIZZLE }
