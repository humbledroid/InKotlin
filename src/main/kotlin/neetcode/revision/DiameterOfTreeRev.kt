package neetcode.revision

import neetcode.tree.TreeNode

class DiameterOfTreeRev {
    var res = 0
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        dfs(root)
        return res
    }

    fun dfs(root: TreeNode?) : Int{
        if(root == null) return 0

        val leftDepth = dfs(root.left)
        val rightDepth = dfs(root.right)

        res = maxOf(res, leftDepth + rightDepth)

        return 1 + maxOf(leftDepth, rightDepth)
    }
}