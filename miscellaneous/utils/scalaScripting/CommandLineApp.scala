package miscellaneous.utils.scalaScripting

trait CommandLineApp extends App {
  def main()

  try {
    main()
  } catch {
    case e: Exception => exitWithMessage(e.getMessage);
  }

  protected def exitWithMessage(msg: String) = { println("Error : " + msg); System.exit(0) }
}
