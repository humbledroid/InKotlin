package learning.coroutines.patterns

import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {

    suspend fun WorkOrder.diagnose(): WorkOrder {
        println("$car - Diagnosing $symptom 🔍")
        delay(symptom.timeToDiagnose)

        val diagnosis = when (symptom) {
            Symptom.BURNING_SMELL   -> Diagnosis.OIL_LEAK
            Symptom.SQUEAKING_SOUND -> Diagnosis.WORN_BRAKE_PADS
            Symptom.WILL_NOT_START  -> Diagnosis.DEAD_BATTERY
        }
        println("$car - Diagnosis for $symptom is $diagnosis 🔍☑️")

        return copy(diagnosis = diagnosis, cost = cost + 150)
    }

    suspend fun WorkOrder.repair(): WorkOrder {
        val diagnosis = diagnosis ?: return this

        val cost = withContext(Dispatchers.IO) {
            println("$car - Fixing problem: $diagnosis 🔧")
            simulateBlockingWork(diagnosis.timeToRepair)

            when (diagnosis) {
                Diagnosis.OIL_LEAK        -> 125
                Diagnosis.WORN_BRAKE_PADS -> 750
                Diagnosis.DEAD_BATTERY    -> 250
            }
        }
        println("$car - Finished fixing problem: $diagnosis 🔧☑️")

        return copy(cost = this.cost + cost)
    }

    suspend fun process(order: WorkOrder): Int {
        return order.diagnose().repair().cost
    }

    runBlocking(Dispatchers.Default) {
        val job1 = async { process(WorkOrder("🚙", Symptom.SQUEAKING_SOUND)) }
        val job2 = async { process(WorkOrder("🚗", Symptom.WILL_NOT_START)) }
        val job3 = async { process(WorkOrder("🚚", Symptom.BURNING_SMELL)) }

        val total = job1.await() + job2.await() + job3.await()
        println($$"Total is $$${total}")
    }
}




private fun simulateBlockingWork(duration: Duration) {
    Thread.sleep(duration.inWholeMilliseconds)
}
