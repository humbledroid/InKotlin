package neetcode.tree

class DiameterOfTree {
    private var res = 0
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        dfs(root)
        return res
    }

    fun dfs(root: TreeNode?): Int {
        if(root == null) return 0

        val left = dfs(root.left)
        val right = dfs(root.right)

        res = maxOf(res, left+right)

        return 1 + maxOf(left, right)
    }
}