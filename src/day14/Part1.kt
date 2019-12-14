package day14

import java.io.File
import kotlin.math.ceil

const val FILE_NAME = "src/day14/input.txt"

const val CHEMICAL_ORE = "ORE"
const val CHEMICAL_FUEL = "FUEL"

fun main() {
    println(calcMinReqOreForFuel(readReactions(FILE_NAME)))
}

fun readReactions(fileName: String) = File(fileName).readLines().map { readReaction(it) }

data class ChemicalAmount(val name: String, val amount: Int)
data class Reaction(val inputs: List<ChemicalAmount>, val output: ChemicalAmount)

fun readReaction(line: String): Reaction {
    val parts = line.split("=>")
    val output = readChemicalAmount(parts[1])
    val inputs = parts[0].split(",").map { readChemicalAmount(it) }
    return Reaction(inputs, output)
}

fun readChemicalAmount(str: String): ChemicalAmount {
    val parts = str.trim().split(" ")
    return ChemicalAmount(parts[1], parts[0].toInt())
}

fun calcMinReqOreForFuel(reactions: List<Reaction>): Int {
    val chemicalsMap = reactions.map { it.output.name to it }.toMap()
    val produced = mutableMapOf<String, Int>()
    calcReqOre(CHEMICAL_FUEL, 1, chemicalsMap, produced)
    return produced[CHEMICAL_ORE] ?: -1
}

fun calcReqOre(
    chemical: String,
    reqAmount: Int,
    chemicalsMap: Map<String, Reaction>,
    produced: MutableMap<String, Int>
): Int {
    if (chemical == CHEMICAL_ORE) {
        produced[chemical] = (produced[chemical] ?: 0) + reqAmount
        return reqAmount
    }

    val reaction = chemicalsMap[chemical] ?: return -1
    val numReactions = ceil((reqAmount - (produced[chemical] ?: 0)).toFloat() / reaction.output.amount).toInt()
    val oreAmount = reaction.inputs.map { calcReqOre(it.name, it.amount * numReactions, chemicalsMap, produced) }.sum()
    produced[chemical] = (produced[chemical] ?: 0) + numReactions * reaction.output.amount - reqAmount
    return oreAmount
}
