package edu.luc.cs.cs371.topwords
package impl

import edu.luc.cs.cs371.topwords.main.WordCloudObserver
import scala.collection.mutable.ArrayBuffer

class TestObserver extends WordCloudObserver:
  val updates = ArrayBuffer.empty[Seq[(String, Int)]]

  override def onCloudUpdated(entries: Seq[(String, Int)]): Unit =
    updates += entries
end TestObserver