package dnsClient;
import java.nio.ByteBuffer;
import java.util.Random;
public class DNSResponse {
	private byte[] response;
	private QueryType qType;
	private int ansCount, timeToLive, rDataLength;
	private DNSRecord[] answer;
	private boolean AA;


	
	public DNSResponse(byte[] response, int requestSize, QueryType qType){
		this.response = response;
		this.qType = qType;
		this.parseHeader();
		answer = new DNSRecord[ansCount];
		int offSet = requestSize;
		for (int i = 0; i< ansCount; i++){
				answer[i] = this.parseAnswer(offSet);
				offSet += answer[i].getByteLength();
		}
		
	}
	private DNSRecord parseAnswer(int index){
		DNSRecord result = new DNSRecord (this.AA);
		String domain;
		int countByte = index;
		
		
		
	}
	 private data getDomainFromIndex(int index){
	    	data result = new data();
	    	int wordSize = response[index];
	    	String domain = "";
	    	boolean start = true;
	    	int count = 0;
	    	while(wordSize != 0){
				if (!start){
					domain += ".";
				}
		    	if ((wordSize & 0xC0) == (int) 0xC0) {
		    		byte[] offset = { (byte) (response[index] & 0x3F), response[index + 1] };
		            ByteBuffer wrapped = ByteBuffer.wrap(offset);
		            domain += getDomainFromIndex(wrapped.getShort()).getDomain();
		            index += 2;
		            count +=2;
		            wordSize = 0;
		    	}else{
		    		domain += getWordFromIndex(index);
		    		index += wordSize + 1;
		    		count += wordSize + 1;
		    		wordSize = response[index];
		    	}
	            start = false;
	            
	    	}
	    	result.setDomain(domain);
	    	result.setBytes(count);
	    	return result;
	    }
	    private String getWordFromIndex(int index){
	    	String word = "";
	    	int wordSize = response[index];
	    	for(int i =0; i < wordSize; i++){
	    		word += (char) response[index + i + 1];
			}
	    	return word;
	    }
	public void outputResponse(){
		System.out.println();
		if(this.ansCount <= 0){
			System.out.println("NOT FOUND");
			return;
		}
		System.out.println("***Answer Section (" + this.ansCount + " answerRecords)***");
		outputRecords();
		
	}
	private void outputRecords(){
		System.out.println();
		
		
	}
	private void parseHeader(){
		byte[] ansCount = { response[6], response[7]};
		ByteBuffer wrapped = ByteBuffer.wrap(ansCount);
		this.ansCount = wrapped.getShort();
		
		this.AA = getBit(response[2],2)==1;
	}
	private int getBit(byte b, int position){
		return (b >> position) & 1;
	}
}
