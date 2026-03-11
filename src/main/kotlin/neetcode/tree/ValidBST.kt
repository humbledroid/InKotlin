package neetcode.tree

class ValidBST {
    fun isValidBST(root: TreeNode?): Boolean {
        fun dfs(node: TreeNode?, min: Long, max: Long): Boolean {
            if (node == null) return true
            if (node.`val` !in (min + 1)..<max) return false
            return dfs(node.left, min, node.`val`.toLong()) &&
                    dfs(node.right, node.`val`.toLong(), max)
        }

        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE)
    }
}