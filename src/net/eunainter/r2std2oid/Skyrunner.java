package net.eunainter.r2std2oid;

import java.util.HashMap;

public class Skyrunner {
	
	private R2stD2oid mR2d2;
	private RestObserver mrObserver;
	
	
	// Map of requests
	private HashMap<Enum, Integer> mRequestIds;
	
	public HashMap<Enum, Integer> getmRequestIds() {
		return mRequestIds;
	}

	public void setmRequestIds(HashMap<Enum, Integer> mRequestIds) {
		this.mRequestIds = mRequestIds;
	}
	
	/*
	 * Five tag for requests.
	 * Enums are not easy to implement in switch in Java: traditional method used
	 */
	public static final class RequestsTag {
		public static final int KPOSUM		= 0;
		public static final int KPOSDOIS	= 1;
		public static final int KPOSTRES	= 2;
		public static final int KPOSQUATRO	= 3;
		public static final int KPOSCINCO	= 4;
		public static final int SHAREPARAMS	= 5;
	}
	
	public Skyrunner() {
		mR2d2 = new R2stD2oid();	
		
		mRequestIds = new HashMap<Enum, Integer>();
	}
	
	public Skyrunner(RestObserver robserver) {
		this();
		this.mrObserver = robserver;
	}
	
	private void createConnection(){
		mR2d2 = new R2stD2oid();
		mR2d2.addObserver(mrObserver);
	}
	
	
	public void addObserver(RestObserver respobs) {
		mR2d2.addObserver(respobs);
	}
	
	public void sendRequest(RequestR2D2 request, Enum tag) {
		createConnection();
		mR2d2.execute(request);
		mRequestIds.put(tag, request.getId());
	}
	
	private HashMap<Enum, Integer> getRequestIds() {
		
		return mRequestIds;
	}
	
	

}
