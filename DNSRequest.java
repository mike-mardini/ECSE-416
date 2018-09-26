package dnsClient;
import java.nio.ByteBuffer;
import java.util.Random;
//import dnsClient.QueryType;
public class DNSRequest {
	
	private String domain;
	private QueryType type;
	public DNSRequest(String domain, QueryType type){
		this.domain = domain;
		this.type = type;
	}
	
	public byte[] getRequest(){
		int qNameLength = 0;
		String[] items = domain.split("\\.");
		for (int i =0; i< items.length; i++){
			qNameLength += items[i].length()+1; //1 byte for each character +1 for the length
		}
		ByteBuffer request = ByteBuffer.allocate(12 + qNameLength + 1 + 4); // 12 for header, QName, 1 for empty byte, 4 for QType +QClass
		request.put(createHeader(0));//Request header
		request.put(createHeader(qNameLength));//Question header
	return request.array();
	}
	private byte[] createHeader(int qNameLength){
		ByteBuffer header;
		if (qNameLength ==0){
			header = ByteBuffer.allocate(12);
			byte[] ID = new byte[2];
			new Random().nextBytes(ID);
			header.put(ID); //random ID
			header.put((byte)0x01); // message is a query, standard query, not truncated, do recursively
			header.put((byte)0x00);// Z 
			header.put((byte)0x00);//
			header.put((byte)0x01);//QD Count	
		} else {
			header = ByteBuffer.allocate(qNameLength +1 +4);
			String[] items = domain.split("\\.");
			for (int i=0;i<items.length; i++){
				header.put((byte)items[i].length());
				for(int j =0;j<items[i].length();j++){
					header.put((byte)((int)items[i].charAt(j)));
				}
			}
			header.put((byte) 0x00); //empty byte
			header.put(hexStringToByteArray("000" +hexValueFromQueryType(type)));// Query Type
			header.put((byte)0x00);//
			header.put((byte)0x0001);//Query Class
		}
		return header.array();
	}
	private char hexValueFromQueryType(QueryType type){
		char qtype;
		if (type == QueryType.A){
			qtype = '1';
		}else if (type == QueryType.NS){
			qtype = '2';
		}else {
			qtype = 'F';
		}
		return qtype; 
	}
	private static byte[] hexStringToByteArray(String s){
		int length = s.length();
		byte[]data = new byte[length/2];
		for (int i = 0; i<length; i+=2){
			data[i/2] = (byte)((Character.digit(s.charAt(i),16)<<4) + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
}
