package edu.luc.cs.cs371.topwords
package main

import com.typesafe.scalalogging.StrictLogging
import edu.luc.cs.cs371.topwords.impl.{ConsoleObserver, TopWordsProcessor}
import mainargs.{ParserForMethods, arg, main}

object TopWords extends StrictLogging:

  @main
  def run(
           @arg(short = 'c', doc = "word cloud size") cloudSize: Int = 10,
           @arg(short = 'l', doc = "minimum word length") minLength: Int = 6,
           @arg(short = 'w', doc = "moving window size") windowSize: Int = 1000
         ): Unit =
    require(cloudSize > 0, "cloud-size must be positive")
    require(minLength > 0, "length-at-least must be positive")
    require(windowSize > 0, "window-size must be positive")

    val config = Config(
      cloudSize = cloudSize,
      minLength = minLength,
      windowSize = windowSize
    )

    logger.debug(
      s"cloudSize=${config.cloudSize} minLength=${config.minLength} windowSize=${config.windowSize}"
    )

    val observer = new ConsoleObserver
    val processor = new TopWordsProcessor(config, observer)

    val lines = scala.io.Source.stdin.getLines
    val words =
      import scala.language.unsafeNulls
      lines
        .flatMap(line => line.split("(?U)[^\\p{Alpha}0-9']+"))
        .filter(_.nonEmpty)

    processor.processWords(words)

  def main(args: Array[String]): Unit =
    ParserForMethods(this).runOrExit(args.toIndexedSeq)
    ()

end TopWords