package units;

public class DataMemory {
	private int[]data;
	
	public DataMemory(int size) {
		this.data = new int[size];
		
	}
	
	public void setData(int address,int data) {
		this.data[address]=data;
		
	}
	
	public int getData(int address) {
		
		return(data[address]);
		
	}
	public String toString() {
		String r="";
		for(int i=0;i<64;i++) {
			r+=i+" : "+data[i]+'\n';
		}
		return r;
	}
	
}
