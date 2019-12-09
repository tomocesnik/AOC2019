package day9

enum class ParameterMode(val code: Int) {
    POSITION(0) {
        override fun readData(procState: ProcState, parameter: String) = procState.memory[parameter.toInt()]

        override fun writeData(data: String, procState: ProcState, parameter: String) =
            writeDataAtIndex(parameter.toInt(), data, procState)
    },
    IMMEDIATE(1) {
        override fun readData(procState: ProcState, parameter: String) = parameter

        // no write
        override fun writeData(data: String, procState: ProcState, parameter: String) = procState.memory
    },
    RELATIVE(2) {
        override fun readData(procState: ProcState, parameter: String) =
            procState.memory[procState.relativeBase + parameter.toInt()]

        override fun writeData(data: String, procState: ProcState, parameter: String) =
            writeDataAtIndex(procState.relativeBase + parameter.toInt(), data, procState)
    };

    abstract fun readData(procState: ProcState, parameter: String): String

    abstract fun writeData(data: String, procState: ProcState, parameter: String): Memory

    protected fun writeDataAtIndex(writeIndex: Int, data: String, procState: ProcState): Memory {
        val newMem = ensureMem(procState.memory, writeIndex)
        newMem[writeIndex] = data
        return newMem
    }

    private fun ensureMem(memory: Memory, maxIndex: Int): MutableList<String> {
        val newMem = memory.toMutableList()
        while (maxIndex >= newMem.size) {
            newMem.add("0")
        }
        return newMem
    }
}
