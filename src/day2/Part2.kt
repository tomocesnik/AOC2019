package day2

const val RESULT = 19690720
const val PARAM_MIN = 0
const val PARAM_MAX = 99

fun main() {
    println(findParamsForProgramResult(readProgram(FILE_NAME), RESULT, PARAM_MIN, PARAM_MAX))
}

fun findParamsForProgramResult(program: List<Int>, result: Int, paramMin: Int, paramMax: Int): Int {
    for (noun in paramMin..paramMax) {
        for (verb in paramMin..paramMax) {
            val progResult = executeProgram(program, noun, verb)
            if (progResult == result) {
                return 100 * noun + verb
            }
        }
    }
    return -1
}
