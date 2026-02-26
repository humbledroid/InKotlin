package neetcode

class Node(var `val`: Int) {
    var next: Node? = null
    var random: Node? = null
}

class CopyLLWithRandomPointer {
    private val map = mutableMapOf<Node, Node>()
    fun copyRandomList(head: Node?): Node? {
        if (head == null) return null

        if(map.containsKey(head)) return map[head]!!

        val copy = Node(head.`val`)

        map[head] = copy
        copy.next = copyRandomList(head.next)
        copy.random = map[head.random]
        return copy
    }

    fun copyRandomListOptimized(head: Node?): Node? {
        if(head == null) return null

        var l1: Node? = head
        while (l1 != null) {
            val l2 = Node(l1.`val`)
            l2.next = l1.next
            l1.next = l2
            l1 = l2.next
        }

        val newHead = head.next

        l1 = head
        while (l1 != null) {
            if (l1.random != null) {
                l1.next?.random = l1.random?.next
            }
            l1 = l1.next?.next
        }

        l1 = head
        while (l1 != null) {
            val l2 = l1.next
            l1.next = l2?.next
            val nextL2 = l2?.next
            if (nextL2 != null) {
                l2.next = nextL2.next
            }
            l1 = l1.next
        }

        return newHead
    }
}


