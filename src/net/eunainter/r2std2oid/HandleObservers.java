package net.eunainter.r2std2oid;

import java.util.ArrayList;

public class HandleObservers {
	ArrayList<RestObserver> _observers;
	
	
	public HandleObservers() {
		this._observers = new ArrayList<RestObserver>();
	}
	
	
	public void addObserver(RestObserver rObserver) {
		this._observers.add(rObserver);
	}
	
	public void removeObserver(RestObserver rObserver) {
		if (this._observers.contains(rObserver)) {
			this._observers.remove(rObserver);
		}
	}
	
	public void notifyObservers(String restResult) {
		for (RestObserver rObserver : this._observers) {
			rObserver.endConnecting();
			rObserver.receivedResponse(restResult);
		}
	}
	
	public void progressObservers() {
		for (RestObserver rObserver : this._observers)
			rObserver.startConnecting();
		
	}

}
