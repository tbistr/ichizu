#!/bin/bash

UI_INSTS=(sw lw add addi sub and andi or ori xor xori sll srl sra slli srli srai slt sltu slti sltiu beq bne blt bge bltu bgeu jal jalr lui auipc)
MI_INSTS=(csr scall)

WORK_DIR=/workspaces/ichizu
RESULT=$WORK_DIR/src/test/results.txt
echo "" > $RESULT
cd $WORK_DIR

function loop_test(){
    INSTS=${!1}
    ISA=$2

    for INST in ${INSTS[@]}
    do
        echo $INST
        sed -e "s/{package}/riscv/" -e "s/{isa}/$ISA/" -e "s/{inst}/$INST/" $WORK_DIR/src/main/resources/Memory.scala > $WORK_DIR/src/main/scala/riscv/Memory.scala
        sbt "testOnly riscv.RiscvTest" >>  $RESULT
    done
}

loop_test UI_INSTS[@] "ui"
loop_test MI_INSTS[@] "mi"
