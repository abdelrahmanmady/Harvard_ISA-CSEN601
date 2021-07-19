package stages;

import simulation.Simulator;

public class decodeInstructions extends Stage{
	
	public decodeInstructions(Simulator simulator) {
		
		super(simulator);
		
	}
	
	@Override
	public void run() {
		String instruction=simulator.getFetchedInstruction();
		simulator.setTempFetchedInstruction(instruction);
		String opcode=instruction.substring(0,4);
		String firstArgBin=instruction.substring(4,10);
		String secondArgBin=instruction.substring(10,16);
		simulator.setWriteReg(Integer.parseInt(firstArgBin,2));
		int temp1=Integer.parseInt(firstArgBin,2);
		simulator.setFirstArg(simulator.getRegFile().getRegister(temp1).getValue());
		if(opcode.matches("0000|0001|0010|0101|0110|0111")) {
			int temp2=Integer.parseInt(secondArgBin,2);
			simulator.setSecondArg(simulator.getRegFile().getRegister(temp2).getValue());
			
		}else if(opcode.matches("0011|0100")) {
			String o=String.format("%32s", secondArgBin).replace(' ',secondArgBin.charAt(0));
			simulator.setSecondArg(Integer.parseUnsignedInt(o, 2));
			
		}else {
			simulator.setSecondArg(Integer.parseInt(secondArgBin,2));
		}
		int pc=simulator.getRegFile().getRegister(64).getValue()-1;
		simulator.setTempPC(pc);
		System.out.println("Instruction "+pc+" is currently decoding....              Inputs : Binary Instruction = "+instruction);
		
	}

}
