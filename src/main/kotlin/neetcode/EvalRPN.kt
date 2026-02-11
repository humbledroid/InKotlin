package neetcode

import java.util.*

class EvalRPN {
    fun evalRPN(tokens: Array<String>): Int {
        val stack = Stack<Int>()
        for (token in tokens) {
            when (token) {
                "+" -> {
                    val v1 = stack.pop()
                    val v2 = stack.pop()
                    stack.push(v2 + v1)
                }
                "-" -> {
                    val v1 = stack.pop()
                    val v2 = stack.pop()
                    stack.push(v2 - v1)
                }
                "*" -> {
                    val v1 = stack.pop()
                    val v2 = stack.pop()
                    stack.push(v2 * v1)
                }
                "/" -> {
                    val v1 = stack.pop()
                    val v2 = stack.pop()
                    stack.push(v2 / v1)
                }
                else -> stack.push(token.toInt())
            }
        }

        return stack.peek()
    }
}


fun main() {
    val evalRPN = EvalRPN()
    println(evalRPN.evalRPN(arrayOf("1","2","+","3","*","4","-")))
}