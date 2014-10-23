/**
 *  Level : painless
 */
class CountDiv {
    public int solution(int A, int B, int K) {
        int count = 0;
        while (A % K != 0 && A < B) A++;
        return (B - A)/K + (A % K == 0 ? 1 : 0);
    }
}
