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
		switch(this.queryType){
		case A:
			 authS = this.auth ? "auth" : "non-auth";
			System.out.println("IP\t" + this.domain + "\t" + this.TTL + "\t" + authS);
			break;
		case NS:
			  authS = this.auth ? "auth" : "non-auth";
			System.out.println("NS\t" + this.domain + "\t" + this.TTL + "\t" + authS);
			break;
		case MX:
			 authS = this.auth ? "auth" : "non-auth";
			System.out.println("MX\t" + this.domain + "\t" + mxPref + "\t" + this.TTL + "\t" + authS);
			break;
		case CNAME:
			authS = this.auth ? "auth" : "non-auth";
			System.out.println("CNAME\t" + this.domain + "\t" + this.TTL + "\t" + authS);
			break;
		default:
			break;
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
}
