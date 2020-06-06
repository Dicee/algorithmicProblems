package miscellaneous.funchcop

import com.dici.check.Check

import scala.math._

trait Player {
	def health   : Int
	def actionPts: Int
	def movePts  : Int
            
	def hit (damage: Int  ): Player
	def move(dist  : Int  ): Player
	def cast(spell : Spell): Player
	def play(players: List[ReadOnlyPlayer]): Unit
	
	val name    : String
	val safeCopy: ReadOnlyPlayer
}

sealed trait ReadOnlyPlayer extends Player {
    val safeCopy = this

    // noop
	override def hit (damage: Int  ) = this
	override def move(dist  : Int  ) = this
	override def cast(spell : Spell) = this
	override def play(players: List[ReadOnlyPlayer]) = ()
}

object BasePlayer {
    private val namesRegistry = new scala.collection.mutable.HashSet[String]
            private def register(name: String) = {
        Check.isTrue(!namesRegistry.contains(name), s"Name already in use: ${name}")
        namesRegistry += name
    }
}

abstract class BasePlayer(val name: String, private var _health: Int = 0, private var _actionPts: Int = 6, private var _movePts: Int = 3) extends Player {
	Check.isPositive(_health)
	Check.isPositive(_actionPts)
	Check.isPositive(_movePts)
	
	BasePlayer.register(name)
	
	def play(players: List[ReadOnlyPlayer])

	final def health    = _health
	final def actionPts = _actionPts
	final def movePts   = _movePts
	
	final def hit(damage: Int) = { _health = max(0, _health - damage); this }
	
	final def move(dist: Int) = {
		if (dist > _movePts) throw new ImpossibleMoveException(dist, _movePts)
		_movePts -= dist
		this
	}
	
	final def cast(spell: Spell) = {
	    if (spell.cost > _actionPts) throw new ImpossibleActionException(spell.cost, _movePts)
		_actionPts -= spell.cost
		this
	}
	
	lazy val safeCopy: ReadOnlyPlayer = ReadOnlyCopy
	
	private object ReadOnlyCopy extends ReadOnlyPlayer {
		// reads the values of the containing outer object
		override def health    = _health
		override def actionPts = _actionPts
		override def movePts   = _movePts
		
		lazy val name = BasePlayer.this.name
	}
}

sealed class UnsufficientPointsException(ptsName: String, required: Int, actual: Int) 
	extends Exception(s"Unsufficient ${ptsName} points. Required: ${required}, actual: ${actual}")
sealed class ImpossibleMoveException  (required: Int, actual: Int) extends UnsufficientPointsException("move", required, actual)
sealed class ImpossibleActionException(required: Int, actual: Int) extends UnsufficientPointsException("health", required, actual)