package net.eunainter.r2std2oid;

public interface RestObserver {

	public void receivedResponse(String response);
	public void startConnecting();
	public void endConnecting();
}
