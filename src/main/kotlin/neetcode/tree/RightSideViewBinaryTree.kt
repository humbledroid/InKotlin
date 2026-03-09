package neetcode.tree

import neetcode.revision.tree.LevelOrderBinaryTreeRev

class RightSideViewBinaryTree {
    fun rightSideView(root: TreeNode?): List<Int> {
        val res = mutableListOf<Int>()

        fun dfs(root: TreeNode?, depth: Int) {
            if(root == null) return

            if (res.size == depth) {
                res.add(root.`val`)
            }
            dfs(root.right, depth + 1)
            dfs(root.left, depth + 1)
        }

        dfs(root, 0)

        return res
    }
}

fun main() {
    val levelOrderTree = RightSideViewBinaryTree()
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
    println(levelOrderTree.rightSideView(root))
}

