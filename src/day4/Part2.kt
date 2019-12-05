package day4

fun main() {
    println(findNumDifferentPasswords(readRange(FILE_NAME), ::isValidExactPairPassword))
}

fun isValidExactPairPassword(number: Int): Boolean {
    val digits = number.toString().toCharArray().map { it.toString().toInt() }
    var digitsPair = false
    var numDigitDuplicates = 0
    for (i in 1 until digits.size) {
        if (digits[i] == digits[i - 1]) {
            numDigitDuplicates++
            if ((i == digits.size - 1) && (numDigitDuplicates == 1)) digitsPair = true
        } else {
            if (numDigitDuplicates == 1) digitsPair = true
            numDigitDuplicates = 0
            if (digits[i] < digits[i - 1]) return false
        }
    }
    return digitsPair
}
