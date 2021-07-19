package simulation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import stages.decodeInstructions;
import stages.executeStage;
import stages.fetchInstructions;
import units.DataMemory;
import units.InstructionMemory;
import units.RegisterFile;

public class Simulator {
	
	RegisterFile regFile;
	InstructionMemory instructionMemory;
	DataMemory dataMemory;
	
	String fetchedInstruction;
	
	int firstArg;
	int secondArg;
	int writeReg;
	
	int tempPC;
	String tempFetchedInstruction;
	boolean flush;
	ArrayList<String> toFetch;
	boolean toDecode;
	boolean toExecute;
	
	
	public Simulator() {
	
		this.regFile=new RegisterFile();
		this.instructionMemory=new InstructionMemory(1024);
		this.dataMemory=new DataMemory(2048);
		
		this.flush=false;
		this.toDecode=false;
		this.toExecute=false;
		
	}	
	
	
	public void run() throws IOException {
		fetchInstructions fetchStage=new fetchInstructions(this);
		decodeInstructions decodeStage=new decodeInstructions(this);
		executeStage executeStage=new executeStage(this);
		loadInstructions("C:\\Users\\test\\text3.txt", instructionMemory,regFile);
		
		int noOfInstructions=instructionMemory.getNumberOfInstructions();
		int noOfCycles=3+(noOfInstructions-1)*1;
		int i=1;
		int index=0;
		intializeToFetch(instructionMemory,index);

		while(i<=noOfCycles) {
			System.out.println("Clock Cycle "+i+" :");
			
			if(toExecute)
				executeStage.run();
			if(this.flush==true) {
				index=this.regFile.getRegister(64).getValue();
				intializeToFetch(instructionMemory,index);
				noOfCycles=i+(3+((toFetch.size()-1)*1));
				toExecute=false;
				toDecode=false;
				flush=false;
			}else{
				if(toDecode) {
					decodeStage.run();
					toExecute=true;
				}
				else
					toExecute=false;
				
				if(toFetch.isEmpty()==false) {
					fetchStage.run();
					toFetch.remove(toFetch.size()-1);
					toDecode=true;
				}
				else
					toDecode=false;
			}
			i++;
			System.out.println('\n');	
			
			
		}
		System.out.println(this.regFile.toString());
		System.out.println("Data Memory \n"+this.dataMemory.toString());
		System.out.println("Instruction Memory \n"+this.instructionMemory.toString());
				
	}
	
	public void loadInstructions(String path,InstructionMemory instructionMemory,RegisterFile regFile) throws IOException {
		File file = new File(path);
		  
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			int i=0;
			while ((st = br.readLine()) != null) {
				String instruction=parseInstruction(st,regFile);
				instructionMemory.setInstruction(i, instruction);
				i++;
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String parseInstruction(String instruction,RegisterFile regFile) {
		String split[]=instruction.split(" ");
		split[0].toUpperCase();
		String opcode;
		String firstArg;
		String secondArg;
		/*** Parsing the opcode ******/
		switch(split[0]) {
		case "ADD": // DEC=0
			opcode="0000";
			break;
		case "SUB": // DEC=1
			opcode="0001";
			break;
		case "MUL": // DEC=2
			opcode="0010";
			break;
		case "LDI": // DEC=3
			opcode="0011";
			break;
		case "BEQZ": // DEC=4
			opcode="0100";
			break;
		case "AND": // DEC=5
			opcode="0101";
			break;
		case "OR": // DEC=6
			opcode="0110";
			break;
		case "JR": // DEC=7
			opcode="0111";
			break;
		case "SLC": // DEC=8
			opcode="1000";
			break;
		case "SRC": // DEC=9
			opcode="1001";
			break;
		case "LB": // DEC=10
			opcode="1010";
			break;
		case "SB": // DEC=11
			opcode="1011";
			break;
		default:
			opcode=null;
		}
		
		/*** Getting the First Register Address ***/
		firstArg=String.format("%6s",Integer.toBinaryString(regFile.getRegister(split[1]))).replace(' ','0');
		
		/**** Getting the second argument ****/
		
		if (opcode.matches("0000|0001|0010|0101|0110|0111")) {
			
			secondArg=String.format("%6s",Integer.toBinaryString(regFile.getRegister(split[2]))).replace(' ','0');
		}else if(opcode.matches("0011|0100")) {
			int x=Integer.parseInt(split[2]);
			if(x<0) {
				String temp1=Integer.toBinaryString(x);
				secondArg=temp1.substring(temp1.length()-6,temp1.length());

			}else {
				secondArg=String.format("%6s",Integer.toBinaryString(Integer.parseInt(split[2]))).replace(' ','0');

			}
		}
		else {	
			secondArg=String.format("%6s",Integer.toBinaryString(Integer.parseInt(split[2]))).replace(' ','0');
		}
		/**** Construct the instruction in one BinaryString opcode|1stArg|2ndArg ****/
		String binaryInstruction=opcode+firstArg+secondArg;
		return binaryInstruction;
			
		
		
	}
	public void intializeToFetch(InstructionMemory instructionMemory,int index) {
		this.toFetch=new ArrayList<String>();
		for(int i=instructionMemory.getNumberOfInstructions()-1;i>=index;i--) {
			this.toFetch.add(instructionMemory.getInstruction(i));
		}
	}


	
	public RegisterFile getRegFile() {
		return regFile;
	}


	public InstructionMemory getInstructionMemory() {
		return instructionMemory;
	}


	public DataMemory getDataMemory() {
		return dataMemory;
	}
	public String getFetchedInstruction() {
		return fetchedInstruction;
	}
	public void setFetchedInstruction(String fetchedInstruction) {
		this.fetchedInstruction = fetchedInstruction;
	}
	public int getFirstArg() {
		return firstArg;
	}
	public int getSecondArg() {
		return secondArg;
	}
	public void setFirstArg(int firstArg) {
		this.firstArg = firstArg;
	}
	public void setSecondArg(int secondArg) {
		this.secondArg = secondArg;
	}
	public int getWriteReg() {
		return writeReg;
	}
	public void setWriteReg(int writeReg) {
		this.writeReg = writeReg;
	}
	public void setFlush(boolean flush) {
		this.flush=flush;
	}
	

	public String getTempFetchedInstruction() {
		return tempFetchedInstruction;
	}


	public void setTempFetchedInstruction(String tempFetchedInstruction) {
		this.tempFetchedInstruction = tempFetchedInstruction;
	}
	


	public int getTempPC() {
		return tempPC;
	}


	public void setTempPC(int tempPC) {
		this.tempPC = tempPC;
	}


	public static void main(String[] args) throws IOException {
		Simulator simulator=new Simulator();
		simulator.run();
	}
	
	
}
