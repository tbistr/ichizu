/*
BSD 3-Clause License

Copyright (c) 2021, chadyuu
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package riscv

import chisel3._
import chisel3.util._
import common.Consts._
import chisel3.util.experimental.loadMemoryFromFile

class ImemPortIo extends Bundle {
  val addr = Input(UInt(WORD_LEN.W))
  val inst = Output(UInt(WORD_LEN.W))
}

class DmemPortIo extends Bundle {
  val addr = Input(UInt(WORD_LEN.W))
  val rdata = Output(UInt(WORD_LEN.W))
  val wen = Input(UInt(MEN_LEN.W))
  val wdata = Input(UInt(WORD_LEN.W))
}

class Memory extends Module {
  val io = IO(new Bundle {
    val imem = new ImemPortIo()
    val dmem = new DmemPortIo()
  })

  val mem = Mem(16384, UInt(8.W))
  loadMemoryFromFile(mem, "")
  io.imem.inst := Cat(
    mem(io.imem.addr + 3.U(WORD_LEN.W)),
    mem(io.imem.addr + 2.U(WORD_LEN.W)),
    mem(io.imem.addr + 1.U(WORD_LEN.W)),
    mem(io.imem.addr)
  )
  io.dmem.rdata := Cat(
    mem(io.dmem.addr + 3.U(WORD_LEN.W)),
    mem(io.dmem.addr + 2.U(WORD_LEN.W)),
    mem(io.dmem.addr + 1.U(WORD_LEN.W)),
    mem(io.dmem.addr)
  )

  when(io.dmem.wen === MEN_S) {
    mem(io.dmem.addr) := io.dmem.wdata(7, 0)
    mem(io.dmem.addr + 1.U(WORD_LEN.W)) := io.dmem.wdata(15, 8)
    mem(io.dmem.addr + 2.U(WORD_LEN.W)) := io.dmem.wdata(23, 16)
    mem(io.dmem.addr + 3.U(WORD_LEN.W)) := io.dmem.wdata(31, 24)
  }
}
