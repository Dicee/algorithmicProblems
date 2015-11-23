package codingame.medium

import java.util.Scanner
import java.util.LinkedList

object WinamaxChallenge extends App {
	type Decks = Array[LinkedList[Int]]
	
	val VALUES = Map(	) ++ (2 to 10).map(i => i.toString -> i)
	val BATTLE_TURNS = 3
	
	val sc   = new Scanner(System.in)
	val decks = Array(new LinkedList[Int], new LinkedList[Int])
	for (deck <- decks ; i <- 1 to sc.nextLine.toInt) deck.add(parseValue(sc.nextLine))
		
	var turns = 0
	var tie   = false
	val tempDecks = Array(new LinkedList[Int], new LinkedList[Int])
	
	while (!tie && decks.forall(!_.isEmpty)) {
		undeck(decks, tempDecks, 1)
		
		val cmp = tempDecks(0).peekLast - tempDecks(1).peekLast
		if (cmp != 0) {
			val winner = (cmp / Math.abs(cmp) - 1) / (-2)
			for (deck <- tempDecks) { decks(winner).addAll(deck); deck.clear }
			turns += 1
		} else {
			tie = decks(0).size < BATTLE_TURNS || decks(1).size < BATTLE_TURNS
			if (!tie) undeck(decks, tempDecks, BATTLE_TURNS)
		}
	}
	println(if (tie) "PAT" else (1 + decks.indexWhere(!_.isEmpty)) + " " + turns)
	
	def parseValue(line: String) = VALUES(line.dropRight(1)) 
	def undeck(decks: Decks, tempDecks: Decks, depth: Int) = 
		for (tuple <- decks.zip(tempDecks) ; i <- 1 to depth) tuple match {
			case (deck, tempDeck) => tempDeck.add(deck.pop) 
		}
}

