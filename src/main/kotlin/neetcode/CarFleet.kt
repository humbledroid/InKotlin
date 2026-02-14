package neetcode

class CarFleet {
//    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
//        val remainingTimeArray = DoubleArray(target) { 0.toDouble() }
//        var fleetCount = 0
//
//        for (i in position.indices) {
//            val currentCar = position[i]
//            remainingTimeArray[currentCar] = (target - currentCar) / speed[i].toDouble()
//        }
//
//        var lastFleetCarTime = 0.toDouble()
//        for (i in remainingTimeArray.size - 1 downTo 0) {
//            val currenCarTime = remainingTimeArray[i]
//            if (currenCarTime > lastFleetCarTime) {
//                lastFleetCarTime = currenCarTime
//                fleetCount++
//            }
//        }
//
//        return fleetCount
//    }

    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {

        val pair = position.zip(speed).sortedByDescending { it.first }
        println(pair)

        val stack = mutableListOf<Double>()

        for((p, s) in pair) {
            val remainingTime = (target - p)/s.toDouble()
            stack.add(remainingTime)
            if(stack.size >= 2 && stack[stack.size - 1] <= stack[stack.size - 2]) {
                stack.removeAt(stack.size - 1)
            }

        }

        return stack.size

    }
}

fun main(){
    val carFleet = CarFleet()
    println(carFleet.carFleet(target = 10, position = intArrayOf(4,1,0,7), speed = intArrayOf(2,2,1,1)))
}