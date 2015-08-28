package miscellaneous.utils.collection

import miscellaneous.utils.check.Check

class BloomFilter[T](numBuckets: Int, hashFunctions: (T => Int)*) {
    Check.isGreaterThan(numBuckets, 0)
    Check.isGreaterThan(hashFunctions.length, 0)
    
    private val hashFuns = hashFunctions.map(f => (t: T) => Math.abs(f(t)) % numBuckets).toArray
    private val buckets  = Array.fill[Boolean](numBuckets)(false)
    private var size     = 0
    
    def +=            (t: T) = { size += 1; hashFuns.foreach(hash => buckets(hash(t)) = true) } 
    def mayContain    (t: T) = if (hashFuns.forall(hash => buckets(hash(t)))) falsePositiveProbability else 0
    def doesNotContain(t: T) = mayContain(t) == 0
    
    private def falsePositiveProbability = Math.pow(1 - Math.pow(1 - 1d/buckets.length, hashFuns.length*size), hashFuns.length)
}