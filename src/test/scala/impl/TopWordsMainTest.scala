package edu.luc.cs.cs371.topwords
package impl

import edu.luc.cs.cs371.topwords.main.TopWords
import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

class TopWordsMainTest extends AnyFunSuite:

  test("run produces expected output for sample input"):
    val input =
      """a b c
        |aa bb cc
        |aa bb aa bb
        |""".stripMargin

    val originalIn = System.in
    val originalOut = System.out

    val testIn =
      new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
    val buffer = new ByteArrayOutputStream
    val testOut = new PrintStream(buffer)

    try
      System.setIn(testIn)
      System.setOut(testOut)

      TopWords.run(cloudSize = 3, minLength = 2, windowSize = 5)

      testOut.flush()
      val lines =
        buffer.toString(StandardCharsets.UTF_8)
          .linesIterator
          .map(_.trim)
          .filter(_.matches(""".+\: \d+.*"""))
          .filterNot(_.contains("DEBUG"))
          .toList

      assert(lines == List(
        "aa: 2 bb: 2 cc: 1",
        "aa: 2 bb: 2 cc: 1",
        "aa: 2 bb: 2 cc: 1"
      ))
    finally
      System.setIn(originalIn)
      System.setOut(originalOut)

  test("main(args) parses command line arguments correctly"):
    val input =
      """a b c
        |aa bb cc
        |aa bb aa bb
        |""".stripMargin

    val originalIn = System.in
    val originalOut = System.out

    val testIn =
      new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
    val buffer = new ByteArrayOutputStream
    val testOut = new PrintStream(buffer)

    try
      System.setIn(testIn)
      System.setOut(testOut)

      TopWords.main(Array("-c", "3", "-l", "2", "-w", "5"))

      testOut.flush()
      val lines =
        buffer.toString(StandardCharsets.UTF_8)
          .linesIterator
          .map(_.trim)
          .filter(_.matches(""".+\: \d+.*"""))
          .filterNot(_.contains("DEBUG"))
          .toList

      assert(lines == List(
        "aa: 2 bb: 2 cc: 1",
        "aa: 2 bb: 2 cc: 1",
        "aa: 2 bb: 2 cc: 1"
      ))
    finally
      System.setIn(originalIn)
      System.setOut(originalOut)

  test("run rejects nonpositive cloud size"):
    intercept[IllegalArgumentException]:
      TopWords.run(cloudSize = 0, minLength = 2, windowSize = 5)

  test("run rejects nonpositive minimum length"):
    intercept[IllegalArgumentException]:
      TopWords.run(cloudSize = 3, minLength = 0, windowSize = 5)

  test("run rejects nonpositive window size"):
    intercept[IllegalArgumentException]:
      TopWords.run(cloudSize = 3, minLength = 2, windowSize = 0)

end TopWordsMainTest