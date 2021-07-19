package units;

public class Register {
	private String name;
	private int value;
	private int size;
	
	public Register(int size) {
		this.size=size;
		this.value=0;
		
	}
	
	public Register(String name,int size) {
		this.name=name;
		this.size=size;
		this.value=0;
		
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSize() {
		return size;
	}
	 
	public String toString() {
		int x=0;
		String r="";
		String temp1="";
		String temp2="";
		if(this.value<0) {
			temp1=Integer.toBinaryString(this.value);
			temp2=temp1.substring(temp1.length()-8,temp1.length());
			temp1=String.format("%32s", temp2).replace(' ',temp2.charAt(0));
			x=Integer.parseUnsignedInt(temp1,2);

		}else {
			temp2=String.format("%8s",Integer.toBinaryString(this.value)).replace(' ','0');
			x=Integer.parseInt(temp2,2);

		}
		r=getName()+" = "+temp2+", DEC = "+x;
		return r;
	}

}
