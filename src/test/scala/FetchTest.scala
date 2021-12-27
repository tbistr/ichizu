package riscv

import chisel3._
import org.scalatest._
import chiseltest._

class HexTest extends FlatSpec with ChiselScalatestTester {
  "mycpu" should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.exit.peek().litToBoolean) {
        c.clock.step(1)
      }
      // peekの評価タイミングがサンプルと少し違う。
      c.clock.step(1)
    }
  }
}
