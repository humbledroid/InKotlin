package graphs

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max


class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}


class LevelOrderBFS {

    val result = mutableListOf<ArrayList<Int>>()
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val resultList = mutableListOf<List<Int>>()
        if(root == null){
            return resultList
        }

        val queue: Queue<TreeNode> = LinkedList()
        queue.offer(root)

        while(queue.isNotEmpty()){
            val sizeOfNextLevel = queue.size
            val currentLevelList = ArrayList<Int>(sizeOfNextLevel)
            for (i in 0 until sizeOfNextLevel) {
                val currentNode = queue.poll()
                if(currentNode.left!=null){
                    queue.offer(currentNode.left)
                }
                if(currentNode.right!=null){
                    queue.offer(currentNode.right)
                }
                currentLevelList.add(currentNode.`val`)
            }
            resultList.add(currentLevelList)
        }

        return resultList.toList()
    }

    fun treeRunner(node: TreeNode, level: Int){
        if(result.size == level){
            result.add(ArrayList())
        }
        result[level].add(node.`val`)

        if(node.left!=null){
            treeRunner(node.left!!, level+1)
        }
        if(node.right!=null){
            treeRunner(node.right!!, level+1)
        }
    }

    fun leveOrderRecursive(root: TreeNode?): List<List<Int>>{
        if(root == null){
            return result
        }
        treeRunner(root, 0)

        return result
    }

    fun averageOfLevels(root: TreeNode?): DoubleArray {
        val result = mutableListOf<Double>()
        if(root == null){
            return result.toDoubleArray()
        }

        val queue: Queue<TreeNode> = LinkedList()
        queue.offer(root)
        while(queue.isNotEmpty()) {
            val sizeOfNextLevel = queue.size
            var sum: Double = 0.toDouble()
            for (i in 0 until sizeOfNextLevel) {
                val currentNode = queue.poll()
                if (currentNode.left != null) {
                    queue.offer(currentNode.left)
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right)
                }
                sum += currentNode.`val`
            }
            result.add(sum/sizeOfNextLevel.toDouble())
        }
        return result.toDoubleArray()
    }

    fun isSymmetric(root: TreeNode?): Boolean {
        val queue: Queue<TreeNode> = LinkedList()
        queue.add(root?.left)
        queue.add(root?.right)

        while (queue.isNotEmpty()){
            val leftNode = queue.poll()
            val rightNode = queue.poll()
            if(leftNode==null && rightNode==null){
                continue
            }
            if(leftNode==null || rightNode==null){
                return false
            }

            if(leftNode.`val` != rightNode.`val`){
                return false
            }
            queue.add(leftNode.left)
            queue.add(rightNode.right)
            queue.add(leftNode.right)
            queue.add(rightNode.left)
        }
        return true
    }

    fun lengthOfLongestSubstring(s: String): Int {
        var result = 0
        val n = s.length
        val mp = mutableMapOf<Char, Int>()
        var i = 0

        for (j in 0 until n){
            if(mp.containsKey(s[j])){
                i = max(mp[s[j]] ?: 0, i)
            }
            result = max(result, j-i+1)
            mp[s[j]] = j+1
        }
        return result
    }
}

fun main(){
//    val levelOrderBFS = LevelOrderBFS()
//    // [2147483647,2147483647,2147483647]
//    val treeNode = TreeNode(`val` = 2147483647).apply {
//        left = TreeNode(`val` = 2147483647)
//        right = TreeNode(`val` = 2147483647)
//    }
//    val result = levelOrderBFS.averageOfLevels(treeNode)
//    result.forEach {
//        println(it)
//    }

    val l = LevelOrderBFS()
    l.lengthOfLongestSubstring("abcabcbb")
}