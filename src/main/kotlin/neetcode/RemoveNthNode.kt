package neetcode

class RemoveNthNode {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val dummy = ListNode(0).apply {
            next = head
        }

        var left: ListNode? = dummy
        var right: ListNode? = head


        var count = n

        while(count != 0) {
            right = right?.next
            count--
        }

        while (right != null) {
            left = left?.next
            right = right.next
        }


        left?.next = left?.next?.next
        return dummy.next
    }
}