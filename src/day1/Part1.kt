package day1

import java.io.File

const val FILE_NAME = "src/day1/input.txt"

fun main() {
    println(sumFuelMasses(readModulesMasses(FILE_NAME)))
}

fun readModulesMasses(fileName: String) = File(fileName).readLines().map { it.toInt() }

fun sumFuelMasses(modulesMasses: List<Int>) = modulesMasses.map { calcFuelReq(it) }.sum()

fun calcFuelReq(mass: Int) = mass / 3 - 2
