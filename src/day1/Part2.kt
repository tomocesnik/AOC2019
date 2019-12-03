package day1

import java.io.File

fun main() {
    println(sumAllFuelMasses(readModulesMasses(FILE_NAME)))
}

fun sumAllFuelMasses(modulesMasses: List<Int>) = modulesMasses.map { calcModuleFuelReq(it) }.sum()

fun calcModuleFuelReq(mass: Int): Int {
    var sum = 0
    var fuelMass = calcFuelReq(mass)

    while (fuelMass > 0) {
        sum += fuelMass
        fuelMass = calcFuelReq(fuelMass)
    }

    return sum
}
