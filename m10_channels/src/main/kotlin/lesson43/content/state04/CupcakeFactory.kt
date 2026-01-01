package lesson43.content.state04

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class Baker(
    val inbound: ReceiveChannel<Int>,
    val outbound: SendChannel<Cupcake>
) {
    private val rng = Random(0)

    suspend fun start() {
        for (count in inbound) {
            println("🥣 Baking $count cupcakes")
            simulateWork(1.seconds)
            val cupcakes = List(count) { Cupcake(it, Batter.entries.random(rng)) }
            cupcakes.forEach { outbound.send(it) }
        }
    }
}

class Decorator(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Cupcake>
) {
    val rng = Random(1)

    suspend fun start() {
        for (cupcake in inbound) {
            println("🧁 Decorating $cupcake")
            simulateWork(1.seconds)
            val decorated = cupcake.copy(
                frosting = Frosting.entries.random(rng),
                topping = Topping.entries.random(rng)
            )
            outbound.send(decorated)
        }
    }
}

class Boxer(
    val inbound: ReceiveChannel<Cupcake>,
    val outbound: SendChannel<Box>
) {
    suspend fun start() {
        for (cupcake in inbound) {
            println("🎁 Boxing $cupcake")
            simulateWork(250.milliseconds)
            val box = Box(cupcake)
            outbound.send(box)
        }
    }
}

class Inspector(
    val fromDecorator: ReceiveChannel<Cupcake>,
    val fromBoxer: ReceiveChannel<Box>,
    val toDecorator: SendChannel<Cupcake>,
    val toBoxer: SendChannel<Cupcake>
) {
    val rng = Random(123)

    suspend fun start() {
        while (currentCoroutineContext().isActive) {
            val cupcake = fromDecorator.receive()
            println("🔍 Inspecting product")

            if (rng.nextInt(100) < 20) {
                println("⛔ Decoration failed: Cupcake #${cupcake.id}")
                toDecorator.send(cupcake)
            } else {
                toBoxer.send(cupcake)
            }

            val box = fromBoxer.receive()

            if (rng.nextInt(100) < 10) {
                println("⛔ Box failed: Cupcake #${box.cupcake.id}")
                toBoxer.send(box.cupcake)
            } else {
                println("✅ Passed: $box")
            }
        }
    }
}

fun main() = runBlocking(Dispatchers.Default) {
    val countChannel = Channel<Int>(BUFFERED)
    val bakedChannel = Channel<Cupcake>(BUFFERED)
    val decoratedChannel = Channel<Cupcake>(BUFFERED)
    val decoratedOkChannel = Channel<Cupcake>(BUFFERED)
    val boxChannel = Channel<Box>(BUFFERED)

    launch { Baker(countChannel, bakedChannel).start() }
    launch { Decorator(bakedChannel, decoratedChannel).start() }
    launch { Boxer(decoratedOkChannel, boxChannel).start() }
    launch { Inspector(decoratedChannel, boxChannel, bakedChannel, decoratedOkChannel).start() }
    countChannel.send(12)
}

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}

data class Cupcake(
    val id: Int,
    val batter: Batter,
    val frosting: Frosting? = null,
    val topping: Topping? = null
)

data class Box(val cupcake: Cupcake)

enum class Batter { VANILLA, CHOCOLATE, ALMOND }
enum class Frosting { VANILLA, CHOCOLATE }
enum class Topping { SPRINKLES, CHOCOLATE_DRIZZLE, CARAMEL_DRIZZLE }
