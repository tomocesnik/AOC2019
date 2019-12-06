package day6

import java.io.File

const val FILE_NAME = "src/day6/input.txt"

const val NODE_NAME_ROOT = "COM"

fun main() {
    println(countOrbits(readOrbits(FILE_NAME)))
}

fun readOrbits(fileName: String) = File(fileName).readLines().map { it.split(")") }.toList()

fun countOrbits(orbits: List<List<String>>): Int {
    val orbitsRoot = createOrbitsTree(orbits) ?: return -1
    return visitOrbitToCountOrbits(orbitsRoot, 0)
}

fun createOrbitsTree(orbits: List<List<String>>): OrbitNode? {
    val nodes = mutableMapOf<String, OrbitNode>()
    orbits.forEach {
        val orbitNode = nodes.computeIfAbsent(it[0]) { nodeName -> OrbitNode(nodeName) }
        val orbitChildNode = nodes.computeIfAbsent(it[1]) { nodeName -> OrbitNode(nodeName) }
        orbitNode.children.add(orbitChildNode)
    }
    return nodes[NODE_NAME_ROOT]
}

data class OrbitNode(val name: String) {
    val children = mutableListOf<OrbitNode>()
}

fun visitOrbitToCountOrbits(node: OrbitNode, level: Int): Int =
    node.children.map { visitOrbitToCountOrbits(it, level + 1) }.sum() + level
