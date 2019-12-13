package day13

import day9.Program
import day9.readProgram

fun main() {
    println(getWinningScore(readProgram(FILE_NAME)))
}

fun getWinningScore(progCode: List<String>): Int {
    val modProgCode = progCode.toMutableList()
    modProgCode[0] = "2"
    val program = Program(modProgCode)

    val arcadeDisplay = ArcadeDisplay()
    var outputs = program.execute()
    var tiles = outputs.map { it.toInt() }.chunked(3)
    arcadeDisplay.update(tiles)
    var paddleX = (tiles.find { it.last() == 3 } ?: listOf(0, 0, 3))[0]
    var ballX = (tiles.find { it.last() == 4 } ?: listOf(0, 0, 4))[0]

    while (!program.halted) {
        val input = (ballX - paddleX).coerceIn(-1, 1).toString()
        outputs = program.execute(listOf(input))
        tiles = outputs.map { it.toInt() }.chunked(3)
        arcadeDisplay.update(tiles)
        paddleX = (tiles.find { it.last() == 3 } ?: listOf(paddleX, 0, 3))[0]
        ballX = (tiles.find { it.last() == 4 } ?: listOf(ballX, 0, 4))[0]
    }

    return arcadeDisplay.segmentedDisplay
}
