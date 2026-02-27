package neetcode

class AddTwoNumbers {
    fun add(l1: ListNode?, l2: ListNode?, carry: Int): ListNode? {
        if(l1 == null && l2 == null && carry == 0) return null

        val value1 = l1?.`val` ?: 0
        val value2 = l2?.`val` ?: 0

        val sum = value1 + value2 + carry
        val newCarry = sum / 10
        val newNodeValue = sum % 10

        val nextNode = add(l1?.next, l2?.next, newCarry)

        return ListNode(newNodeValue).apply {
            next = nextNode
        }
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        return add(l1, l2, 0)
    }
}