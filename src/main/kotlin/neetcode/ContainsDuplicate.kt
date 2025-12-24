package neetcode

class ContainsDuplicate {
    fun containsDuplicate(nums: IntArray): Boolean {
        val hashSet = HashSet<Int>()
        nums.forEach {
            if(hashSet.contains(it)){
                return false
            }
            hashSet.add(it)
        }
        return true
    }
}