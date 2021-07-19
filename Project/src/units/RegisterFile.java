package units;

public class RegisterFile {
	private Register[] registers;
	public RegisterFile() {
		this.registers = new Register[66];
		this.registers[64]=new Register("PC",16);
		this.registers[65]=new Register("SREG",8);
		for(int i=0;i<64;i++)
			registers[i]=new Register("R"+i,8);
	}
	public int getRegister(String name){
		int address=0;
		for(int i=0;i<registers.length;i++) {
			if(registers[i].getName().equalsIgnoreCase(name)) {
				address=i;
				break;
			}
		}
		return address;
	}
	
	public Register getRegister(int address){
		
		return registers[address];
	}
	
	public void writeRegister(int address, int value)
	{
		registers[address].setValue(value);
	}
	
	public void setZeroFlag(char bit) {
		String sreg=String.format("%8s",Integer.toBinaryString(this.registers[65].getValue())).replace(' ', '0');		
		String s1=sreg.substring(0,7);
		String s=s1+bit;
		this.registers[65].setValue(Integer.parseInt(s,2));
	}
	
	public void setSignFlag() {
		String sreg=String.format("%8s",Integer.toBinaryString(this.registers[65].getValue())).replace(' ', '0');		
		char bit;

		if(sreg.charAt(5)=='0') {
			if(sreg.charAt(4)=='0')
				bit='0';
			else
				bit='1';
		}else {
			if(sreg.charAt(4)=='0')
				bit='1';
			else
				bit='0';
		}
			
		String s1=sreg.substring(0,6);
		char s2=sreg.charAt(7);
		String s=s1+bit+s2;
		this.registers[65].setValue(Integer.parseInt(s,2));
	}
	
	public void setNegFlag(char bit) {
		String sreg=String.format("%8s",Integer.toBinaryString(this.registers[65].getValue())).replace(' ', '0');
		String s1=sreg.substring(0,5);
		String s2=sreg.substring(6,sreg.length());
		String s=s1+bit+s2;
		this.registers[65].setValue(Integer.parseInt(s,2));
	}
	
	public void setOverflowFlag(char bit) {
		String sreg=String.format("%8s",Integer.toBinaryString(this.registers[65].getValue())).replace(' ', '0');
		String s1=sreg.substring(0,4);
		String s2=sreg.substring(5,sreg.length());
		String s=s1+bit+s2;
		this.registers[65].setValue(Integer.parseInt(s,2));
	}
	
	public void setCarryFlag(char bit) {
		String sreg=String.format("%8s",Integer.toBinaryString(this.registers[65].getValue())).replace(' ', '0');
		String s1=sreg.substring(0,3);
		String s2=sreg.substring(4,sreg.length());
		String s=s1+bit+s2;
		this.registers[65].setValue(Integer.parseInt(s,2));
	}
	
	public String toString()
	{
		String r = "";
		for(int i = 0; i <registers.length; ++i)
			r +=registers[i].toString() + "\n";
		return r;
	}
}
