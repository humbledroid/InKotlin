package neetcode.revision.tree

import neetcode.tree.TreeNode

class GoodNodesRev {
    fun goodNodes(root: TreeNode?): Int {
        if(root == null) return 0

        fun dfs(node: TreeNode?, maxVal: Int): Int {
            if(node == null) return 0
            var res = 0
            if(node.`val` >= maxVal){
                res = 1
            }
            val newMax = maxOf(maxVal, node.`val`)
            res += dfs(node.left, newMax)
            res += dfs(node.right, newMax)

            return res
        }

        return dfs(root, root.`val`)
    }

}