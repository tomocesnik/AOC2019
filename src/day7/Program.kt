package day7

import day5.getOpCode

class Program(code: List<Int>) {
    private val mProg = code.toMutableList()
    private var progCounter = 0

    val halted: Boolean
        get() = progCounter >= mProg.size

    fun execute(inputs: List<Int>): MutableList<Int> {
        val outputs = mutableListOf<Int>()
        val mInputs = inputs.toMutableList()
        while (!halted) {
            val (opCode, parameterModes) = getOpCode(mProg[progCounter]) ?: break
            progCounter = opCode.execute(progCounter, mProg, parameterModes, mInputs, outputs) ?: break
        }
        return outputs
    }
}
