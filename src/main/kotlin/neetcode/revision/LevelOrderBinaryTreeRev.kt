package neetcode.revision

import neetcode.tree.LevelOrderBinaryTree
import neetcode.tree.TreeNode

class LevelOrderBinaryTreeRev {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        if (root == null) return result

        val queue = ArrayDeque<TreeNode>()

        queue.add(root)

        while (queue.isNotEmpty()) {
            val levelSize = queue.size
            val levelList = mutableListOf<Int>()

            repeat(levelSize) {
                val node = queue.removeFirst()
                levelList.add(node.`val`)

                node.left?.let { queue.add(it) }
                node.right?.let { queue.add(it) }
            }
            result.add(levelList)
        }

        return result
    }
}


fun main() {
    val levelOrderTree = LevelOrderBinaryTreeRev()
    val root = TreeNode(1).apply {
        left = TreeNode(2).apply {
            left = TreeNode(4)
            right = TreeNode(5)
        }
        right = TreeNode(3).apply {
            left = TreeNode(6)
            right = TreeNode(7)
        }
    }
    println(levelOrderTree.levelOrder(root))
}