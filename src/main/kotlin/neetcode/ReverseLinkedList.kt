package neetcode

class ReverseLinkedList {
    fun reverseList(head: ListNode?): ListNode? {
        var prev:ListNode? = null
        var currentNode = head

        while(currentNode != null) {
            val temp = currentNode.next
            currentNode.next = prev
            prev = currentNode
            currentNode = temp
        }

        return prev
    }
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}