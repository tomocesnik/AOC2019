package day9

import java.io.File

const val FILE_NAME = "src/day9/input.txt"

const val NUM_AMPS = 5

fun main() {
    println(runBoostProgram(readProgram(FILE_NAME), "1"))
}

fun readProgram(fileName: String) = File(fileName).readText().trim().split(",")

fun runBoostProgram(progCode: List<String>, inputValue: String): String {
    val program = Program(progCode)
    val outputs = program.execute(listOf(inputValue))
    return outputs.last()
}
