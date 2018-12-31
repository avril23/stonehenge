package org.sstudio.stonehenge.common.utils

import org.joda.time.DateTime

final class StopWatch {

  private[this] var _startTime = 0L
  private[this] var _elapsedTime = 0L

  def elapsedTime: Long = _elapsedTime

  def start(): DateTime = {
    _startTime = System.currentTimeMillis()
    DateTime.now()
  }

  def stop(): Long = {
    if (_startTime == 0L) {
      throw new Exception("Please start stopwatch first.")
    }
    val endTime = System.currentTimeMillis()
    val diff = endTime - _startTime
    _elapsedTime = diff
    diff
  }
}