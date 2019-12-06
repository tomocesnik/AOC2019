package day6

const val NODE_NAME_YOU = "YOU"
const val NODE_NAME_SAN = "SAN"

fun main() {
    println(countOrbitalTransfers(readOrbits(FILE_NAME)))
}

fun countOrbitalTransfers(orbits: List<List<String>>): Int {
    val orbitsRoot = createOrbitsTree(orbits) ?: return -1
    return visitOrbitToCountOrbitalTransfers(orbitsRoot).first
}

fun visitOrbitToCountOrbitalTransfers(node: OrbitNode): Pair<Int, Boolean> {
    if ((node.name == NODE_NAME_YOU) || (node.name == NODE_NAME_SAN)) {
        return Pair(1, false)
    } else {
        val orbTransfers = node.children.map { visitOrbitToCountOrbitalTransfers(it) }

        val matched = orbTransfers.filter { it.second }
        if (matched.size == 1) return matched[0]

        val foundNodes = orbTransfers.filter { it.first > 0 }
        return when (foundNodes.size) {
            1 -> Pair(foundNodes[0].first + 1, false)
            2 -> Pair(foundNodes[0].first + foundNodes[1].first - 2, true)
            else -> Pair(0, false)
        }
    }
}
