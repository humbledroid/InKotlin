package learning.coroutines.foundations

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime


/* ****************************************************************************
   It's Pet Adoption Day, and the shelter has a dog who's ready to be adopted!

   Implement the `buildDogProfile()` function below, but don't change the code
   in any of the other functions. Inside `buildDogProfile()`...

   - Call `getShelterInfo()` and `getVetRecord()` in parallel.
   - Combine their results into a `DogProfile` object, and return that.

   By calling those two function in parallel, your code should complete in a
   little over 3 seconds.

   ************************************************************************* */

fun main() {
    val time = measureTime {
        runBlocking {
            val profile = buildDogProfile()
            println(profile)
        }
    }
    kotlin.io.println("The dog profile was created in $time")
}

suspend fun buildDogProfile(): DogProfile = coroutineScope {
    val shelterInfo = async {
        getShelterInfo()
    }

    val vetRecord = async {
        getVetRecord()
    }

    return@coroutineScope DogProfile(shelterInfo.await(), vetRecord.await())
}

suspend fun getShelterInfo(): Dog {
    kotlin.io.println("Fetching shelter data...")
    delay(3.seconds)
    return Dog(name = "Luna", breed = "Labrador")
}

suspend fun getVetRecord(): VetRecord {
    kotlin.io.println("Reading vet records...")
    delay(2.seconds)
    return VetRecord(vaccinated = true, microchipId = "A123456789")
}

data class Dog(val name: String, val breed: String)
data class VetRecord(val vaccinated: Boolean, val microchipId: String)
data class DogProfile(val dog: Dog, val record: VetRecord)
