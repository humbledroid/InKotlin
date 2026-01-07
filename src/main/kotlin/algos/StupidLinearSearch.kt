package algos

class StupidLinearSearch(val inputArray: IntArray) {
    fun searchFor(input: Int): Int? {
        for (i in inputArray.indices) {
            if(inputArray[i] == input) {
                return i
            }
        }

        return null
    }
}


fun main() {
    val stupidLinearSearch = StupidLinearSearch(intArrayOf(1,3,5,6,10,12, 45, 53,245,23,23))

    val input = 45
    print("Found $input at ${stupidLinearSearch.searchFor(input)}")
}