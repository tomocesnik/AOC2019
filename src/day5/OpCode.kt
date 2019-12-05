package day5

enum class OpCode(val code: Int) {
    ADD(1) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ) =
            mathOp(progCounter, program, parameterModes) { a, b -> a + b }
    },
    MULTIPLY(2) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ) =
            mathOp(progCounter, program, parameterModes) { a, b -> a * b }
    },
    SAVE_INPUT(3) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            // to support more inputs, would make sense to remove the first one from the list
            program[program[progCounter + 1]] = inputs[0]
            return progCounter + 2
        }
    },
    LOAD_OUTPUT(4) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            outputs.add(parameterModes[0].getData(program[progCounter + 1], program))
            return progCounter + 2
        }
    },
    JUMP_IF_TRUE(5) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            if (parameterModes[0].getData(program[progCounter + 1], program) != 0) {
                return parameterModes[1].getData(program[progCounter + 2], program)
            }
            return progCounter + 3
        }
    },
    JUMP_IF_FALSE(6) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            if (parameterModes[0].getData(program[progCounter + 1], program) == 0) {
                return parameterModes[1].getData(program[progCounter + 2], program)
            }
            return progCounter + 3
        }
    },
    LESS_THAN(7) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            val cmp = if (parameterModes[0].getData(program[progCounter + 1], program)
                < parameterModes[1].getData(program[progCounter + 2], program)
            ) 1 else 0
            // writing always uses position mode
            program[program[progCounter + 3]] = cmp
            return progCounter + 4
        }
    },
    EQUALS(8) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ): Int {
            val cmp = if (parameterModes[0].getData(program[progCounter + 1], program)
                == parameterModes[1].getData(program[progCounter + 2], program)
            ) 1 else 0
            // writing always uses position mode
            program[program[progCounter + 3]] = cmp
            return progCounter + 4
        }
    },
    HALT(99) {
        override fun execute(
            progCounter: Int,
            program: MutableList<Int>,
            parameterModes: List<ParameterMode>,
            inputs: List<Int>,
            outputs: MutableList<Int>
        ) = program.size
    };

    abstract fun execute(
        progCounter: Int,
        program: MutableList<Int>,
        parameterModes: List<ParameterMode>,
        inputs: List<Int>,
        outputs: MutableList<Int>
    ): Int

    fun mathOp(
        progCounter: Int,
        program: MutableList<Int>,
        parameterModes: List<ParameterMode>,
        func: (Int, Int) -> Int
    ): Int {
        val a = parameterModes[0].getData(program[progCounter + 1], program)
        val b = parameterModes[1].getData(program[progCounter + 2], program)
        val c = func(a, b)
        // writing always uses position mode
        program[program[progCounter + 3]] = c
        return progCounter + 4
    }
}
