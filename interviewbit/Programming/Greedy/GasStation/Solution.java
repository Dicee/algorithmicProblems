public class Solution {
	public int canCompleteCircuit(List<Integer> gas, List<Integer> cost) {
        int minimumStockToFinishLap = 0;
        int stock = 0;
        int solution = 0;

        for (int i = 0; i < gas.size(); i++) {
            stock += gas.get(i) - cost.get(i);
            if (stock < 0) {
                minimumStockToFinishLap -= stock;
                stock = 0;
                solution = i + 1;
            }
        }

        return stock >= minimumStockToFinishLap && solution < gas.size() ? solution : -1;
    }
}
