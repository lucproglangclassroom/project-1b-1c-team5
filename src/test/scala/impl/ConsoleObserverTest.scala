package edu.luc.cs.cs371.topwords
package impl

import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

class ConsoleObserverTest extends AnyFunSuite:

  test("console observer prints entries in expected format"):
    val originalOut = System.out
    val buffer = new ByteArrayOutputStream
    val testOut = new PrintStream(buffer)

    try
      System.setOut(testOut)

      val observer = new ConsoleObserver
      observer.onCloudUpdated(Seq(("aa", 2), ("bb", 1), ("cc", 1)))

      testOut.flush()
      val output = buffer.toString(StandardCharsets.UTF_8).trim

      assert(output == "aa: 2 bb: 1 cc: 1")
    finally
      System.setOut(originalOut)

end ConsoleObserverTest