package neetcode.tree

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class InvertTree {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null

        val queue: ArrayDeque<TreeNode?> = ArrayDeque()
        queue.add(root)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            node?.let {
                val temp = it.left
                it.left = it.right
                it.right = temp
                queue.add(it.left)
                queue.add(it.right)
            }
        }

        return root
    }

    fun invertTreeRec(root: TreeNode?): TreeNode? {
        if (root == null) return null
        return root.apply {
            left = invertTreeRec(right).also {
                right = invertTreeRec(left)
            }
        }
    }
}