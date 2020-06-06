package miscellaneous.mergeRectangles

import java.io.{File, InputStream}

import scala.io.Source

class BinaryImageGrid(val width: Int, val height: Int, pixelSize: Int = 50) {
  private[this] val grid = new ImageGrid(width, height, pixelSize)
  private[this] val matrix = Array.ofDim[Byte](width, height)

  def set(i: Int, j: Int): Unit = {
    matrix(i)(j) = 1
    grid.set(i, j, GreenCell)
  }

  def unset(i: Int, j: Int): Unit = {
    matrix(i)(j) = 0
    grid.set(i, j, RedCell)
  }

  def unsetRange(wRange: Range, hRange: Range): Unit = {
    for (i <- wRange; j <- hRange) unset(i, j)
    grid.setRange(wRange, hRange, RedCell)
  }

  def setRange(wRange: Range, hRange: Range): Unit = {
    for (i <- wRange; j <- hRange) set(i, j)
    grid.setRange(wRange, hRange, GreenCell)
  }

  def get(i: Int, j: Int) = matrix(i)(j) == 1

  def copy = {
    val copy = new BinaryImageGrid(width, height, pixelSize)
    for (i <- matrix.indices; j <- matrix.indices) if (get(i, j)) copy.set(i, j) else copy.unset(i, j)
    copy
  }

  def writeTo(f: File)= grid.writeTo(f)
  def writeTo(writableImage: WritableImage, wOffset: Int, hOffset: Int) = grid.writeTo(writableImage, wOffset, hOffset)
}

object BinaryImageGrid {
  def fromInputStream(is: InputStream, pixelSize: Int) = {
    val matrix = Source.fromInputStream(is).getLines.map(_.split("")).toArray
    val grid = new BinaryImageGrid(matrix(0).length, matrix.length, pixelSize)
    for (w <- matrix(0).indices; h <- matrix.indices) {
      if (matrix(h)(w) == "1") grid.set(w, h)
      else grid.unset(w, h)
    }
    grid
  }
}