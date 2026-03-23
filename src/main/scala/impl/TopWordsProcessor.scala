package edu.luc.cs.cs371.topwords
package impl

import edu.luc.cs.cs371.topwords.main.{Config, WordCloudObserver}

import scala.collection.mutable

class TopWordsProcessor(config: Config, observer: WordCloudObserver):

  private val window = mutable.Queue.empty[String]
  private val counts = mutable.HashMap.empty[String, Int]

  def processWord(word: String): Unit =
    if word.length >= config.minLength then
      addWord(word)

      if window.size > config.windowSize then
        removeOldestWord()

      if window.size == config.windowSize then
        observer.onCloudUpdated(currentCloud)

  def processWords(words: Iterator[String]): Unit =
    words.foreach(processWord)

  private def addWord(word: String): Unit =
    window.enqueue(word)
    counts.update(word, counts.getOrElse(word, 0) + 1)

  private def removeOldestWord(): Unit =
    val oldest = window.dequeue()
    val newCount = counts(oldest) - 1
    if newCount <= 0 then {
      counts.remove(oldest)
      ()
    } else
      counts.update(oldest, newCount)

  private def currentCloud: Seq[(String, Int)] =
    counts.toSeq
      .sortBy((word, count) => (-count, word))
      .take(config.cloudSize)

end TopWordsProcessor