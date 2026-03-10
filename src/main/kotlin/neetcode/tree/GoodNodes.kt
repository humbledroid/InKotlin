package neetcode.tree

class GoodNodes {
    fun goodNodes(root: TreeNode?): Int {
        if(root == null) return 0

        fun dfs(root: TreeNode?, maxVal: Int): Int {
            if(root == null) return 0

            var res = 0
            if(root.`val` >= maxVal){
                res = 1
            }

            val newMax = maxOf(maxVal, root.`val`)
            res += dfs(root.left, newMax)
            res += dfs(root.right, newMax)

            return res
        }

        return dfs(root, root.`val`)
    }
}