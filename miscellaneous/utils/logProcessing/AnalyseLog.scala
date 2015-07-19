package miscellaneous.utils.logProcessing

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.SequenceInputStream
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.matching.Regex
import java.nio.file.Files
import java.nio.file.Paths

object AnalyseLog extends App {
    implicit class RichInputStream(stream: InputStream) { def concat(that: InputStream) = new SequenceInputStream(stream,that) }
    implicit class RichFile       (file  : File       ) { def inputStream = new FileInputStream(file)                          }
    
    try {
       if (args.length < 1) exitWithMessage("Missing input file parameter")
       
       val nFiles = if (args.length > 2 && args(args.length - 2) == "--errors") args.length - 2 else  args.length       
       
       val collectStat = 
           if (nFiles != args.length) {
               val errorsPath = args(args.length - 1)
               println("\nFailed workunits written in " + errorsPath)
               val reg = new Regex(".*WorkUnit\\{(.+)\\}.*marketplace ([^\\s]+)")
               val extract = (line: String) => { 
                   val matcher = reg.findFirstMatchIn(line).get
                   "%s for %s".format(matcher.group(1),matcher.group(2))
               }
               Some(new CollectStat(errorsPath,s => s.contains("Failure during comparison") && !s.contains("NotFound"), extract))
           } else
               None
       
       val input = args.slice(1,nFiles)
                       .map(new File(_))
                       .foldRight[InputStream](new File(args(0)).inputStream)((file,is) => is concat file.inputStream)
       Source.fromInputStream(input).getLines.foreach(line => (Stats.ALL ++ collectStat).foreach(_.ingest(line)))
       
       Stat.publishAll(Stats.COMPLIANCE_STATS)
       Stat.publishAll(Stats.PER_NUMBER_OF_CORES_STATS)
       Stat.publishAll(Stats.PER_STAGE_STATS)
       if (collectStat.isDefined) collectStat.get.publish
       
       println("Number of timeslices built to gather this data : " + Stats.COMPLIANCE_STATS.map(_.count).sum/128f)
    } catch {
        case e: Exception => exitWithMessage(e.getMessage); 
    }
    
    def exitWithMessage(msg: String) = { println("Error : " + msg); System.exit(0) }
}

object Stats {
    val SUCCESS = new CounterStat("Successful checks : " + _, _.contains("Successfully checked WorkUnit"))
    val SUCCESS_NO_ORDER = new CounterStat("Successful checks ignoring the order, the date and removing duplicates : " + _, _.contains("ignoring the order, the duplicates and the timestamp"))
    val NO_DATA = new CounterStat("No data available to comparison : " + _, s => s.contains("Failure during comparison of WorkUnit") && s.contains("NotFoundException"))
    val FAILURES = new CounterStat("Failed checks : " + _, s => s.contains("Failure during comparison of WorkUnit") && !s.contains("NotFoundException"))
    val COMPLIANCE_STATS = List(SUCCESS, SUCCESS_NO_ORDER, NO_DATA, FAILURES)
    
    private val perCoresTime = new Regex("(\\d+) seconds") 
    val PER_NUMBER_OF_CORES_STATS = Stream.iterate(8)(x => if (x < 128) 2*x else x + 64).take(10).map(x => {
        val extractValue = (s: String) => perCoresTime.findAllMatchIn(s).map(_.group(1).toInt).toList.head
        new AverageStat(
                (count,avg) => "Average execution time with %d cores : %s s (computed on %d timeslices)".format(x, avg, count), 
                s => s.contains("Built timeslice") && s.contains(x + " cores"), 
                extractValue)
    })

    private def perStage(description: String, logFragment: String) = {
        val perStageTime = new Regex("finished in (\\d+(\\.\\d+)?)")
        new AverageStat(
                (count,avg) => description + " average execution time : " + avg + " s",
                line => line.contains(logFragment) && line.contains("finished"), 
                perStageTime.findAllMatchIn(_).map(_.group(1).toDouble.ceil.toInt).toList.head)
    }
    
    val STAGE_1_EXECUTION_TIME = perStage("Download + rewrite + sort", "INFO,DAGScheduler,Stage 0 (mapToPair at CdsRawToS3SparkTask.java")
    val STAGE_2_EXECUTION_TIME = perStage("Write to S3", "INFO,DAGScheduler,Stage 1 (foreach at CdsRawToS3SparkTask.java")
    val STAGE_3_EXECUTION_TIME = perStage("Compare the output with the prod one", "INFO,DAGScheduler,Stage 2 (foreach at CdsRawToS3SparkTask.java")
    
    val PER_STAGE_STATS = List(STAGE_1_EXECUTION_TIME, STAGE_2_EXECUTION_TIME, STAGE_3_EXECUTION_TIME)
    
    val ALL = List(SUCCESS, SUCCESS_NO_ORDER, NO_DATA, FAILURES) ++ PER_NUMBER_OF_CORES_STATS ++ PER_STAGE_STATS
}