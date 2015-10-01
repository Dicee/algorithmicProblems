package miscellaneous.funchcop

import scala.math._
import com.dici.check.Check

abstract class Player(private var _health: Int = 0, private var _actionPts: Int = 6, private var _movePts: Int = 3) {
	Check.isPositive(_health)
	Check.isPositive(_actionPts)
	Check.isPositive(_movePts)
	
	def health    = _health
	def actionPts = _actionPts
	def movePts   = _movePts
	
	def hit(damage: Int) = { _health = max(0, _health - damage); this }
	def move(dist: Int) = {
		if (dist > _movePts) throw new ImpossibleMoveException(dist, _movePts)
		_movePts -= dist
		this
	}
	def cast(spell: Spell) = {
	    if (spell.cost > _actionPts) throw new ImpossibleActionException(spell.cost, _movePts)
		_actionPts -= spell.cost
		this
	}
}

sealed class UnsufficientPointsException(ptsName: String, required: Int, actual: Int) 
	extends Exception(s"Unsufficient ${ptsName} points. Required: ${required}, actual: ${actual}")
sealed class ImpossibleMoveException  (required: Int, actual: Int) extends UnsufficientPointsException("move", required, actual)
sealed class ImpossibleActionException(required: Int, actual: Int) extends UnsufficientPointsException("health", required, actual)