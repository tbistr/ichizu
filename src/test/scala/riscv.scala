package riscv

import chisel3._
import org.scalatest.flatspec._
import chiseltest._

class FetchTest extends AnyFlatSpec with ChiselScalatestTester {
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

class DecodeTest extends AnyFlatSpec with ChiselScalatestTester {
  "mycpu" should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.exit.peek().litToBoolean) {
        c.clock.step(1)
      }
      c.clock.step(1)
    }
  }
}

class LWTest extends AnyFlatSpec with ChiselScalatestTester {
  "mycpu" should "work through hex" in {
    test(new Top) { c =>
      while (!c.io.exit.peek().litToBoolean) {
        c.clock.step(1)
      }
      c.clock.step(1)
    }
  }
}
