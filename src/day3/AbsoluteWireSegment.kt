package day3

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

typealias Point = Pair<Int, Int>

class AbsoluteWireSegment(
    private val prevAbsoluteWireSegment: AbsoluteWireSegment?,
    private val relativeWireSegment: RelativeWireSegment
) {
    private val orientation = relativeWireSegment.direction.orientation
    private val posCoord: Int
    private val firstCoord: Int
    private val lastCoord: Int

    init {
        if (prevAbsoluteWireSegment == null) {
            posCoord = 0
            firstCoord = 0
            lastCoord = relativeWireSegment.direction.multiplier * relativeWireSegment.length
        } else {
            // assume orientation is always different from previous
            posCoord = prevAbsoluteWireSegment.lastCoord
            firstCoord = prevAbsoluteWireSegment.posCoord
            lastCoord = prevAbsoluteWireSegment.posCoord + relativeWireSegment.getDirectionValue()
        }
    }

    private val minCoord = min(firstCoord, lastCoord)
    private val maxCoord = max(firstCoord, lastCoord)

    fun findIntersection(other: AbsoluteWireSegment): Point? {
        if (orientation == other.orientation) {
            return null
        }

        if ((other.minCoord < posCoord) && (posCoord < other.maxCoord) && (minCoord < other.posCoord) && (other.posCoord < maxCoord)) {
            return when (orientation) {
                LineOrientation.HORIZONTAL -> Point(
                    other.posCoord,
                    posCoord
                )
                LineOrientation.VERTICAL -> Point(
                    posCoord,
                    other.posCoord
                )
            }
        }

        return null
    }

    fun getPathLengthFrom(point: Point): Int {
        // assume point is on the line
        val pointCoord = when (orientation) {
            LineOrientation.HORIZONTAL -> point.first
            LineOrientation.VERTICAL -> point.second
        }
        var pathLength = abs(abs(pointCoord) - abs(firstCoord))

        var paws = prevAbsoluteWireSegment
        while (paws != null) {
            pathLength += paws.relativeWireSegment.length
            paws = paws.prevAbsoluteWireSegment
        }
        return pathLength
    }
}
