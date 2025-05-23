name := "text-util"

organization := "no.vedaadata"

version := "1.0.2"

scalaVersion := "3.3.4"

resolvers += "Vedaa Data Public" at "https://mymavenrepo.com/repo/UulFGWFKTwklJGmfuD8D/"

libraryDependencies += "no.vedaadata" %% "generator-util" % "0.9.5" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

publishTo := Some("Vedaa Data Public publisher" at "https://mymavenrepo.com/repo/zPAvi2SoOMk6Bj2jtxNA/")

// scalacOptions := Seq("-Xmax-inlines:200")