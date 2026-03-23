package edu.luc.cs.cs371.topwords
package impl

import edu.luc.cs.cs371.topwords.main.Config
import org.scalatest.funsuite.AnyFunSuite

class TopWordsProcessorTest extends AnyFunSuite:

  test("no output before the window is full"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 5),
      observer
    )

    processor.processWords(Iterator("aa", "bb", "cc", "dd"))

    assert(observer.updates.isEmpty)

  test("first output appears when the window becomes full"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 5),
      observer
    )

    processor.processWords(Iterator("aa", "bb", "cc", "aa", "bb"))

    assert(observer.updates.size == 1)
    assert(observer.updates.head == Seq(("aa", 2), ("bb", 2), ("cc", 1)))

  test("short words are ignored"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 3),
      observer
    )

    processor.processWords(Iterator("a", "bb", "c", "dd", "ee"))

    assert(observer.updates.size == 1)
    assert(observer.updates.head == Seq(("bb", 1), ("dd", 1), ("ee", 1)))

  test("window slides forward correctly"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 5),
      observer
    )

    processor.processWords(Iterator("aa", "bb", "cc", "aa", "bb", "aa"))

    assert(observer.updates.size == 2)
    assert(observer.updates(0) == Seq(("aa", 2), ("bb", 2), ("cc", 1)))
    assert(observer.updates(1) == Seq(("aa", 2), ("bb", 2), ("cc", 1)))

  test("cloud size limits the number of reported words"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 2, minLength = 2, windowSize = 5),
      observer
    )

    processor.processWords(Iterator("aa", "bb", "cc", "aa", "bb"))

    assert(observer.updates.head == Seq(("aa", 2), ("bb", 2)))

  test("all same words produce one dominant entry"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 4),
      observer
    )

    processor.processWords(Iterator("aa", "aa", "aa", "aa"))

    assert(observer.updates.head == Seq(("aa", 4)))

  test("ties are broken alphabetically"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 3),
      observer
    )

    processor.processWords(Iterator("cc", "bb", "aa"))

    assert(observer.updates.head == Seq(("aa", 1), ("bb", 1), ("cc", 1)))

  test("words that slide out of the window are removed from the counts"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 3, minLength = 2, windowSize = 3),
      observer
    )

    processor.processWords(Iterator("aa", "bb", "cc", "dd"))

    assert(observer.updates.size == 2)
    assert(observer.updates(0) == Seq(("aa", 1), ("bb", 1), ("cc", 1)))
    assert(observer.updates(1) == Seq(("bb", 1), ("cc", 1), ("dd", 1)))

  test("cloud size larger than distinct words returns only available words"):
    val observer = new TestObserver
    val processor = new TopWordsProcessor(
      Config(cloudSize = 10, minLength = 2, windowSize = 4),
      observer
    )

    processor.processWords(Iterator("aa", "aa", "bb", "bb"))

    assert(observer.updates.size == 1)
    assert(observer.updates.head == Seq(("aa", 2), ("bb", 2)))

end TopWordsProcessorTest