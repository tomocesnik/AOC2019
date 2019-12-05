package day4

import java.io.File

const val FILE_NAME = "src/day4/input.txt"

fun main() {
    println(findNumDifferentPasswords(readRange(FILE_NAME), ::isValidPassword))
}

fun readRange(fileName: String) = File(fileName).readText().split("-").map { it.trim().toInt() }

fun findNumDifferentPasswords(range: List<Int>, validityFunc: (Int) -> Boolean) =
    (range[0]..range[1]).filter { validityFunc(it) }.count()

fun isValidPassword(number: Int): Boolean {
    val digits = number.toString().toCharArray().map { it.toString().toInt() }
    var digitsPair = false
    for (i in 1 until digits.size) {
        if (digits[i] < digits[i - 1]) return false
        else if (digits[i] == digits[i - 1]) digitsPair = true
    }
    return digitsPair
}
