package interviewbit.Programming.Greedy.GasStation;

import java.util.List;

/**
 * Proof that a greedy solution works for this problem. TODO: translate it in english (I originally wrote it for a friend in my
 * native language) !
 *
 * Notons S(i) une solution partant de l’indice i. Soit s(i,j) la quantité d’essence dans le réservoir après avoir parcouru tous
 * les indices (circulaires) entre i et j avec une réserve initiale de 0.
 *
 * Si S(i) est une solution, alors s(i,j) >= 0 pour tout j < N. En effet, s’il existe un j tel que s(i,(j + 1) mod N) < 0,
 * il est impossible d’atteindre j+1 depuis j donc S(i) ne peut être une solution. Réciproquement, si s(i,j) reste positif pour
 * tout j, alors toute transition du noeud j au noeud (j + 1) mod N est possible, donc S(i) est une solution.
 *
 * Ainsi, S(i) solution <=> s(i,j) >= 0 pour tout j < N.
 *
 * Supposons maintenant qu’il existe (i, j >= i) tels que pour tout k < j, s(i, k) >= 0 et s(i, j) < 0. Prouvons que pour tout k
 * tel que i <= k < j, S(k) n’est pas solution.
 *
 * Soit k dans l’intervalle décrit, on sait que s(i,j) < 0 et s(i,k) >= 0. On sait également que s(k,k) = 0 puisque le stock
 * initial d’essence est de 0. Ainsi, s(i,k) >= s(k,k). Par ailleurs, s(i,k+1) = s(i,k) + g(k+1) - s(k+1) où g(k+1) est la
 * quantité d’essence disponible à la station k+1 et s(k+1) le coût de transport de k à k+1. De la même façon,
 * s(k,k+1) = s(k,k) + g(k+1) - s(k+1) = s(i,k+1) + s(k,k) - s(i,k).
 *
 * On peut progresser ainsi pour arriver à la conclusion que s(k,j) = s(i,j) + s(k,k) - s(i,k). Etant donné que s(i,k) >= s(k,k),
 * on en déduit que s(k,j) <= s(i,j) < 0. Ainsi, S(k) ne peut être solution.
 *
 * On a donc prouvé qu’un algorithme glouton effectuant une unique itération de gauche à droite peut déterminer la solution
 * d’indice minimale. En effet, si le stock d’essence atteint un nombre négatif à l’indice j, nous avous démontré plus haut
 * qu’aucune solution ne pouvait commencer à un indice inférieur à j.
 */
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
