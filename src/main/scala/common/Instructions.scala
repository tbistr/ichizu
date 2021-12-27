// Copyright (c) 2021, chadyuu
// All rights reserved.

// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:

// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.

// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.

// 3. Neither the name of the copyright holder nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.

// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package common

import chisel3.util._

object Instructions {
  // ロード・ストア
  val LW = BitPat("b?????????????????010?????0000011")
  val SW = BitPat("b?????????????????010?????0100011")

  // 加算
  val ADD = BitPat("b0000000??????????000?????0110011")
  val ADDI = BitPat("b?????????????????000?????0010011")

  // 減算
  val SUB = BitPat("b0100000??????????000?????0110011")

  // 論理演算
  val AND = BitPat("b0000000??????????111?????0110011")
  val OR = BitPat("b0000000??????????110?????0110011")
  val XOR = BitPat("b0000000??????????100?????0110011")
  val ANDI = BitPat("b?????????????????111?????0010011")
  val ORI = BitPat("b?????????????????110?????0010011")
  val XORI = BitPat("b?????????????????100?????0010011")

  // シフト
  val SLL = BitPat("b0000000??????????001?????0110011")
  val SRL = BitPat("b0000000??????????101?????0110011")
  val SRA = BitPat("b0100000??????????101?????0110011")
  val SLLI = BitPat("b0000000??????????001?????0010011")
  val SRLI = BitPat("b0000000??????????101?????0010011")
  val SRAI = BitPat("b0100000??????????101?????0010011")

  // 比較
  val SLT = BitPat("b0000000??????????010?????0110011")
  val SLTU = BitPat("b0000000??????????011?????0110011")
  val SLTI = BitPat("b?????????????????010?????0010011")
  val SLTIU = BitPat("b?????????????????011?????0010011")

  // 条件分岐
  val BEQ = BitPat("b?????????????????000?????1100011")
  val BNE = BitPat("b?????????????????001?????1100011")
  val BLT = BitPat("b?????????????????100?????1100011")
  val BGE = BitPat("b?????????????????101?????1100011")
  val BLTU = BitPat("b?????????????????110?????1100011")
  val BGEU = BitPat("b?????????????????111?????1100011")

  // ジャンプ
  val JAL = BitPat("b?????????????????????????1101111")
  val JALR = BitPat("b?????????????????000?????1100111")

  // 即値ロード
  val LUI = BitPat("b?????????????????????????0110111")
  val AUIPC = BitPat("b?????????????????????????0010111")

  // CSR
  val CSRRW = BitPat("b?????????????????001?????1110011")
  val CSRRWI = BitPat("b?????????????????101?????1110011")
  val CSRRS = BitPat("b?????????????????010?????1110011")
  val CSRRSI = BitPat("b?????????????????110?????1110011")
  val CSRRC = BitPat("b?????????????????011?????1110011")
  val CSRRCI = BitPat("b?????????????????111?????1110011")

  // 例外
  val ECALL = BitPat("b00000000000000000000000001110011")

  // ベクトル
  val VSETVLI = BitPat("b?????????????????111?????1010111")
  val VLE = BitPat("b000000100000?????????????0000111")
  val VSE = BitPat("b000000100000?????????????0100111")
  val VADDVV = BitPat("b0000001??????????000?????1010111")

  // カスタム
  val PCNT = BitPat("b000000000000?????110?????0001011")
}
