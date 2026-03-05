package neetcode.tree

class BalancedBinaryTree {
    fun isBalanced(root: TreeNode?): Boolean {
        return dfs(root).first
    }

    private fun dfs(root: TreeNode?): Pair<Boolean, Int> {
        if(root == null) return true to 0

        val left = dfs(root.left)
        val right = dfs(root.right)

        val balanced = left.first && right.first && Math.abs(left.second - right.second) <= 1

        return balanced to 1 + maxOf(left.second, right.second)
    }
}