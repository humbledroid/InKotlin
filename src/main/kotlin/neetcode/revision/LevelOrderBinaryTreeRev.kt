package neetcode.revision

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

    fun levelOrderRec(root: TreeNode?): List<List<Int>> {
        val res = mutableListOf<MutableList<Int>>()

        fun dfs(root: TreeNode?, depth: Int) {
            if(root == null) return

            if (res.size == depth) {
                res.add(mutableListOf())
            }

            res[depth].add(root.`val`)
            dfs(root.left, depth + 1)
            dfs(root.right, depth + 1)
        }

        dfs(root, 0)

        return res
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
    println(levelOrderTree.levelOrderRec(root))
}