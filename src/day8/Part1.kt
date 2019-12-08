package day8

import java.io.File

const val FILE_NAME = "src/day8/input.txt"
const val IMAGE_WIDTH = 25
const val IMAGE_HEIGHT = 6
const val PIXELS_IN_LAYER = IMAGE_WIDTH * IMAGE_HEIGHT

fun main() {
    println(findMinZeroesLayer(readImage(FILE_NAME)))
}

fun readImage(fileName: String) = File(fileName).readText().trim()

fun findMinZeroesLayer(fileContent: String): Int {
    val counts: MutableList<Triple<Int, Int, Int>> = mutableListOf()
    fileContent.indices.forEach {
        val layer = it / PIXELS_IN_LAYER
        if (layer >= counts.size) {
            counts.add(Triple(0, 0, 0))
        }

        val countsOfLayer = counts[layer].toList().toMutableList()
        countsOfLayer[fileContent[it].toString().toInt()]++
        counts[layer] = Triple(countsOfLayer[0], countsOfLayer[1], countsOfLayer[2])
    }

    val minZeroesLayer = counts.minBy { it.first } ?: return -1
    return minZeroesLayer.second * minZeroesLayer.third
}
