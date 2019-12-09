package day9

enum class OpCode(val code: Int) {
    ADD(1) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ) =
            Pair(
                mathOp(procState, parameterModes) { a, b -> (a.toBigInteger() + b.toBigInteger()).toString() },
                progInAndOut
            )
    },
    MULTIPLY(2) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ) =
            Pair(
                mathOp(procState, parameterModes) { a, b -> (a.toBigInteger() * b.toBigInteger()).toString() },
                progInAndOut
            )
    },
    SAVE_INPUT(3) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut>? {
            if (progInAndOut.inputs.isEmpty()) {
                return null
            }
            val newMem =
                parameterModes[0].writeData(progInAndOut.inputs[0], procState, procState.getValueAtProcCounter(1))
            return Pair(
                procState.copy(procCounter = procState.procCounter + 2, memory = newMem),
                progInAndOut.copy(inputs = progInAndOut.inputs.drop(1))
            )
        }
    },
    LOAD_OUTPUT(4) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val output = parameterModes[0].readData(procState, procState.getValueAtProcCounter(1))
            return Pair(
                procState.copy(procCounter = procState.procCounter + 2),
                progInAndOut.copy(outputs = progInAndOut.outputs.toMutableList().apply { add(output) })
            )
        }
    },
    JUMP_IF_TRUE(5) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val newProcCounter =
                if (parameterModes[0].readData(procState, procState.getValueAtProcCounter(1)).toInt() != 0) {
                    parameterModes[1].readData(procState, procState.getValueAtProcCounter(2)).toInt()
                } else {
                    procState.procCounter + 3
                }
            return Pair(procState.copy(procCounter = newProcCounter), progInAndOut)
        }
    },
    JUMP_IF_FALSE(6) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val newProcCounter =
                if (parameterModes[0].readData(procState, procState.getValueAtProcCounter(1)).toInt() == 0) {
                    parameterModes[1].readData(procState, procState.getValueAtProcCounter(2)).toInt()
                } else {
                    procState.procCounter + 3
                }
            return Pair(procState.copy(procCounter = newProcCounter), progInAndOut)
        }
    },
    LESS_THAN(7) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val cmp = if (parameterModes[0].readData(procState, procState.getValueAtProcCounter(1)).toBigInteger()
                < parameterModes[1].readData(procState, procState.getValueAtProcCounter(2)).toBigInteger()
            ) 1 else 0
            val newMem = parameterModes[2].writeData(cmp.toString(), procState, procState.getValueAtProcCounter(3))
            return Pair(procState.copy(procCounter = procState.procCounter + 4, memory = newMem), progInAndOut)
        }
    },
    EQUALS(8) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val cmp = if (parameterModes[0].readData(procState, procState.getValueAtProcCounter(1)).toBigInteger()
                == parameterModes[1].readData(procState, procState.getValueAtProcCounter(2)).toBigInteger()
            ) 1 else 0
            val newMem = parameterModes[2].writeData(cmp.toString(), procState, procState.getValueAtProcCounter(3))
            return Pair(procState.copy(procCounter = procState.procCounter + 4, memory = newMem), progInAndOut)
        }
    },
    ADJUST_RELATIVE_BASE(9) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ): Pair<ProcState, ProgInAndOut> {
            val relBaseAdjustment = parameterModes[0].readData(procState, procState.getValueAtProcCounter(1)).toInt()
            return Pair(
                procState.copy(
                    procCounter = procState.procCounter + 2,
                    relativeBase = procState.relativeBase + relBaseAdjustment
                ), progInAndOut
            )
        }
    },
    HALT(99) {
        override fun execute(
            procState: ProcState,
            progInAndOut: ProgInAndOut,
            parameterModes: List<ParameterMode>
        ) = Pair(procState.copy(procCounter = PROC_COUNTER_HALT), progInAndOut)
    };

    abstract fun execute(
        procState: ProcState,
        progInAndOut: ProgInAndOut,
        parameterModes: List<ParameterMode>
    ): Pair<ProcState, ProgInAndOut>?

    protected fun mathOp(
        procState: ProcState,
        parameterModes: List<ParameterMode>,
        func: (String, String) -> String
    ): ProcState {
        val a = parameterModes[0].readData(procState, procState.getValueAtProcCounter(1))
        val b = parameterModes[1].readData(procState, procState.getValueAtProcCounter(2))
        val c = func(a, b)
        val newMem = parameterModes[2].writeData(c, procState, procState.getValueAtProcCounter(3))
        return procState.copy(procCounter = procState.procCounter + 4, memory = newMem)
    }
}
