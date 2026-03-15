package neetcode.tree

class MaxPathSum {
    private var result = Int.MIN_VALUE

    fun maxPathSum(root: TreeNode?): Int {
        dfs(root)
        return result
    }

    private fun dfs(node: TreeNode?): Int {
        if(node == null){
            return 0
        }

        val leftMax = maxOf(dfs(node.left), 0)
        val rightMax = maxOf(dfs(node.right), 0)

        result = maxOf(result, node.`val` + leftMax+rightMax)

        return node.`val` + maxOf(leftMax, rightMax)
    }
}