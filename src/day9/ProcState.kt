package day9

const val PROC_COUNTER_HALT = -1

typealias Memory = List<String>

data class ProcState(
    val procCounter: Int = 0,
    val memory: Memory,
    val relativeBase: Int = 0
) {
    val halted: Boolean
        get() = procCounter == PROC_COUNTER_HALT

    fun getValueAtProcCounter(offset: Int = 0) = memory[procCounter + offset]
}
