package neetcode.revision

import neetcode.ListNode

class RemoteNthNode {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val dummyHead = ListNode(0).apply {
            next = head
        }

        var left: ListNode? = dummyHead
        var right: ListNode? = head

        var count = n
        while(count != 0){
            right = right?.next
            count--
        }

        while(right!=null){
            left = left?.next
            right = right.next
        }

        left?.next = left?.next?.next
        return dummyHead.next
    }
}