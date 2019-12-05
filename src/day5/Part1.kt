package day5

import java.io.File

const val FILE_NAME = "src/day5/input.txt"

fun main() {
    println(runDiagnosticProgram(readProgram(FILE_NAME), 1))
}

fun readProgram(fileName: String) = File(fileName).readText().split(",").map { it.trim().toInt() }

fun runDiagnosticProgram(program: List<Int>, input: Int): Int {
    val outputs = executeProgram(program, listOf(input))
    return outputs.last()
}

fun executeProgram(program: List<Int>, inputs: List<Int>): MutableList<Int> {
    val outputs = mutableListOf<Int>()
    val rProg = program.toMutableList()
    var progCounter = 0
    while (progCounter < rProg.size) {
        val (opCode, parameterModes) = getOpCode(rProg[progCounter]) ?: break
        progCounter = opCode.execute(progCounter, rProg, parameterModes, inputs, outputs)
    }
    return outputs
}

const val OP_CODE_MAX_DIGITS = 5

fun getOpCode(code: Int): Pair<OpCode, List<ParameterMode>>? {
    val digits = code.toString().toCharArray().map { it.toString().toInt() }.toMutableList()
    val fillDigits = OP_CODE_MAX_DIGITS - digits.size
    (0 until fillDigits).forEach { _ -> digits.add(0, 0) }
    val parameterModes =
        (2 downTo 0).map { ParameterMode.values().find { pm -> pm.code == digits[it] } ?: return null }.toList()
    val code = (3 until digits.size).joinToString(separator = "") { digits[it].toString() }.toInt()
    val opCode = OpCode.values().find { it.code == code } ?: return null
    return Pair(opCode, parameterModes)
}

enum class ParameterMode(val code: Int) {
    POSITION(0) {
        override fun getData(parameter: Int, program: MutableList<Int>) = program[parameter]
    },
    IMMEDIATE(1) {
        override fun getData(parameter: Int, program: MutableList<Int>) = parameter
    };

    abstract fun getData(parameter: Int, program: MutableList<Int>): Int
}
