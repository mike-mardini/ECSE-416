package dnsClient;

public class DNSRecord {
	private int TTL, rdLength, mxPref;
	private String name, domain;
	private QueryType queryType;
	private boolean auth;
	private int byteLength;
	
	public DNSRecord(boolean auth){
		this.auth = auth;
	}
	
	public void outputRecord(){
		String authS;
		
		if (this.queryType == QueryType.A){
			 authS = this.auth ? "auth" : "non-auth";
			System.out.println("IP\t" + this.domain +"\t" + this.name + "\t" + this.TTL + "\t" + authS);
			
		}else if(this.queryType == QueryType.NS){
		
			  authS = this.auth ? "auth" : "non-auth";
			System.out.println("NS\t" + this.domain +"\t" + this.name + "\t" + this.TTL + "\t" + authS);
			
		}else if(this.queryType == QueryType.MX){
			 authS = this.auth ? "auth" : "non-auth";
			System.out.println("MX\t" + this.domain + "\t" + this.name + "\t" + mxPref + "\t" + this.TTL + "\t" + authS);
			

		}else if(this.queryType == QueryType.CNAME){
			authS = this.auth ? "auth" : "non-auth";
			System.out.println("CNAME\t" + this.domain +"\t" + this.name + "\t" + this.TTL + "\t" + authS);
			
		}else{
			
		}
		}
		
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDomain(){
		return domain;
	}
	public void setDomain(String domain){
		this.domain = domain;
	}
	public QueryType getQueryType(){
		return queryType;
	}
	public void setQueryType(QueryType queryType){
		this.queryType = queryType;
	}
	public int getTTL(){
		return TTL;
	}
	public void setTTL(int TTL){
		this.TTL = TTL;
	}
	public int getRdLength(){
		return rdLength;
	}
	public void setRdLength(int rdLength){
		this.rdLength = rdLength;
	}
	public int getMxPref(){
		return mxPref;
	}
	public void setMxPref(int mxPref){
		this.mxPref = mxPref;
	}
	public boolean isAuth(){
		return auth;
	}
	public void setAuth(boolean auth){
		this.auth = auth;
	}
	public int getByteLength(){
		return byteLength;
	}
	public void setByteLength(int byteLength){
		this.byteLength = byteLength;
	}
}
