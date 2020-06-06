package miscellaneous.funchcop

class Fight(players: Player*) {
	private val alivePlayers = new RingIterator(players: _*)
	
	private var _round = 1
	def round = _round
	
	def nextRound() = {
		val player = alivePlayers.next
		player.play(alivePlayers.elements.filter(_ == player).map(_.safeCopy))
		_round += 1
	}
}