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
}

