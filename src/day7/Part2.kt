package day7

import day2.readProgram

fun main() {
    println(findMaxOutputSignalLoop(readProgram(FILE_NAME), 0))
}

fun findMaxOutputSignalLoop(program: List<Int>, initialInput: Int): Int? {
    val startInt = 5
    val phaseSettings = (startInt until NUM_AMPS + startInt).toMutableList()
    val perms = mutableListOf<List<Int>>()
    generatePermutations(phaseSettings.size, phaseSettings, perms)
    return perms.map { executeAllAmpProgramsInLoop(program, initialInput, it) }.max()
}

fun executeAllAmpProgramsInLoop(program: List<Int>, initialInput: Int, phaseSettings: List<Int>): Int {
    var inAndOut = mutableListOf<Int>(initialInput)
    val programs = (0 until NUM_AMPS).map { Program(program) }.toList()

    var iterations = 0
    while (!programs.last().halted) {
        programs.forEachIndexed { index, program ->
            if (iterations == 0) inAndOut.add(0, phaseSettings[index])
            inAndOut = program.execute(inAndOut)
        }
        iterations++
    }
    return inAndOut[0]
}
