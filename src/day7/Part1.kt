package day7

import day2.readProgram
import day5.executeProgram

const val FILE_NAME = "src/day7/input.txt"

const val NUM_AMPS = 5

fun main() {
    println(findMaxOutputSignal(readProgram(FILE_NAME), 0))
}

fun findMaxOutputSignal(program: List<Int>, initialInput: Int): Int? {
    val phaseSettings = (0 until NUM_AMPS).toMutableList()
    val perms = mutableListOf<List<Int>>()
    generatePermutations(phaseSettings.size, phaseSettings, perms)
    return perms.map { executeAllAmpPrograms(program, initialInput, it) }.max()
}

fun generatePermutations(k: Int, items: MutableList<Int>, perms: MutableList<List<Int>>) {
    if (k == 1) perms.add(items.toList())
    else {
        // generate permutations with kth unaltered
        generatePermutations(k - 1, items, perms)

        // generate permutations for kth swapped with each k-1 initial
        (0 until k - 1).forEach {
            // swap choice dependent on parity of k (even or odd)
            if (k % 2 == 0) {
                val tmp = items[it]
                items[it] = items[k - 1]
                items[k - 1] = tmp
            } else {
                val tmp = items[0]
                items[0] = items[k - 1]
                items[k - 1] = tmp
            }
            generatePermutations(k - 1, items, perms)
        }
    }
}

fun executeAllAmpPrograms(program: List<Int>, initialInput: Int, phaseSettings: List<Int>): Int {
    var inputSignal = initialInput
    (0 until NUM_AMPS).forEach {
        inputSignal = executeAmpProgram(program, phaseSettings[it], inputSignal)
    }
    return inputSignal
}

fun executeAmpProgram(program: List<Int>, phaseSetting: Int, inputSignal: Int) =
    executeProgram(program, listOf(phaseSetting, inputSignal))[0]
