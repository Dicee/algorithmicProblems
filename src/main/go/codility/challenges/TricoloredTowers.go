package challenges

import "fmt"

/*
 * Link: https://app.codility.com/programmers/task/tricolored_towers/
 * Level: Medium
 * Score: 100% correctness and performance
 */
func main() {
	fmt.Println(Solution([]string{"aab", "cab", "baa", "baa"}))        // 3
	fmt.Println(Solution([]string{"zzz", "zbz", "zbz", "dgf"}))        // 2
	fmt.Println(Solution([]string{"abc", "cba", "cab", "bac", "bca"})) // 3
}

func Solution(towers []string) int {
	possibilities := make(map[string]int)
	maxCount := 0

	for i := 0; i < len(towers); i++ {
		tower := towers[i]
		updatePossibilities(tower, tower, true, possibilities, &maxCount)
		updatePossibilities(tower, fmt.Sprintf("%c%c%c", tower[1], tower[0], tower[2]), false, possibilities, &maxCount)
		updatePossibilities(tower, fmt.Sprintf("%c%c%c", tower[0], tower[2], tower[1]), false, possibilities, &maxCount)
	}

	return maxCount
}

func updatePossibilities(tower, permutation string, countSelf bool, possibilities map[string]int, maxCount *int) {
	if tower == permutation && !countSelf {
		return
	}
	count := possibilities[permutation] + 1
	possibilities[permutation] = count
	*maxCount = max(count, *maxCount)
}

// god... does this language seriously force you to implement this yourself? Well... that's proof enough that not everything Google does
// is neat and nice to use!
func max(x, y int) int {
	if x < y {
		return y
	}
	return x
}
