package neetcode.revision.tree

import neetcode.tree.TreeNode

class SeDeTreeRev {
    fun serialize(root: TreeNode?): String {
        val result = mutableListOf<String>()

        fun dfs(node: TreeNode?) {
            if(node == null) {
                result.add("N")
                return
            }

            result.add(node.`val`.toString())
            dfs(node.left)
            dfs(node.right)
        }

        dfs(root)
        return result.joinToString(",")
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        val splitData = data.split(",")
        var i = 0

        fun dfs(): TreeNode? {
            if(splitData[i] == "N"){
                i++
                return null
            }

            val node = TreeNode(splitData[i].toInt())
            i++
            node.left = dfs()
            node.right = dfs()

            return node
        }


        return dfs()
    }
}