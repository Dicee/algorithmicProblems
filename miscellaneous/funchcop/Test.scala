package miscellaneous.funchcop

object Test extends App {
	val player   = new BasePlayer("coucou", _health = 10) { def play(x: List[ReadOnlyPlayer]) = () }
	val safeCopy = player.safeCopy
	
	player.hit(5)
	println(safeCopy.health)
	
	safeCopy.hit(5)
	println(safeCopy.health)

	player.hit(2)
	println(safeCopy.health)
}