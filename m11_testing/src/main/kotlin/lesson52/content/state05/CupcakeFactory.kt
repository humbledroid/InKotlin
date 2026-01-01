package lesson52.content.state05

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

val rng = Random(0)

fun bakingFlow(count: Int) = flow {
    println("🥣 Baking $count cupcakes")
    simulateWork(1.seconds)
    val cupcakes = List(count) { Cupcake(it, Batter.entries.random(rng)) }
    cupcakes.forEach { emit(it) }
}

fun main(): Unit = runBlocking(Dispatchers.Default + CoroutineName("runBlocking")) {

    val bakedChannel = bakingFlow(12).produceIn(this + CoroutineName("Producer"))
    val decoratedChannel = Channel<Cupcake>(BUFFERED)
    val boxChannel = Channel<Box>(BUFFERED)

    val decorators = List(3) {
        launch(CoroutineName("Decorator #$it")) {
            Decorator(bakedChannel, decoratedChannel).start()
        }
    }

    launch(CoroutineName("Boxer")) { Boxer(decoratedChannel, boxChannel).start() }

    launch(CoroutineName("Decorators")) {
        decorators.joinAll()
        decoratedChannel.close()
    }

    for (box in boxChannel) {
        println("✅ $box")
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
                decoration = Decoration.entries.random(rng)
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

private fun simulateWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}

data class Cupcake(
    val id: Int,
    val batter: Batter,
    val frosting: Frosting? = null,
    val decoration: Decoration? = null
)

data class Box(val cupcake: Cupcake)

enum class Batter { VANILLA, CHOCOLATE, ALMOND }
enum class Frosting { VANILLA, CHOCOLATE }
enum class Decoration { SPRINKLES, CHOCOLATE_DRIZZLE, CARAMEL_DRIZZLE }
