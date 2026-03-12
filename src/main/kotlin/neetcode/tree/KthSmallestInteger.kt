package neetcode.tree

class KthSmallestInteger {
    private var count = 0
    private var res = 0
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        count = k
        dfs(root)
        return res
    }

    fun dfs(node: TreeNode?) {
        if(node == null) return

        dfs(node.left)
        count--
        if(count == 0){
            res = node.`val`
            return
        }

        dfs(node.right)
    }
}