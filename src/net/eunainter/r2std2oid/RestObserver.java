package net.eunainter.r2std2oid;

public interface RestObserver {

	public void receivedResponse(ResponseR2D2 response);
	public void startConnecting();
	public void endConnecting();
}
