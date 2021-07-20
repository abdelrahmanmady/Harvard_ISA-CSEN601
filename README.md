# Harvard_ISA-CSEN601

This project is a MIPS ISA like simulator for a pipelined implementation using java.

Components :

1. Data Memory Size: 2048 * 8
• The data memory addresses are from 0 to 2^11 − 1 (0 to 2047).
• Each memory block (row) contains 1 word which is 8 bits (1 byte).
• The data memory is word/byte addressable (1 word = 1 byte).
• The data is stored in the data memory.


2. Instruction Memory Size: 1024 * 16
• The instruction memory addresses are from 0 to 2^10 − 1 (0 to 1023).
• Each memory block (row) contains 1 word which is 16 bits (2 bytes).
• The instruction memory is word addressable.
• The program instructions are stored in the instruction memory.

3. Registers : 0-->63 General purpose Registers
               64 Program counter
               65 Status Register
               
               
4. Supported instructions: Add, Subtract, Multiply, Load Immediate, Branch if Equal Zero, And, Or, Jump Register, Shift Left Circular, Shift Right Circular, Load Byte, Store Byte
  Format ADD R1 R2----> R1=R1+R2
  
Datapath: 3 Stages 
InstructionFetch-->InstructionDecode--->InstructionExecute

Simulation: The instructions to be executed must be written in a txt file (one instruction per line) and the path to the file should be added in the simulator code.
