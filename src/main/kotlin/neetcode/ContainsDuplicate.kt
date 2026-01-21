package neetcode

class ContainsDuplicate {
    fun containsDuplicate(nums: IntArray): Boolean {
        // Bruteforce
//        for (i in nums.indices) {
//            for (j in i+1 until nums.size) {
//                if (nums[j] == nums[i]) return true
//            }
//        }
//        return false

    // Quick and witty
    //return nums.toHashSet().size != nums.size

        // a little performant, as whole set is not needed to be built
        val seen = mutableSetOf<Int>()
        for (num in nums) {
            if (num in seen) return true
            seen.add(num)
        }
        return false

    }
}
fun main() {
    val contains = ContainsDuplicate()
    println(contains.containsDuplicate(intArrayOf(1, 2, 3)))
}