import sbt._
import org.sstudio.stonehenge.build.Resolvers
import org.sstudio.stonehenge.build.Dependencies._

// Task
lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

// Settings
lazy val projectSettings = Seq(
  organization := "org.sstudio",
  version := "0.1.0",
  scalaVersion := "2.11.11",
  resolvers := Resolvers.list,
  compileScalastyle := scalastyle.in(Compile).toTask("").value,
  (compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value
)

lazy val scoverageSettings = Seq(
  coverageMinimum := 70,
  coverageFailOnMinimum := false,
  coverageHighlighting := true
)

lazy val commonSettings = projectSettings ++ scoverageSettings

// Options
lazy val compilerOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-optimise",
  "-unchecked",
  "-language:implicitConversions"
)

// Merge Strategy
lazy val universalCustomize: Def.Setting[String => sbtassembly.MergeStrategy] =
  assemblyMergeStrategy in assembly := {
    case "META-INF/MANIFEST.MF" => MergeStrategy.discard
    case "application.conf" => MergeStrategy.concat
    case "META-INF/mailcap" => MergeStrategy.concat
    case "META-INF\\mailcap" => MergeStrategy.concat
    case PathList(ps@_*) if ps.last endsWith ".default" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".dat" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".dtd" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".xsd" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".class" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".xml" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".properties" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".txt" => MergeStrategy.concat
    case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
    case PathList(ps@_*) if ps.last endsWith ".java" => MergeStrategy.first
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }

// Dependencies
lazy val commonDeps = Seq(
  circeCore,
  circeGeneric,
  circeParse,
  doobie,
  typesafeConfig,
  jodaTime,
  log4jCore,
  log4jApi,
  log4jWeb
)

lazy val stonehengeDeps = Seq(
  akka
)

lazy val pebbleDeps = Seq()

// Projects
lazy val common = (project in file("common"))
  .settings(
    commonSettings,
    name := "common",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= commonDeps
  )

lazy val stonehenge = (project in file("stonehenge"))
  .settings(
    commonSettings,
    name := "stonehenge",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= stonehengeDeps
  ) dependsOn common

lazy val pebble = (project in file("pebble"))
  .settings(
    commonSettings,
    name := "pebble",
    scalacOptions ++= compilerOptions,
    libraryDependencies ++= pebbleDeps
  ) dependsOn common

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "root",
    scalacOptions ++= compilerOptions
  ) aggregate (common, stonehenge, pebble)

// Commands
addCommandAlias("root", ";project root")
addCommandAlias("stonehenge", ";project stonehenge")
addCommandAlias("pebble", ";project pebble")

