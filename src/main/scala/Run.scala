

object Run {

  def main (args: Array[String]): Unit = {
    //read in the args and start up akka
    if (args.length != 3) {
      println(
        """You must enter the following three arguments:
          | - Directory from which to read from (eg ~/Music)
          | - Directory to create the new, smaller collection (eg ~/small-music)
          | - Maximum size of the new collection in GB (eg 32)
        """.stripMargin)
      System.exit(1)
    }
  }
}
