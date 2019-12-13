package day13

import day9.Program
import day9.readProgram

const val FILE_NAME = "src/day13/input.txt"

fun main() {
    println(countBlockTiles(readProgram(FILE_NAME)))
}

fun countBlockTiles(progCode: List<String>): Int {
    val program = Program(progCode)
    val outputs = program.execute()
    val tiles = outputs.map { it.toInt() }.chunked(3)
    val arcadeDisplay = ArcadeDisplay()
    arcadeDisplay.update(tiles)
    return tiles.filter { it.last() == 2 }.count()
}
