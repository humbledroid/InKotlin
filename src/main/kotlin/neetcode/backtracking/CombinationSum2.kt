package neetcode.backtracking

class CombinationSum2 {
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        val result = HashSet<List<Int>>()
        candidates.sort()

        fun generateSubset(i: Int, current: MutableList<Int>, total: Int) {
            if(total == target){
                result.add(ArrayList(current))
                return
            }
            if (total > target || i == candidates.size) {
                return
            }

            current.add(candidates[i])
            generateSubset(i + 1, current, total + candidates[i])
            current.removeAt(current.size - 1)

            generateSubset(i + 1, current, total)
        }
        generateSubset(0, mutableListOf(), 0)
        return result.toList()
    }
}