//
//  private[this] lazy val runScalaStyle = taskKey[Unit]("testScalaStyle")
//  private[this] lazy val _organization = "org.sstudio"
//  private[this] lazy val _version = "0.1"
//  private[this] lazy val _scalaVersion = "2.11.11"
//
//  val buildSettingsWithScalaStyle: scala.Seq[sbt.Def.Setting[_]] = Defaults.coreDefaultSettings ++ Seq(
//    organization := _organization,
//    version := _version,
//    scalaVersion := _scalaVersion,
//    scalacOptions in Compile ++= Seq(
//      "-encoding",
//      "UTF-8",
//      "-optimise",
//      "-deprecation",
//      "-unchecked",
//      "-feature"
//    ),
//    runScalaStyle := {
//      scalastyle.in(Compile).toTask("").value
//    },
//    (compile in Compile) <<= (compile in Compile) dependsOn runScalaStyle
//  )
//
//  /*
//  * Disable match warning. please ALWAYS *DISABLE* it for compiler performance.
//  * https://github.com/scala/scala/pull/1134
//  * */
//  // initialize ~= { _ => sys.props("scalac.patmat.analysisBudget") = "off" }
//
//object Resolvers {
//  //val sunRepo = "Sun Maven2 Repo" at "http://download.java.net/maven/2"
//  //val newMotionRepo = "The New Motion Public Repo" at "http://nexus.thenewmotion.com/content/groups/public/"
//  private[this] val mvn2 = Resolver.url("mvn 2", url("http://central.maven.org/maven2"))
//  private[this] val sprayRepo = Resolver.url("spray repo", url("http://repo.spray.io"))
//  private[this] val typesafeRepo = Resolver.url("Typesafe Repository", url("http://repo.typesafe.com/typesafe/releases/"))
//  private[this] val conjarsRepo = Resolver.url("conjars", url("http://conjars.org/repo/"))
//  val myResolvers: Seq[Resolver] = Seq(mvn2, sprayRepo, typesafeRepo, conjarsRepo)
//}
//
//object Dependencies {
//  // spray 1.3.3 is built against Scala 2.10.5
//  // and Akka 2.3.9 as well as Scala 2.11.6 and Akka 2.3.9.
//  private[build] val akka = "com.typesafe.akka" %% "akka-actor" % "2.4.14"
//  private[build] val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.9"
//  private[build] val akkaHttpCore = "com.typesafe.akka" %% "akka-http-core" % "10.0.9"
//  private[build] val sparkCore = "org.apache.spark" %% "spark-core" % "2.1.1"
//  private[build] val sparkSql = "org.apache.spark" %% "spark-sql" % "2.1.1"
//  private[build] val json4sNative = "org.json4s" %% "json4s-native" % "3.2.11"
//  private[build] val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.2.11"
//  private[build] val log4jCore = "org.apache.logging.log4j" % "log4j-core" % "2.6.2"
//  private[build] val log4jApi = "org.apache.logging.log4j" % "log4j-api" % "2.6.2"
//  private[build] val log4jWeb = "org.apache.logging.log4j" % "log4j-web" % "2.6.2"
//  private[build] val scalaj = "org.scalaj" % "scalaj-http_2.11" % "2.3.0"
//  private[build] val zeromq = "org.zeromq" % "jeromq" % "0.3.6"
//}
//
//object MultipleModuleProjectBuild extends Build {
//
//  import Dependencies._
//  import BuildSettings._
//
//  private[this] val stonehengeDependencies = Seq(
//    akka,
//    akkaHttpCore,
//    log4jCore,
//    log4jApi,
//    log4jWeb,
//    scalaj,
//    zeromq,
//    json4sJackson,
//    json4sNative,
//    sparkCore,
//    sparkSql
//  )
//
//  private[this] val pebbleDependencies = Seq(
//
//  )
//
//  private[this] val commonDependencies = Seq(
//
//  )
//
//  private[this] val mergeStrategyCustomize = assemblyMergeStrategy in assembly := {
//    case "META-INF/MANIFEST.MF" => MergeStrategy.discard
//    case "application.conf" => MergeStrategy.concat
//    case "META-INF/mailcap" => MergeStrategy.concat
//    case PathList(ps@_*) if ps.last endsWith ".default" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".dat" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".dtd" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".xsd" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".class" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".xml" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".properties" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".txt" => MergeStrategy.concat
//    case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
//    case PathList(ps@_*) if ps.last endsWith ".java" => MergeStrategy.first
//    case x =>
//      val oldStrategy = (assemblyMergeStrategy in assembly).value
//      oldStrategy(x)
//  }
//
//  private[this] val stonehengeAssembly = Seq(
//    mergeStrategyCustomize,
//    assemblyJarName in assembly := {
//      "org.sstudio.stonehenge-" + version.value + ".jar"
//    },
//    resolvers := Resolvers.myResolvers,
//    test in assembly := {}
//  )
//
//  private[this] val pebbleAssembly = Seq(
//    mergeStrategyCustomize,
//    assemblyJarName in assembly := {
//      "org.sstudio.pebble-" + version.value + ".jar"
//    },
//    resolvers := Resolvers.myResolvers,
//    test in assembly := {}
//  )
//
//  lazy val common: sbt.Project = Project(id = "common",
//    base = file("common"),
//    settings = buildSettingsWithScalaStyle ++ Seq(libraryDependencies ++= commonDependencies)
//  )
//
//  lazy val stonehenge: sbt.Project = Project(id = "stonehenge",
//    base = file("stonehenge"),
//    settings = buildSettingsWithScalaStyle ++ Seq(libraryDependencies ++= stonehengeDependencies) ++ stonehengeAssembly
//  ) dependsOn common
//
//  lazy val pebble: sbt.Project = Project(id = "pebble",
//    base = file("pebble"),
//    settings = buildSettingsWithScalaStyle ++ Seq(libraryDependencies ++= pebbleDependencies) ++ pebbleAssembly
//  ) dependsOn common
//}
