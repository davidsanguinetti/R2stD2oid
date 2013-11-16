package net.eunainter.r2std2oid;

import java.util.HashMap;

public class Skyrunner {
	
	private R2stD2oid mR2d2;
	private RestObserver mrObserver;
	
	
	// Map of requests
	private HashMap<Integer, Integer> mRequestIds;

	public void setmRequestIds(HashMap<Integer, Integer> mRequestIds) {
		this.mRequestIds = mRequestIds;
	}
	
	/*
	 * Five tag for requests.
	 * Enums are not easy to implement in switch in Java: traditional method used
	 */
	public static final class RequestTag {
		public static final int KPOSONE		= 1;
		public static final int KPOSTWO		= 2;
		public static final int KPOSTHREE	= 3;
		public static final int KPOSFOUR	= 4;
		public static final int KPOSFIVE	= 5;
		public static final int SHAREPARAMS	= 0;
	}
	
	public Skyrunner() {
		mR2d2 = new R2stD2oid();	
		
		mRequestIds = new HashMap<Integer, Integer>();
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
	
	public void sendRequest(RequestR2D2 request, Integer tag) {
		createConnection();
		mR2d2.execute(request);
		mRequestIds.put(tag, request.getId());
	}
	
	private HashMap<Integer, Integer> getRequestIds() {
		
		return mRequestIds;
	}
	
	

}
