package codeeval.hard.StringPermutations;

import java.util.Arrays

object MainScala extends App {
	def swap[T](arr: Array[T], i: Int, j: Int) = {
	    val tmp = arr(i)
	    arr(i)  = arr(j)
	    arr(j)  = tmp
	}
	
	def reverse[T](arr: Array[T], min: Int, max: Int) = {
	    var (_min,_max) = (min,max)
	    while (_min < _max) {
	        swap(arr,_min,_max)
	        _min += 1
	        _max -= 1
	    }
	}
	
	def sortedRight(arr: Array[Int],i: Int): Boolean = {
	    for (j <- Range(i,arr.length - 1))  
	        if (arr(j) < arr(j + 1))
	            return false
	    return true
	}
	
	val sb = new StringBuilder
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            var word = line.split("").filter(!_.isEmpty).sorted
            var perm  = Range(0,word.length).toArray
            val end   = perm.reverse
            
            sb.append(word.mkString)
            
            var index = 0
            while (!Arrays.equals(perm,end)) {
                if (index != 0 && sortedRight(perm,index)) {
                    while (index > 0 && perm(index - 1) > perm(index)) index -= 1
                    var k = index - 1
                    while (index < perm.length - 1 && perm(k) < perm(index + 1)) index += 1
                    
                    swap(perm,index,k)
                    reverse(perm,k + 1,perm.length - 1)
                 
                    swap(word,index,k)
                    reverse(word,k + 1,word.length - 1)
                    
                    index = k
                    sb.append(",")
                    sb.append(word.mkString)
                } else 
                    index += 1
            }
            val res = sb.toString
            sb.setLength(0)
            res
        })
        .foreach(println)
}