package day3

import java.io.File
import kotlin.math.abs

const val FILE_NAME = "src/day3/input.txt"

fun main() {
    println(findClosestIntersection(readWirePaths(FILE_NAME)))
}

fun readWirePaths(fileName: String) =
    File(fileName).readLines().map {
        it.split(",").map { ds -> RelativeWireSegment(ds) }.toList()
    }.map { WirePath(it) }.toList()

fun findClosestIntersection(wirePaths: List<WirePath>): Int? {
    val intersections = mutableListOf<Point>()
    wirePaths[0].absoluteWireSegments.forEach {
        wirePaths[1].absoluteWireSegments.forEach { ws ->
            ws.findIntersection(it)?.let { p -> intersections.add(p) }
        }
    }
    return intersections.map { abs(it.first) + abs(it.second) }.min()
}

enum class Direction(val orientation: LineOrientation, val multiplier: Int) {
    U(LineOrientation.VERTICAL, 1),
    D(LineOrientation.VERTICAL, -1),
    R(LineOrientation.HORIZONTAL, 1),
    L(LineOrientation.HORIZONTAL, -1)
}

enum class LineOrientation {
    VERTICAL,
    HORIZONTAL
}

class RelativeWireSegment(dirString: String) {
    val direction = Direction.valueOf(dirString[0].toString())
    val length = dirString.substring(1).toInt()

    fun getDirectionValue() = direction.multiplier * length
}

class WirePath(relativeWireSegments: List<RelativeWireSegment>) {
    val absoluteWireSegments = mapAbsoluteWireSegments(relativeWireSegments)

    private fun mapAbsoluteWireSegments(relativeWireSegments: List<RelativeWireSegment>): List<AbsoluteWireSegment> {
        var prevAbsoluteWireSegment: AbsoluteWireSegment? = null
        val absoluteWireSegments = mutableListOf<AbsoluteWireSegment>()
        for (relativeWireSegment in relativeWireSegments) {
            prevAbsoluteWireSegment = AbsoluteWireSegment(prevAbsoluteWireSegment, relativeWireSegment)
            absoluteWireSegments.add(prevAbsoluteWireSegment)
        }
        return absoluteWireSegments.toList()
    }
}
