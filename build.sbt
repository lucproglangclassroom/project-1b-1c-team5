name := "topwords"

version := "0.4"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "mainargs" % "0.7.8",
  "com.github.sbt.junit" % "jupiter-interface" % "0.17.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "org.scalacheck" %% "scalacheck" % "1.19.0" % Test,
  "org.scalatestplus" %% "scalacheck-1-18" % "3.2.19.0" % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.5.18"
)

Compile / mainClass := Some("edu.luc.cs.cs371.topwords.main.TopWords")
Compile / packageBin / mainClass := Some("edu.luc.cs.cs371.topwords.main.TopWords")

coverageMinimumStmtTotal := 80
coverageFailOnMinimum := false
coverageHighlighting := true
coverageExcludedPackages := "edu\\.luc\\.cs\\.cs371\\.echo\\..*"

Test / unmanagedSources / excludeFilter :=
  HiddenFileFilter || "Echo*.scala"

Test / parallelExecution := false

enablePlugins(JavaAppPackaging)