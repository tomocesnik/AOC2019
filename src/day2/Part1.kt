package day2

import java.io.File

const val FILE_NAME = "src/day2/input.txt"

fun main() {
    println(executeProgram(readProgram(FILE_NAME), 12, 2))
}

fun readProgram(fileName: String) = File(fileName).readText().split(",").map { it.trim().toInt() }

fun executeProgram(program: List<Int>, param1: Int, param2: Int): Int {
    val rProg = replaceProgramFirstParams(program, param1, param2)
    var progCounter = 0
    while (progCounter < rProg.size) {
        val opCode = getOpCode(rProg[progCounter]) ?: break
        progCounter = opCode.execute(progCounter, rProg)
    }
    return rProg[0]
}

fun replaceProgramFirstParams(program: List<Int>, param1: Int, param2: Int): MutableList<Int> {
    val newProg = program.toMutableList()
    newProg[1] = param1
    newProg[2] = param2
    return newProg
}

fun getOpCode(code: Int) = OpCode.values().find { it.code == code }
