package neetcode.tree

class ConstructTree {
    private var preOrderIndex = 0
    private var inOrderIndex = 0

    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {

        fun dfs(limit: Int): TreeNode? {
            if(preOrderIndex >= preorder.size){
                return null
            }

            if(inorder[inOrderIndex] == limit) {
                inOrderIndex++
                return null
            }

            val root = TreeNode(preorder[preOrderIndex])
            preOrderIndex++

            root.left = dfs(root.`val`)
            root.right = dfs(limit)

            return root
        }

        return dfs(Int.MAX_VALUE)
    }
}