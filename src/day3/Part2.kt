package day3

fun main() {
    println(findShortestIntersection(readWirePaths(FILE_NAME)))
}

fun findShortestIntersection(wirePaths: List<WirePath>): Int? {
    val intersections = mutableListOf<Triple<Point, AbsoluteWireSegment, AbsoluteWireSegment>>()
    wirePaths[0].absoluteWireSegments.forEach {
        wirePaths[1].absoluteWireSegments.forEach { ws ->
            ws.findIntersection(it)?.let { p -> intersections.add(Triple(p, ws, it)) }
        }
    }
    return intersections.map { it.second.getPathLengthFrom(it.first) + it.third.getPathLengthFrom(it.first) }.min()
}
