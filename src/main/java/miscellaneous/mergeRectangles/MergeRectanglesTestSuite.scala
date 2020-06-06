package miscellaneous.mergeRectangles

import java.awt.{Color, Desktop}
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File

import javax.imageio.ImageIO
import miscellaneous.mergeRectangles.MultiBeforeAfterImages.{PixelsBetweenBeforeAfter, PixelsBetweenTestCases}

import scala.math.min

object MergeRectanglesTestSuite {
  def main(args: Array[String]): Unit = {
    new TestSuite(List(
      "resource/top_left_corner.txt",
      "resource/center_no_contact.txt",
      "resource/top_right_corner.txt",
      "resource/right_edge_only.txt",
      "resource/top_and_bottom_edges_only.txt",
      "resource/right_and_left_edges_only.txt",
      "resource/everything.txt",
      "resource/nothing.txt",
      "resource/all_edges.txt",
      "resource/top_left_bottom_edges.txt",
      "resource/left_right_bottom_edges.txt",
      "resource/bottom_left_corner.txt",
      "resource/top_edge_only.txt",
      "resource/lanes.txt",
      "resource/dense.txt",
      "resource/internal_holes.txt",
      "resource/disconnected.txt",
      "resource/disconnected_bounding_box_bad_case.txt"
    )).executeSimple(new StopCondition(minRectangleArea = 2), 18, 18, 4, 15, new File("output.png"))
  }
}

private class TestSuite(inputFiles: Seq[String]) {
  def executeSimple(stopCondition: StopCondition, width: Int, height: Int, testsPerLine: Int, pixelSize: Int, outputFile: File) =
    execute(stopCondition, width, height, testsPerLine, pixelSize, MergeRectangles.mergeRectanglesSimple, outputFile)

  def executeHybrid(stopCondition: StopCondition, width: Int, height: Int, testsPerLine: Int, pixelSize: Int, outputFile: File) =
    execute(stopCondition, width, height, testsPerLine, pixelSize, MergeRectangles.mergeRectanglesHybrid, outputFile)

  private def execute(stopCondition: StopCondition, width: Int, height: Int, testsPerLine: Int, pixelSize: Int,
                      algorithm: (BinaryImageGrid, StopCondition) => BinaryImageGrid, outputFile: File): Unit = {

    val testResults = new MultiBeforeAfterImages(inputFiles.length, testsPerLine, width, height, pixelSize)
    for (filename <- inputFiles) {
      val before = BinaryImageGrid.fromInputStream(MergeRectangles.getClass.getResourceAsStream(filename), pixelSize)
      validateInputImageSize(width, height, filename, before)
      val after = algorithm(before, stopCondition)
      testResults.addTestCase(before, after)
    }

    testResults.writeTo(outputFile)
    Desktop.getDesktop.browse(outputFile.toURI)
  }

  private def validateInputImageSize(width: Int, height: Int, filename: String, before: BinaryImageGrid) = {
    if (before.width != width || before.height != height)
      throw new IllegalArgumentException(s"Was expecting image of dimension ${(width, height)} but $filename has dimension ${(before.width, before.height)}")
  }
}

// TODO: could add checks on parameters
private class MultiBeforeAfterImages(numTests: Int, testsPerLine: Int, width: Int, height: Int, pixelSize: Int) extends WritableImage {
  private[this] val bufferedImage = new BufferedImage(determineWidth, determineHeight, TYPE_INT_RGB)
  private[this] var count = 0

  def addTestCase(before: BinaryImageGrid, after: BinaryImageGrid) = {
    if (count == numTests) throw new IllegalStateException(s"Already inserted $numTests tests, can't insert more in allocated space")

    val cellHeight = height * pixelSize + PixelsBetweenTestCases
    val cellWidth = width * pixelSize * 2 + PixelsBetweenTestCases + PixelsBetweenBeforeAfter

    val (line, column) = (count / testsPerLine, count % testsPerLine)
    val (wOffset, hOffset) = (column * cellWidth, line * cellHeight)

    before.writeTo(this, wOffset, hOffset)
    after.writeTo(this, wOffset + width * pixelSize + PixelsBetweenBeforeAfter, hOffset)

    count += 1
  }

  override def set(w: Int, h: Int, color: Color): Unit = bufferedImage.setRGB(w, h, color.getRGB)
  def writeTo(f: File): Unit = ImageIO.write(bufferedImage, "png", f)

  private def determineWidth = {
    val columns = min(numTests, testsPerLine)
    columns * (width * pixelSize * 2 + PixelsBetweenBeforeAfter) + (columns - 1) * PixelsBetweenTestCases
  }

  private def determineHeight = {
    val rows = numTests / testsPerLine + (if (numTests % testsPerLine == 0) 0 else 1)
    rows * height * pixelSize + (rows - 1) * PixelsBetweenTestCases
  }
}

private object MultiBeforeAfterImages {
  val PixelsBetweenBeforeAfter = 2
  val PixelsBetweenTestCases = 15
}
