package day13

class ArcadeDisplay() {
    private val pixelBuffer = mutableMapOf<Int, MutableMap<Int, String>>()
    var segmentedDisplay = 0
        private set

    fun update(tiles: List<List<Int>>) {
        updatePixelBuffer(tiles)
        drawPixelBuffer()
        drawSegmentedDisplay()
    }

    private fun updatePixelBuffer(tiles: List<List<Int>>) {
        tiles.forEach {
            if (it[0] == -1) {
                segmentedDisplay = it.last()
            } else {
                val row = pixelBuffer.computeIfAbsent(it[1]) { mutableMapOf<Int, String>() }
                row[it[0]] = when (it.last()) {
                    0 -> " "
                    1 -> "X"
                    2 -> "+"
                    3 -> "_"
                    4 -> "@"
                    else -> ""
                }
            }
        }
    }

    private fun drawPixelBuffer() {
        pixelBuffer.toSortedMap().values.forEach {
            it.toSortedMap().values.forEach { pixel -> print(pixel) }
            println()
        }
    }

    private fun drawSegmentedDisplay() {
        println("SCORE: $segmentedDisplay")
    }
}
