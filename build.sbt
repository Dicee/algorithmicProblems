name := "algorithmicProblems"

version := "0.1"

scalaVersion := "2.12.7"
libraryDependencies ++= Seq(
  "org.projectlombok" % "lombok" % "1.18.12",
  "org.jsoup" % "jsoup" % "1.13.1",
  "com.google.guava" % "guava" % "29.0-jre"
)

val javaFXModules = Seq("base", "controls", "graphics", "web")
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "15-ea+5" classifier "mac"
)

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.13.3",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.3"
)

libraryDependencies ++= Seq(
  "org.hamcrest" % "hamcrest" % "2.2",
  "org.junit.jupiter" % "junit-jupiter-engine" % "5.0.0" % Test
)

