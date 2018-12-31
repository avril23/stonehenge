package org.sstudio.stonehenge.common

import java.util.concurrent.{ExecutorService, Executors}

import io.circe.Decoder
import io.circe.parser.decode
import scala.concurrent.ExecutionContext

object Implicits {

  implicit class JsonStringDecoder(jsonStr: String) {
    final def asObj[T: Decoder]: Option[T] = {
      decode[T](jsonStr).right.toOption
    }
  }

  implicit val commonExecutionContext = new ExecutionContext {

    val threadPool: ExecutorService = Executors.newCachedThreadPool()

    def execute(runnable: Runnable): Unit = threadPool.submit(runnable)

    def reportFailure(t: Throwable) {}
  }
}
