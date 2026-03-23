package edu.luc.cs.cs371.topwords
package main

trait WordCloudObserver:
  def onCloudUpdated(entries: Seq[(String, Int)]): Unit
end WordCloudObserver