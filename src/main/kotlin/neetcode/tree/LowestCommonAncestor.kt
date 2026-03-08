package neetcode.tree

class LowestCommonAncestor {
    fun lowestCommonAncestorNormal(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
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

    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }

        if(p!!.`val` > root.`val` && q!!.`val` > root.`val`) {
            return lowestCommonAncestor(root.right, p, q)
        }else if(p.`val` < root.`val` && q!!.`val` < root.`val`) {
            return lowestCommonAncestor(root.left, p, q)
        }
        return root
    }

    fun lowestCommonAncestorSeq(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null) {
            return null
        }
        return when {
            p!!.`val` > root.`val` && q!!.`val` > root.`val` -> lowestCommonAncestor(root.right, p, q)
            p.`val` < root.`val` && q!!.`val` < root.`val` -> lowestCommonAncestor(root.left, p, q)
            else -> root
        }
    }
}