package dnsClient;
import java.net.*;
import java.io.IOException;
import java.util.Arrays;
//import dnsClient.QueryType;
//import dnsClient.DNSRequest;


public class DNSClient {
	private int timeout = 5000;
	private int maxRetries = 3;
	private byte[] server = new byte[4];
	String address;
	private String name;
	private int port = 53;
	public QueryType queryType = QueryType.A;
	
	public static void main(String args[])throws Exception{
		try {
			DNSClient client = new DNSClient(args);
			client.makeRequest();
		} catch (Exception e){
		 System.out.println(e.getMessage());
	}

	}

	
	public DNSClient(String args[]){
		try{
			this.parseInput(args);
		} catch (Exception e){
			throw new IllegalArgumentException("ERROR\tIncorrect input: Check arguments and try again");
		}
		if (server == null || name == null){
			throw new IllegalArgumentException("ERROR\tIncorret input: Server IP and domain name must be provided.");
		}
	}
	public void makeRequest(){
		System.out.println("DNS Client sending request for " + name);
		System.out.println("Server: " + address);
		System.out.println("Request type: " + queryType);
		pollRequest(1);
	}
	private void pollRequest(int retryNumber){
		if (retryNumber > maxRetries){
			System.out.println("ERROR\tMaximum number of retries " + maxRetries+ " exceeded");
			return;
		}
	
	try {
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(timeout);
		InetAddress inetaddress = InetAddress.getByAddress(server);
		DNSRequest request = new DNSRequest(name, queryType);
		
		byte[] requestBytes = request.getRequest();
		byte[] responseBytes = new byte[1024];
		
		DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestBytes.length, inetaddress, port);
		DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
		
		long startTime = System.currentTimeMillis();
		socket.send(requestPacket); 
		socket.receive(responsePacket);
		long endTime = System.currentTimeMillis();
		socket.close();
		
		
		System.out.println("Response received after " + (endTime - startTime)/1000. + " seconds " + "(" + (retryNumber -1) + " retries");
		
		DNSResponse response = new DNSResponse(responsePacket.getData(), requestBytes.length,queryType);
		response.outputResponse();
		
	}catch (SocketTimeoutException e){
		System.out.println("ERROR\tSocket Timeout");
		System.out.println("Reattempting request...");
		pollRequest(++retryNumber);
	}catch (Exception e){
		System.out.println(e.getMessage());
	}
	}
	private void parseInput(String args[]){
		
		
		
		for(int i = 0; i<args.length; i++){
			if (args[i].equals("-t")){
				this.timeout = Integer.parseInt(args[i+1])*1000;
				i++;
			} else if (args[i].equals("-r")){
				this.maxRetries = Integer.parseInt(args[i+1]);				
				i++;
			} else if (args[i].equals("-p")){
				this.port = Integer.parseInt(args[i+1]);
				i++;				
			} else if (args[i].equals("-mx")){
				this.queryType = QueryType.MX;
				
			
			}else if (args[i].equals("-ns")){
				this.queryType = QueryType.NS;
				
			}else if(args[i].charAt(0) =='@'){
				address = args[i].substring(1);
				String[] addressComponents = address.split("\\.");
				
				for (int j = 0; j <addressComponents.length; j++){
					int ipValue = Integer.parseInt(addressComponents[j]);
					if(ipValue <0 || ipValue>255){
						throw new NumberFormatException("ERROR\tIncorrect input syntax: IP Address numbers must be between 0 and 255");
					}
					server[j] = (byte)ipValue;
					}
				 name = args[i+1];
				}
			}
		}
	
		}
		
	
