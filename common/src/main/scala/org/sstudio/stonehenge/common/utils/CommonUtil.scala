package org.sstudio.stonehenge.common.utils

import java.util.UUID

object CommonUtil {

  def getUUID: String = UUID.randomUUID().toString.replace("-", "")

  def toBytes(x: String): Array[Byte] = {
    val len = x.length
    val buf = new Array[Byte](len)
    for (i <- 0 until len)
      buf(i) = x(i).toByte
    buf
  }

  def getUUID(seed: String, salt: String = ""): String = {
    val newSeed = if (salt == "") seed else seed + salt
    val bytes = toBytes(newSeed)
    UUID.nameUUIDFromBytes(bytes).toString.replace("-", "")
  }
}