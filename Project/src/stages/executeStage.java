package stages;

import simulation.Simulator;

public class executeStage extends Stage{
	
	public executeStage(Simulator simulator) {
		super(simulator);
	}
	
	@Override
	public void run() {
		int firstArg=simulator.getFirstArg();
		int secondArg=simulator.getSecondArg();
		int writeReg=simulator.getWriteReg();
		String instruction=simulator.getTempFetchedInstruction();
		String opcode=instruction.substring(0,4);
		int pc=simulator.getTempPC();
		System.out.println("Instruction "+pc+" is currently executing....             Inputs : First = "+firstArg+", Second = "+secondArg+", Destination = "+writeReg);
		Integer result=0;
		byte res=0;
		String temp1="";
		String output="";
		switch(opcode) {
		case "0000":  //ADD R1 R2 ----> R1 = R1 + R2
			result=firstArg+secondArg;
			res=result.byteValue();
			//Carry flag
			temp1=String.format("%32s",Integer.toBinaryString(result)).replace(' ', '0');
			if(temp1.charAt(23)=='1') 
				simulator.getRegFile().setCarryFlag('1');
			else
				simulator.getRegFile().setCarryFlag('0');
			//OverFlow Flag
			if((firstArg>0 && secondArg>0 && res<0)||(firstArg<0 && secondArg<0 && res>0))
				simulator.getRegFile().setOverflowFlag('1');
			else
				simulator.getRegFile().setOverflowFlag('0');
			
			//Negative Flag
			if(res<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			
			//Sign Flag
			simulator.getRegFile().setSignFlag();
			
			//Zero Flag
			if(res==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			
			//Write back 
			simulator.getRegFile().getRegister(writeReg).setValue(res);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////	/////////////////////////////////////////////////////////////////////////////////////////
		case "0001":  // SUB R1 R2------> R1 = R1 - R2
			result=firstArg-secondArg;
			res=result.byteValue();
			temp1=String.format("%32s",Integer.toBinaryString(result)).replace(' ', '0');
			//Carry Flag
			if(temp1.charAt(23)=='1') 
				simulator.getRegFile().setCarryFlag('1');
			else
				simulator.getRegFile().setCarryFlag('0');
			//OverFlow Flag
			if((firstArg>0 && secondArg<0 && res<0)||(firstArg<0 && secondArg>0 && res>0))
				simulator.getRegFile().setOverflowFlag('1');
			else
				simulator.getRegFile().setOverflowFlag('0');
			//Negative Flag
			if(res<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Sign Flag
			simulator.getRegFile().setSignFlag();
			//Zero Flag
			if(res==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write back 
			simulator.getRegFile().getRegister(writeReg).setValue(res);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0010": // MUL R1 R2--------> R1 = R1 * R2
			result=firstArg*secondArg;
			res=result.byteValue();
			temp1=String.format("%32s",Integer.toBinaryString(result)).replace(' ', '0');
			//Carry Flag
			if(temp1.charAt(23)=='1') 
				simulator.getRegFile().setCarryFlag('1');
			else
				simulator.getRegFile().setCarryFlag('0');
			//Negative Flag
			if(res<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Zero Flag
			if(res==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write back 
			simulator.getRegFile().getRegister(writeReg).setValue(res);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0101": // AND R1 R2------> R1 = R1 & R2
			result=Integer.parseInt(bitwiseAnd(String.format("%8s",Integer.toBinaryString(firstArg)).replace(' ', '0'), String.format("%8s",Integer.toBinaryString(secondArg)).replace(' ', '0')),2);
			//Negative Flag
			if(result<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Zero Flag
			if(result==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(result);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0110": // OR R1 R2-------> R1 = R1 | R2
			result=Integer.parseInt(bitwiseOr(String.format("%8s",Integer.toBinaryString(firstArg)).replace(' ', '0'), String.format("%8s",Integer.toBinaryString(secondArg)).replace(' ', '0')),2);
			//Negative Flag
			if(result<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Zero Flag
			if(result==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(result);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0111": // JR R1 R2------> PC = R1 || R2
			String concatenated=String.format("%8s",Integer.toBinaryString(firstArg)).replace(' ', '0').concat(String.format("%8s",Integer.toBinaryString(secondArg)).replace(' ', '0'));
			result=Integer.parseInt(concatenated,2);
			//Write Back
			simulator.getRegFile().getRegister(simulator.getRegFile().getRegister("PC")).setValue(result);
			simulator.setFlush(true);
			//Logs
			output=simulator.getRegFile().getRegister(64).getName()+" = "+simulator.getRegFile().getRegister(64).getValue(); 
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0011":// LDI R1 IMM------> R1 = IMM
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(secondArg);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "0100":// BEQZ R1 IMM------> IF(R1==0) PC=PC+1+IMM
			if(firstArg==0) {
				result=pc+secondArg;
				simulator.setFlush(true);
			}
			else {
				result=simulator.getRegFile().getRegister(64).getValue();
			}
			//Write Back
			simulator.getRegFile().getRegister(simulator.getRegFile().getRegister("PC")).setValue(result);
			//Logs
			output=simulator.getRegFile().getRegister(64).getName()+" = "+simulator.getRegFile().getRegister(64).getValue();
			break;
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		case "1000":// SLC R1 IMM-----> R1= R1 << IMM | R1 >>> 8 - IMM
			temp1=String.format("%8s",Integer.toBinaryString(firstArg)).replace(' ','0');
			result=Integer.parseInt(temp1.substring(secondArg,temp1.length()).concat(temp1.substring(0,secondArg)),2);
			//Negative Flag
			if(result<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Zero Flag
			if(result==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(result);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "1001":// SRC R1 IMM-------> R1 = R1 >>> IMM | R1 << 8 - IMM
			temp1=String.format("%8s",Integer.toBinaryString(firstArg)).replace(' ','0');
			result=Integer.parseInt(temp1.substring(temp1.length()-secondArg,temp1.length()).concat(temp1.substring(0,temp1.length()-secondArg)),2);
			//Negative Flag
			if(result<0)
				simulator.getRegFile().setNegFlag('1');
			else
				simulator.getRegFile().setNegFlag('0');
			//Zero Flag
			if(result==0)
				simulator.getRegFile().setZeroFlag('1');
			else
				simulator.getRegFile().setZeroFlag('0');
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(result);
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "1010":// LB R1 ADDRESS------> R1 = Data[ADDRESS]
			//Write Back
			simulator.getRegFile().getRegister(writeReg).setValue(simulator.getDataMemory().getData(secondArg));
			//Logs
			output=simulator.getRegFile().getRegister(writeReg).getName()+" = "+simulator.getRegFile().getRegister(writeReg).getValue();
			break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		case "1011":// SB R1 ADDRESS-------> Data[ADDRESS] = R1
			//Write Back
			simulator.getDataMemory().setData(secondArg, firstArg);
			//Logs
			output="DataMemory["+secondArg+"] = "+simulator.getDataMemory().getData(secondArg);
			break;
		}
		System.out.println(output);
	}
	public String bitwiseAnd(String firstArg,String secondArg) {
		String output="";
		for(int i=0;i<firstArg.length();i++) {
			boolean b1;
			boolean b2;
			if(firstArg.charAt(i)=='1')
				b1=true;
			else
				b1=false;
			if(secondArg.charAt(i)=='1')
				b2=true;
			else
				b2=false;
			boolean b3=b1&b2;
			if(b3)
				output=output+'1';
			else
				output=output+'0';
			
			
		}
		return output;
	}
	
	public String bitwiseOr(String firstArg,String secondArg) {
		String output="";
		for(int i=0;i<firstArg.length();i++) {
			boolean b1;
			boolean b2;
			if(firstArg.charAt(i)=='1')
				b1=true;
			else
				b1=false;
			if(secondArg.charAt(i)=='1')
				b2=true;
			else
				b2=false;
			boolean b3=b1|b2;
			if(b3)
				output=output+'1';
			else
				output=output+'0';
			
			
		}
		return output;
		
	}
}
