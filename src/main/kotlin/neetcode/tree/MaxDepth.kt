package neetcode.tree

import jdk.internal.vm.ThreadContainers.root

class MaxDepth {
    // fun maxDepth(root: TreeNode?): Int {
    //     if(root == null) return 0
    //     return 1 + maxOf(maxDepth(root.left), maxDepth(root.right))
    // }

    fun maxDepthRec(root: TreeNode?): Int {
        if (root == null) return 0

        val leftDepth = maxDepthRec(root.left)
        val rightDepth = maxDepthRec(root.right)

        return 1 + maxOf(leftDepth, rightDepth)
    }

    fun maxDepth(root: TreeNode?): Int {
        var level = 0

        if(root == null) return level

        val q = ArrayDeque<TreeNode?>()
        q.addLast(root)

        while(q.isNotEmpty()){
            val size = q.size

            repeat(size){
                val node = q.removeFirst()!!

                node.left?.let {
                    q.addLast(it)
                }

                node.right?.let {
                    q.addLast(it)
                }
            }

            level++
        }

        return level
    }
}