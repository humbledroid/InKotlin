package lesson46.content.state04

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.joinAll
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
    private val seed = Random(0)

    suspend fun start() {
        try {
            for (count in inbound) {
                println("🥣 Baking $count cupcakes")
                simulateWork(1.seconds)
                val cupcakes = List(count) { Cupcake(it, Batter.entries.random(seed)) }
                cupcakes.forEach { outbound.send(it) }
            }
        } finally {
            outbound.close()
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
        try {
            for (cupcake in inbound) {
                println("🎁 Boxing $cupcake")
                simulateWork(250.milliseconds)
                val box = Box(cupcake)
                outbound.send(box)
            }
        } finally {
            outbound.close()
        }
    }
}

fun main() = runBlocking<Unit>(Dispatchers.Default) {
    val countChannel = Channel<Int>(BUFFERED)
    val bakedChannel = Channel<Cupcake>(BUFFERED)
    val decoratedChannel = Channel<Cupcake>(BUFFERED)
    val boxChannel = Channel<Box>(BUFFERED)

    launch { Baker(countChannel, bakedChannel).start() }
    val decorators = List(3) { launch { Decorator(bakedChannel, decoratedChannel).start() } }
    launch { Boxer(decoratedChannel, boxChannel).start() }

    launch {
        decorators.joinAll()
        decoratedChannel.close()
    }

    countChannel.send(12)
    countChannel.close()

    val flow = boxChannel.receiveAsFlow()

    launch {
        val chocolateCount = flow.count { it.cupcake.frosting == CHOCOLATE }
        println("We made $chocolateCount cupcakes with chocolate frosting!")
    }
    launch {
        val vanillaCount = flow.count { it.cupcake.frosting == VANILLA }
        println("We made $vanillaCount cupcakes with vanilla frosting!")
    }
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
