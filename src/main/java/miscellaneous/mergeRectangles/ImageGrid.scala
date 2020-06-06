package miscellaneous.mergeRectangles

import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File

import javax.imageio.ImageIO

// TODO: single pixel per border?
class ImageGrid(val width: Int, val height: Int, pixelSize: Int = 50) {
  private[this] val bufferedImage = new BufferedImage(pixelSize * width, pixelSize * height, TYPE_INT_RGB)

  def set(w: Int, h: Int, cellColor: CellColor): Unit = {
    validateCoordinates(w, h)

    setPixelRange(
      w * pixelSize until (w + 1) * pixelSize,
      h * pixelSize until (h + 1) * pixelSize,
      cellColor
    )
  }

  def setRange(wRange: Range, hRange: Range, cellColor: CellColor): Unit = {
    validateCoordinates(wRange.start, hRange.start)
    validateCoordinates(wRange.last, hRange.last)

    setPixelRange(
      wRange.start * pixelSize until (wRange.last + 1) * pixelSize,
      hRange.start * pixelSize until (hRange.last + 1) * pixelSize,
      cellColor)
  }

  private def setPixelRange(wPixelRange: Range, hPixelRange: Range, cellColor: CellColor): Unit = {
    for (i <- wPixelRange; j <- hPixelRange) {
      val isBorder = i == wPixelRange.start || j == hPixelRange.start || i == wPixelRange.last || j == hPixelRange.last
      val color = if (isBorder) cellColor.border else cellColor.base
      bufferedImage.setRGB(i, j, color.getRGB)
    }
  }

  def writeTo(f: File): Unit = ImageIO.write(bufferedImage, "png", f)

  def writeTo(writableImage: WritableImage, wOffset: Int, hOffset: Int): Unit = {
    for (w <- 0 until bufferedImage.getWidth; h <- 0 until bufferedImage.getHeight) {
      writableImage.set(w + wOffset, h + hOffset, new Color(bufferedImage.getRGB(w, h)))
    }
  }

  private def validateCoordinates(w: Int, h: Int): Unit = if (w < 0 && w >= width && h < 0 && h >= height)
    throw new IndexOutOfBoundsException(s"Out of bounds: ${(w, h)} for image dimensions ${(width, height)}")
}

sealed abstract class CellColor(val base: Color, val border: Color)
object GreenCell extends CellColor(Color.green, Color.black)
object RedCell extends CellColor(Color.red, Color.black)
object GrayCell extends CellColor(Color.gray, Color.black)