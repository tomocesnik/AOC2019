package day12

import java.io.File
import kotlin.math.absoluteValue

const val FILE_NAME = "src/day12/input.txt"

const val TIME_STEPS = 1000

typealias Position = Triple<Int, Int, Int>
typealias Velocity = Triple<Int, Int, Int>

fun main() {
    println(calculateTotalSystemEnergy(readMoonPositions(FILE_NAME)))
}

fun readMoonPositions(fileName: String) = File(fileName).readLines().map {
    it.split(",").map { cs -> cs.split("=").last().replace(">", "").trim().toInt() }.toList()
}.map { Position(it[0], it[1], it[2]) }.toList()

fun calculateTotalSystemEnergy(startPositions: List<Position>): Int {
    val (positions, velocities) = moveMoons(startPositions)
    return positions.zip(velocities).map { calcPotentialEnergy(it.first) * calcKineticEnergy(it.second) }.sum()
}

fun moveMoons(startPositions: List<Position>): Pair<List<Position>, List<Velocity>> {
    var positions = startPositions
    var velocities = List(positions.size) { Velocity(0, 0, 0) }

    (0 until TIME_STEPS).forEach { _ ->
        velocities = updateVelocities(velocities, positions)
        positions = updatePositions(positions, velocities)
    }
    return Pair(positions, velocities)
}

fun calcPotentialEnergy(position: Position) = position.toList().map { it.absoluteValue }.sum()

fun calcKineticEnergy(velocity: Velocity) = velocity.toList().map { it.absoluteValue }.sum()

fun updateVelocities(prevVelocities: List<Velocity>, positions: List<Position>): List<Velocity> {
    val velocities = prevVelocities.toMutableList()
    positions.indices.forEach { i ->
        (i until positions.size).forEach { j ->
            val diffs =
                positions[i].toList().zip(positions[j].toList()).map { it.first.compareTo(it.second).coerceIn(-1, 1) }
            val newVelI = velocities[i].toList().zip(diffs).map { it.first - it.second }
            velocities[i] = Velocity(newVelI[0], newVelI[1], newVelI[2])
            val newVelJ = velocities[j].toList().zip(diffs).map { it.first + it.second }
            velocities[j] = Velocity(newVelJ[0], newVelJ[1], newVelJ[2])
        }
    }
    return velocities.toList()
}

fun updatePositions(prevPositions: List<Position>, velocities: List<Velocity>) =
    prevPositions.zip(velocities).map { it.first.applyVelocity(it.second) }

fun Position.applyVelocity(velocity: Velocity): Position {
    return Position(this.first + velocity.first, this.second + velocity.second, this.third + velocity.third)
}
