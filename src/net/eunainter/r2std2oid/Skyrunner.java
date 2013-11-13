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

	// Request tag
	public static enum kRequests {
		POS_UM,
		POS_DOIS,
		POS_TRES,
		POS_QUATRO,
		POS_CINCO,
		SHAREPARAMS
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
	
	public HashMap<Enum, Integer> getRequestIds() {
		return mRequestIds;
	}
	

}
