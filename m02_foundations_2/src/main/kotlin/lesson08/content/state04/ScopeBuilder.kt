package lesson08.content.state04

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    val configuration = getConfiguration()
    println(configuration)
}

suspend fun getConfiguration(): Configuration = coroutineScope {
    val remote = async { getRemoteConfiguration() }
    val local = async { readLocalConfigurationFromDatabase() }
    return@coroutineScope local.await().mergeWith(remote.await())
}

suspend fun getRemoteConfiguration(): Configuration {
    println("Simulating remote call")
    delay(3.seconds)
    return Configuration("red", null, 3)
}

suspend fun readLocalConfigurationFromDatabase(): Configuration {
    println("Simulating local database access")
    delay(1.seconds)
    return Configuration("blue", "square", null)
}

data class Configuration(val color: String?, val shape: String?, val quantity: Int?) {
    fun mergeWith(other: Configuration) = Configuration(
        this.color ?: other.color,
        this.shape ?: other.shape,
        this.quantity ?: other.quantity
    )
}
