name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies += "commons-dbutils" % "commons-dbutils" % "1.5"
libraryDependencies += "log4j" % "log4j" % "1.2.14"
libraryDependencies += "org.mockito" % "mockito-core" % "1.10.19" % "test"
libraryDependencies += "com.novocode" % "junit-interface" % "0.10-M2" % "test"
libraryDependencies += "junit" % "junit" % "4.12"
coverageEnabled := true