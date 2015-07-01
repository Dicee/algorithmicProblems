package miscellaneous.utils.logProcessing

import java.nio.file.Paths
import java.nio.file.Files
import scala.collection.mutable.ArrayBuffer

object Stat {
    def publishAll(stats: Seq[Stat]) = { stats.foreach(_.publish);  }
}

abstract class Stat(predicate: String => Boolean) {
    def ingest(line: String)
    def publish
}

class CollectStat(outPath: String, predicate: String => Boolean, transform: String => String = x => x) extends Stat(predicate) {
    private val out = Files.newBufferedWriter(Paths.get(outPath))
    def ingest(line: String) = if (predicate(line)) out.write(transform(line) + "\n")
    def publish = out.close
}

class CounterStat(description: Int => String, predicate: String => Boolean) extends Stat(predicate) {
    private var _count = 0
    
    override def ingest(line: String) = if (predicate(line)) _count += 1
    override def publish              = println(description(count))
    
    def count = _count
}

case class Average(avg: Double, error: Double) { override def toString = "%f < %f < %f".format(avg - 2*error, avg, avg + 2*error) }

class AverageStat(description: (Int,Average) => String, predicate: String => Boolean, extractValue: String => Int) extends Stat(predicate) {
    private val measures = ArrayBuffer[Int]()
    
    override def ingest(line: String) = if (predicate(line)) measures += extractValue(line)
    override def publish              = println(description(N,Average(mean, standardError)))   
    
    private def  N                    = measures.length
    private def  mean                 = measures.sum/N.toDouble
    private def  standardDeviation    = Math.sqrt(measures.map(_ - mean).map(Math.pow(_, 2)).sum/N)
    private def  standardError        = standardDeviation/Math.sqrt(measures.length)
}