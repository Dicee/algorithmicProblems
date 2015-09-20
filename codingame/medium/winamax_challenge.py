from collections import deque
import math

VALUES = { "J": 11, "Q": 12, "K": 13, "A": 14 }
VALUES.update({ str(i): i for i in range(2, 11) })

BATTLE_TURNS = 3

def parse_value(line):
	return VALUES[line[:-1]]

def undeck(decks, temp_decks, depth):
	for tup in zip(decks, temp_decks):
		for i in range(depth):
			tup[1].append(tup[0].popleft())

(decks, temp_decks) = ([ deque(), deque() ], [ deque(), deque() ]);
turns = 0
tie   = False
for deck in decks:
	for i in range(int(input())):
	    deck.append(parse_value(input()))

while (not(tie) and len(decks[0]) != 0 and len(decks[1]) != 0):
	undeck(decks, temp_decks, 1)

	comp = temp_decks[0][-1] - temp_decks[1][-1]
	if comp != 0:
		winner = (comp // abs(comp) - 1) // (-2)
		for deck in temp_decks:
			decks[winner].extend(deck)
			deck.clear()
		turns += 1
	else:
		tie = len(decks[0]) < BATTLE_TURNS or len(decks[1]) < BATTLE_TURNS
		if not(tie):
			undeck(decks, temp_decks, BATTLE_TURNS)

winner = "2" if len(decks[0]) == 0 else "1"
print("PAT" if (tie) else winner + " " + str(turns))