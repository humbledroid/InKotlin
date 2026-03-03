package neetcode.revision.tree

import neetcode.tree.TreeNode

class InvertTreeRev {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root?.left == null && root?.right == null) return root

        val deque = ArrayDeque<TreeNode>()
        deque.add(root)
        while (deque.isNotEmpty()) {
            val currentNode = deque.first()

            if (currentNode.left != null) deque.add(currentNode.left!!)
            if (currentNode.right != null) deque.add(currentNode.right!!)

            val tmp = currentNode.left
            currentNode.left = currentNode.right
            currentNode.right = tmp

            deque.removeFirst()
        }

        return root
    }

    fun invertTreeRec(root: TreeNode?): TreeNode? {
        if (root == null) return null
        return root.apply { left = invertTreeRec(right).also { right = invertTreeRec(root.left) } }
    }

    fun invertTreeRecEasy(root: TreeNode?): TreeNode? {
        if (root == null) return null

        val temp = root.left
        root.left = root.right
        root.right = temp

        invertTreeRecEasy(root.left)
        invertTreeRecEasy(root.right)

        return root
    }


}