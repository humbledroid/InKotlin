package neetcode.tree

class LevelOrderBinaryTree {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        val queue = ArrayDeque<TreeNode>()
        if (root == null) return result

        queue.add(root)

//        while (!queue.isEmpty()) {
//            val node = queue.removeFirst()
//            if (node != null) {
//                println(node.`val`)
//                queue.addLast(node.left)
//                queue.addLast(node.right)
//            }
//        }

        while (!queue.isEmpty()) {
            val size = queue.size
            val tempList = mutableListOf<Int>()

            repeat(size) {
                val node = queue.removeFirst()
                tempList.add(node.`val`)
                node.left?.let { queue.add(it) }
                node.right?.let { queue.add(it) }
            }
            result.add(tempList)
        }

        return result
    }
}

fun main() {
    val levelOrderTree = LevelOrderBinaryTree()
    val root = TreeNode(1).apply {
        left = TreeNode(2).apply {
            left = TreeNode(4)
            right = TreeNode(5)
        }
        right = TreeNode(3).apply {
            left = TreeNode(6)
            right = TreeNode(7)
        }
    }
    println(levelOrderTree.levelOrder(root))
}