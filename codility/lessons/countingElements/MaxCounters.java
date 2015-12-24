package codility.lessons.countingElements;

/**
 *   Level : respectable 
 */
class MaxCounters {
    class Counter {
        public int version, value;
        public Counter(int version, int value) {
            this.version = version;
            this.value   = value;
        }
    }
            
    public int[] solution(int N, int[] A) {
        Counter[] counters = new Counter[N];
          
        for (int i=0 ; i<N ; i++) 
            counters[i] = new Counter(0,0);
                     
            int max     = 0;
            int base    = max;
            int version = 0;
                                                  
            for (int i : A) {
                if (1 <= i && i <= N) {
                    if (version > counters[i - 1].version) {
                        counters[i - 1].version = version;
                        counters[i - 1].value   = 1;
                    } else 
                        counters[i - 1].value++;
                        max = Math.max(max,base + counters[i - 1].value);   
                    } else {
                        version++;
                        base = max;
                    }
             }
             
            int[] result = new int[N];
            for (int i=0 ; i<N ; i++) 
                result[i] = base + (counters[i].version == version ? counters[i].value : 0);
            
            return result;
      }
}
