package neetcode

import java.util.*

class MinStack() {
    private val stack = Stack<Pair<Int, Int>>()

    fun push(`val`: Int) {
        if(stack.isEmpty()) {
            stack.push(`val` to `val`)
        }else{
            val currentMin = minOf(`val`, stack.peek().second)
            stack.push(`val` to currentMin)
        }
    }

    fun pop() {
        stack.pop()
    }

    fun top(): Int {
        return stack.peek().first
    }

    fun getMin(): Int {
        return stack.peek().second
    }
}