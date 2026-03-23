package edu.luc.cs.cs371.topwords
package impl

import edu.luc.cs.cs371.topwords.main.WordCloudObserver

class ConsoleObserver extends WordCloudObserver:
  override def onCloudUpdated(entries: Seq[(String, Int)]): Unit =
    val out = System.out
    out.println(entries.map((word, count) => s"$word: $count").mkString(" "))
    if out.checkError() then
      sys.exit(0)
end ConsoleObserver