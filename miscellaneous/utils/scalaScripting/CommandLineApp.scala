package miscellaneous.utils.scalaScripting

trait CommandLineApp extends App {
	def main()

	try {
		main()
		System.exit(0)
	} catch {
		case e: Exception => exitWithMessage(e.getMessage);
	}

	protected def exitWithMessage(msg: String) = { println("Error : " + msg); System.exit(0) }
}
