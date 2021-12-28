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
import common.Consts._
import common.Instructions._
import chisel3.util._

class Core extends Module {
  val io = IO(new Bundle {
    val imem = Flipped(new ImemPortIo())
    val dmem = Flipped(new DmemPortIo())
    val exit = Output(Bool())
  })

  val regfile = Mem(32, UInt(WORD_LEN.W))

  //**********************************
  // Instruction Fetch (IF) Stage

  val pc_reg = RegInit(START_ADDR)
  io.imem.addr := pc_reg
  val inst = io.imem.inst
  pc_reg := pc_reg + 4.U(WORD_LEN.W)

  //**********************************
  // Instruction Decode (ID) Stage

  val rs1_addr = inst(19, 15)
  val rs2_addr = inst(24, 20)
  val wb_addr = inst(11, 7)
  val rs1_data =
    Mux((rs1_addr =/= 0.U(WORD_LEN.U)), regfile(rs1_addr), 0.U(WORD_LEN.W))
  val rs2_data =
    Mux((rs2_addr =/= 0.U(WORD_LEN.U)), regfile(rs2_addr), 0.U(WORD_LEN.W))

  val imm_i = inst(31, 20)
  val imm_i_sext = Cat(Fill(20, imm_i(11)), imm_i)

  //**********************************
  // Execute (EX) Stage

  val alu_out = MuxCase(
    0.U(WORD_LEN.W),
    Seq(
      (inst === LW) -> (rs1_data + imm_i_sext)
    )
  )

  //**********************************
  // Memory Access Stage

  io.dmem.addr := alu_out

  //**********************************
  // Writeback (WB) Stage

  val wb_data = io.dmem.rdata
  when(inst === LW) {
    regfile(wb_addr) := wb_data
  }

  //**********************************
  // Debug
  io.exit := (inst === 0x14131211.U(WORD_LEN.W))
  printf(p"pc_reg   : 0x${Hexadecimal(pc_reg)}\n")
  printf(p"inst     : 0x${Hexadecimal(inst)}\n")
  printf(p"rs1_addr : $rs1_addr\n")
  printf(p"rs2_addr : $rs2_addr\n")
  printf(p"wb_addr  : $wb_addr\n")
  printf(p"rs1_data : 0x${Hexadecimal(rs1_data)}\n")
  printf(p"rs2_data : 0x${Hexadecimal(rs2_data)}\n")
  printf(p"wb_data   : 0x${Hexadecimal(wb_data)}\n")
  printf(p"dmem.addr : ${io.dmem.addr}\n")
  printf("---------\n")
}
