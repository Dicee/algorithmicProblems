package miscellaneous.mergeRectangles

import java.awt.Color

trait WritableImage {
  def set(w: Int, h: Int, color: Color): Unit
}
