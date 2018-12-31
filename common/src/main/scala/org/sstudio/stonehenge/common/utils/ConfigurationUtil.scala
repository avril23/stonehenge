package org.sstudio.stonehenge.common.utils

import com.typesafe.config.{Config, ConfigFactory}
import collection.JavaConversions.collectionAsScalaIterable

final class ConfigurationUtil(configName: String = "") {

  private[this] val config: Config =
    if (configName.nonEmpty) {
      ConfigFactory.load(configName)
    }
    else {
      ConfigFactory.load()
    }

  def getConfig: Config = config

  def hasPath(path: String): Boolean = {
    config.hasPath(path)
  }

  def getString(path: String): String = {
    config.getString(path)
  }

  def getString(path: String, default: String): String = {
    if (hasPath(path)) {
      config.getString(path)
    }
    else {
      default
    }
  }

  def getInt(path: String): Int = {
    config.getInt(path)
  }

  def getInt(path: String, default: Int): Int = {
    if (hasPath(path)) {
      config.getInt(path)
    }
    else {
      default
    }
  }

  def getBool(path: String): Boolean = {
    config.getBoolean(path)
  }

  def getBool(path: String, default: Boolean): Boolean = {
    if (hasPath(path)) {
      config.getBoolean(path)
    }
    else {
      default
    }
  }

  def getDouble(path: String): Double = {
    config.getDouble(path)
  }

  def getDouble(path: String, default: Double): Double = {
    if (hasPath(path)) {
      config.getDouble(path)
    }
    else {
      default
    }
  }

  def getStringList(path: String, default: List[String]): List[String] = {
    if (hasPath(path)) {
      config.getStringList(path).toList
    }
    else {
      default
    }
  }
}