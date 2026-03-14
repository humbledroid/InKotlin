package neetcode.revision.tree

import neetcode.tree.TreeNode

class KthSmallestIntegerRev {
    private var count = 0
    private var res = 0
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        count = k
        dfs(root)
        return res
    }

    fun dfs(node: TreeNode?) {
        if (node == null) return

        count--
        dfs(node.left)
        if(count == 0) {
            res = node.`val`
            return
        }
        dfs(node.left)
    }

}