package neetcode.tree

class LowestCommonAncestor {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        var current = root
        while(current != null){
            current = if(p!!.`val` > current.`val` && q!!.`val` > current.`val`){
                current.right
            } else if (p.`val` < current.`val` && q!!.`val` < current.`val`) {
                current.left
            } else {
                return current
            }
        }

        return null
    }
}