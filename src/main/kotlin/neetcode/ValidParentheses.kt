package neetcode

import java.util.Stack

class ValidParentheses {
    fun isValid(s: String): Boolean {
        val stack = Stack<Char>()

        for (c in s) {
            if(stack.isEmpty()){
                stack.push(c)
            }
            else{
                when {
                    c == ']' && stack.peek() == '[' -> {
                        stack.pop()
                        continue
                    }

                    c == ')' && stack.peek() == '(' -> {
                        stack.pop()
                        continue
                    }

                    c == '}' && stack.peek() == '{' -> {
                        stack.pop()
                        continue
                    }
                }
                stack.push(c)
            }
        }


        return stack.isEmpty()
    }
}


fun main() {
    val s = ValidParentheses()
    println(s.isValid("()"))
}