package neetcode

class ProductOfArrayExceptSelf {
    fun productExceptSelf(nums: IntArray): IntArray {
        // failed attempt
//        val totalProduct = nums.fold(1) { acc, i -> acc * i }
//        println(totalProduct)
//        return buildList {
//            nums.forEach { currentNum ->
//                if(currentNum == 0) add(totalProduct) else add(totalProduct.div(currentNum).toInt())
//            }
//        }.toIntArray()
//        val result = IntArray(nums.size) { 0 }
//        for (i in nums.indices) {
//            var product = 1
//            for (j in nums.indices) {
//                if (i == j) {
//                    continue
//                }
//                product *= nums[j]
//            }
//            result[i] = product
//        }
//
//        return result

        val countOfZero = nums.count { it == 0 }
        val result = IntArray(nums.size) { 0 }
        if (countOfZero > 1) return result

        val totalNonZeroProduct = nums.filter { it != 0 }.fold(1) { acc, i -> acc * i }

        if (countOfZero == 1) {
            for (i in nums.indices) {
                if (nums[i] == 0) {
                    result[i] = totalNonZeroProduct
                } else {
                    result[i] = 0
                }
            }
        } else {
            for (i in nums.indices) {
                if (nums[i] == 0) {
                    result[i] = totalNonZeroProduct
                } else {
                    result[i] = totalNonZeroProduct / nums[i]
                }
            }
        }
        return result
    }
}

fun main(args: Array<String>) {
    val productOfArrayExceptSelf = ProductOfArrayExceptSelf()
    println(productOfArrayExceptSelf.productExceptSelf(intArrayOf(-1, 0, 1, 2, 3)).joinToString(","))
}