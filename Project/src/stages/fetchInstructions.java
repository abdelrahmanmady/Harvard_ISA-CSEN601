package stages;

import simulation.Simulator;

public class fetchInstructions extends Stage{
	
	public fetchInstructions(Simulator simulator) {
		super(simulator);
	}
	
	@Override
	public void run() {
		int pc=(simulator.getRegFile().getRegister(64)).getValue();
		String instruction=simulator.getInstructionMemory().getInstruction(pc);
		simulator.setFetchedInstruction(instruction);
		simulator.getRegFile().getRegister(64).setValue(pc+1);
		System.out.println("Instruction "+pc+" is currently fetching.....             Input : PC = "+pc);
	}

}
