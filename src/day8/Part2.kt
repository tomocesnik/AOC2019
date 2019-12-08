package day8

fun main() {
    println(drawPicture(readImage(FILE_NAME)))
}

fun drawPicture(fileContent: String): String {
    val pixelBuffer: MutableList<PixelColor> = mutableListOf()
    fileContent.indices.forEach {
        val layer = it / PIXELS_IN_LAYER
        val pixelIndex = it % PIXELS_IN_LAYER

        val pixelColor =
            PixelColor.values().find { pc -> pc.code == fileContent[it].toString().toInt() } ?: PixelColor.TRANSPARENT
        if (layer == 0) pixelBuffer.add(pixelColor)
        else if (pixelBuffer[pixelIndex] == PixelColor.TRANSPARENT) pixelBuffer[pixelIndex] = pixelColor
    }

    val sb = StringBuilder()
    pixelBuffer.forEachIndexed { index, pixelColor ->
        sb.append(pixelColor.outChar)

        val column = index % IMAGE_WIDTH
        if (column == (IMAGE_WIDTH - 1)) {
            sb.append("\n")
        }
    }
    return sb.toString()
}

enum class PixelColor(val code: Int, val outChar: String) {
    BLACK(0, " "),
    WHITE(1, "@"),
    TRANSPARENT(2, "")
}
