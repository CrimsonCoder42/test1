package com.cscie88cfinal.spark

import scala.io.Source

object BillsAndLaws extends App{


  val filename = "src/main/resources/data/Bills and laws 1999 to 2022 - 1999to2022.csv"
  for (line <- Source.fromFile(filename).getLines) {
    val cols = line.split(",").map(_.trim)
    println(s"${cols(8)}")
  }




}
