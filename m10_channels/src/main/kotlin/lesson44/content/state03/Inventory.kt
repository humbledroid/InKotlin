@file:OptIn(ObsoleteCoroutinesApi::class, ExperimentalAtomicApi::class)

package lesson44.content.state03

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlin.concurrent.atomics.*
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class Boxer(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Box>,
    val inventory: SendChannel<InventoryMessage>
) {
    suspend fun start() {
        for (cupcake in inbound) {
            println("🎁 Boxing $cupcake")
            simulateWork(250.milliseconds)
            val box = Box(cupcake)
            inventory.send(DecreaseBoxes(1))
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
            order.address.send(IncreaseBoxes(order.count))
            println("🏭 Box factory has delivered ${order.count} boxes.")
        }
    }
}

fun CoroutineScope.createInventory(orderBoxes: SendChannel<BoxOrder>) = actor<InventoryMessage>(capacity = BUFFERED) {
    var boxes = 50
    var hasOpenOrder = false

    for (message in channel) {
        when (message) {
            is DecreaseBoxes -> {
                boxes -= message.count
                if (boxes < 15 && !hasOpenOrder) {
                    orderBoxes.send(BoxOrder(25, channel))
                    hasOpenOrder = true
                }
            }

            is IncreaseBoxes -> {
                boxes += message.count
                hasOpenOrder = false
            }
        }

        println("🧮 Inventory: Boxes=$boxes")
    }
}

sealed interface InventoryMessage {
    val count: Int
}

class DecreaseBoxes(override val count: Int) : InventoryMessage
class IncreaseBoxes(override val count: Int) : InventoryMessage

fun main(): Unit = runBlocking(Dispatchers.Default) {
    val rng = Random(0)
    val cupcakeChannel = Channel<Cupcake>()
    val boxChannel = Channel<Box>()
    val boxOrderChannel = Channel<BoxOrder>()

    val inventory = createInventory(boxOrderChannel)

    launch { BoxFactory(boxOrderChannel).start() }
    launch { Boxer(cupcakeChannel, boxChannel, inventory).start() }
    launch { Boxer(cupcakeChannel, boxChannel, inventory).start() }
    launch { Boxer(cupcakeChannel, boxChannel, inventory).start() }

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

class BoxOrder(val count: Int, val address: SendChannel<IncreaseBoxes>)
data class Box(val cupcake: Cupcake)

enum class Batter { VANILLA, CHOCOLATE, ALMOND }
enum class Frosting { VANILLA, CHOCOLATE }
enum class Decoration { SPRINKLES, CHOCOLATE_DRIZZLE, CARAMEL_DRIZZLE }
