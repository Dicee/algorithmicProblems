package codeeval.easy.SimpleOrTrump

object Main extends App {
    val cardWeights = ('2' to '9').map(_.toString).zip(2 to 9).toMap ++ Map("10" -> 10, "J" -> 11, "Q" -> 12, "K" -> 13, "A" -> 14)

    scala.io.Source.fromFile(args(0))
        .getLines()
        .map(_.split(" \\| ") match { case Array(cards, suitString) => (cards.split(' ').map(parseCard), suitString.head) })
        .map { case (Array(leftCard, rightCard), suit) =>
            val compareWeights = leftCard.hasSameSuit(rightCard) || (!leftCard.hasSuit(suit) && !rightCard.hasSuit(suit))
            selectOrReject(leftCard, rightCard, suit, compareWeights) ++ selectOrReject(rightCard, leftCard, suit, compareWeights)
        }
        .map(_.mkString(" "))
        .foreach(println)

    def parseCard(s: String) = Card(s.last, s.substring(0, s.length - 1))

    def selectOrReject(card: Card, otherCard: Card, suit: Char, compareWeights: Boolean) =
        if ((compareWeights && cardWeights(card.weight) >= cardWeights(otherCard.weight)) ||
           (!compareWeights && card .hasSuit(suit))) Some(card)
        else                                         None

    case class Card(suit: Char, weight: String) {
        def hasSuit    (suit: Char) = this.suit == suit
        def hasSameSuit(that: Card) = hasSuit(that.suit)
        override def toString = weight + suit
    }
}
