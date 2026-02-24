package neetcode

class ReOrderList {
    fun reorderList(head: ListNode?): Unit {
        if(head == null) return

        val nodes = mutableListOf<ListNode>()

        var currentNode: ListNode? = head

        while(currentNode != null) {
            nodes.add(currentNode)
            currentNode = currentNode.next
        }

        var i = 0
        var j = nodes.size - 1

        while(i < j) {
            nodes[i].next = nodes[j]
            i++
            if(i >= j)  break
            nodes[j].next = nodes[i]
            j--
        }

        nodes[i].next = null
    }

    fun reorderListReverseMerge(head: ListNode?): Unit {
        if(head?.next == null) return

        var slow: ListNode? = head
        var fast: ListNode? = head.next

        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        /**
         * by this time, slow will be at the half and fast will be at the end
         */

        val second = slow?.next
        slow?.next = null

        var previous: ListNode? = null

        var currentNode = second

        while(currentNode != null) {
            val tempNode = currentNode.next
            currentNode.next = previous
            previous = currentNode
            currentNode = tempNode
        }

        var first: ListNode? = head
        var secondList: ListNode? = previous

        while(first != null && secondList != null) {
            val tempNode = first.next
            val tempNode2 = secondList.next
            first.next = secondList
            secondList.next = tempNode
            first = tempNode
            secondList = tempNode2
        }
    }
}

