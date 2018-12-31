package org.sstudio.stonehenge.common.utils

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.{Filter, LoggerContext}
import org.apache.logging.log4j.core.appender.ConsoleAppender
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory

object LogUtil {

  private[this] val consolePattern = "%highlight{%d [%t] %-5level: %msg%n[StackTrace] %l%n%throwable}{INFO=green, WARN=cyan}"
  private[this] val pattern = "%d [%t] %-5level: %msg%n[StackTrace] %l%n%throwable"
  private[this] val logHome = ""
  private[this] val logFileName = ""
  private[this] val logAppenders = List("console", "rolling")

  private[this] def getLogLevel: Level = {
    val levelStr = ""
    levelStr.toLowerCase.trim match {
      case "debug" => Level.DEBUG
      case "error" => Level.ERROR
      case "fatal" => Level.FATAL
      case "trace" => Level.TRACE
      case "warn" => Level.WARN
      case "off" => Level.OFF
      case _ => Level.INFO
    }
  }

  private[this] val builder =
    ConfigurationBuilderFactory.newConfigurationBuilder()
      .setStatusLevel(getLogLevel)
      .setConfigurationName("MapleLogConfig")

  builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL)
    .addAttribute("level", getLogLevel))

  private[this] val rootLogger = builder.newRootLogger(Level.ERROR)

  for (appenderStr <- logAppenders) {
    val appender = appenderStr match {
      case "console" =>
        builder.newAppender("stdout", "Console")
          .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
          .add(builder.newLayout("PatternLayout")
            .addAttribute("pattern", consolePattern))
      case "rolling" =>
        val layoutBuilder = builder.newLayout("PatternLayout")
          .addAttribute("pattern", pattern)
        val triggeringPolicy = builder.newComponent("Policies")
        triggeringPolicy.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "500M"))
        triggeringPolicy.addComponent(builder.newComponent("TimeBasedTriggeringPolicy").addAttribute("interval", "1"))
        builder.newAppender("rolling", "RollingFile")
          .addAttribute("fileName", s"$logHome/$logFileName.log")
          .addAttribute("filePattern", s"$logHome/archive/$logFileName-%d{yy-MM-dd HH}.log.gz")
          .add(layoutBuilder)
          .addComponent(triggeringPolicy)
      case _ => throw new NoSuchFieldException(s"Can`t find specific appender. $appenderStr")
    }
    builder.add(appender)
    rootLogger.add(builder.newAppenderRef(appender.getName))
  }

  builder.add(rootLogger)

  private[this] lazy val ctx = Configurator.initialize(builder.build())
  def getLogContext: LoggerContext = ctx
}

