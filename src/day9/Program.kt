package day9

const val OP_CODE_MAX_DIGITS = 5

class Program(progCode: List<String>) {
    private var procState = ProcState(memory = progCode)

    val halted: Boolean
        get() = procState.halted

    fun execute(inputs: List<String>): List<String> {
        var progInAndOut = ProgInAndOut(inputs)
        while (!halted) {
            val (opCode, parameterModes) = getOpCode(procState.getValueAtProcCounter()) ?: break
            val (ps, piao) = opCode.execute(procState, progInAndOut, parameterModes) ?: break
            procState = ps
            progInAndOut = piao
        }
        return progInAndOut.outputs
    }

    private fun getOpCode(code: String): Pair<OpCode, List<ParameterMode>>? {
        val digits = code.chunked(1).map { it.toInt() }.toMutableList()
        val fillDigits = OP_CODE_MAX_DIGITS - digits.size
        (0 until fillDigits).forEach { _ -> digits.add(0, 0) }

        val parameterModes =
            (2 downTo 0).map { ParameterMode.values().find { pm -> pm.code == digits[it] } ?: return null }.toList()

        val ccode = (3 until digits.size).joinToString(separator = "") { digits[it].toString() }.toInt()
        val opCode = OpCode.values().find { it.code == ccode } ?: return null
        return Pair(opCode, parameterModes)
    }
}
