package codeeval.easy.AgeDistribution;

object AgeCategory  {
    val _1     = AgeCategoryVal(0 ,2  ,"Still in Mama's arms"      )
    val _2     = AgeCategoryVal(3 ,4  ,"Preschool Maniac"          )
    val _3     = AgeCategoryVal(5 ,11 ,"Elementary school"         )
    val _4     = AgeCategoryVal(12,14 ,"Middle school"             )
    val _5     = AgeCategoryVal(15,18 ,"High school"               )
    val _6     = AgeCategoryVal(19,22 ,"College"                   )
    val _7     = AgeCategoryVal(23,65 ,"Working for the man"       )
    val _8     = AgeCategoryVal(66,100,"The Golden Years"          )
    val values = Array(_1,_2,_3,_4,_5,_6,_7,_8)
    protected case class AgeCategoryVal(low: Int, high: Int, msg: String) {
        def accept(n: Int) = low <= n && n <= high
    }
}

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(n => AgeCategory.values.find(_.accept(n.toInt)) match {
    	    case Some(x) => x.msg
    	    case None    => "This program is for humans"
    	})
    	.foreach(println)
}
