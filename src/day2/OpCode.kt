package day2

enum class OpCode(val code: Int) {
    ADD(1) {
        override fun execute(progCounter: Int, program: MutableList<Int>) =
            mathOp(progCounter, program) { a, b -> a + b }
    },
    MULTIPLY(2) {
        override fun execute(progCounter: Int, program: MutableList<Int>) =
            mathOp(progCounter, program) { a, b -> a * b }
    },
    HALT(99) {
        override fun execute(progCounter: Int, program: MutableList<Int>) = program.size
    };

    abstract fun execute(progCounter: Int, program: MutableList<Int>): Int

    fun mathOp(progCounter: Int, program: MutableList<Int>, func: (Int, Int) -> Int): Int {
        val a = program[program[progCounter + 1]]
        val b = program[program[progCounter + 2]]
        val c = func(a, b)
        program[program[progCounter + 3]] = c
        return progCounter + 4
    }
}
