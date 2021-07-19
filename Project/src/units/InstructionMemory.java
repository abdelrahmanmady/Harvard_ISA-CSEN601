package units;

public class InstructionMemory {
	String[] instructions;
	int numberOfInstructions;
	
	public InstructionMemory(int size)
	{
		instructions = new String[size];
	}
	
	public String getInstruction(int index)
	{
		return instructions[index];
	}
	
	public void setInstruction(int index, String value)
	{
		instructions[index] = value;
		numberOfInstructions++;
	}
	
	public int getNumberOfInstructions()
	{
		return numberOfInstructions;
	}
	public String toString() {
		String r="";
		for(int i=0;i<numberOfInstructions;i++) {
			r+=i+" : "+instructions[i]+'\n';
		}
		return r;
	}
}
