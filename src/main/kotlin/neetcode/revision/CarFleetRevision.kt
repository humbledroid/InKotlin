package neetcode.revision

import java.util.*

class CarFleetRevision {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val stack = Stack<Double>()

        val pairSortedByDistanceTravelled = position.zip(speed).sortedByDescending { it.first }

        for ((p, s) in pairSortedByDistanceTravelled) {
            val remainingTimeToTarget = (target - p) / s.toDouble()
            stack.push(remainingTimeToTarget)
            if (stack.size >= 2 && stack[stack.size - 1] <= stack[stack.size - 2]) {
                stack.pop()
            }
        }
        return stack.size
    }

    fun carFleetWithoutStack(target: Int, position: IntArray, speed: IntArray): Int {
        var fleetCount = 0
        val remainingTimeForEachCar = DoubleArray(target) { 0.toDouble() }

        for(i in position.indices) {
            val currentCar = position[i]
            remainingTimeForEachCar[currentCar] = (target - currentCar) / speed[i].toDouble()
        }

        var lastFleetCarTime = 0.toDouble()
        for(i in remainingTimeForEachCar.size - 1 downTo 0) {
            if(remainingTimeForEachCar[i] > lastFleetCarTime) {
                lastFleetCarTime = remainingTimeForEachCar[i]
                fleetCount++
            }
        }

        return fleetCount
    }
}

fun main() {
    val carFleetRevision = CarFleetRevision()
    println(carFleetRevision.carFleetWithoutStack(target = 10, position = intArrayOf(4, 1, 0, 7), speed = intArrayOf(2, 2, 1, 1)))
}