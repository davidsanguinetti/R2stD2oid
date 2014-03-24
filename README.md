R2stD2oid
=========

Library to make REST calls on Android


Characteristics
--------

* Agile calls to a REST server;
* Tag each call, allowing multiple calls in the same activity;
* Implements Observable, allowing synch and asynch calls;
* 3 lines of code are enough to make a server call;
* error handling included


```java
public class LocalAgenda implements RestObserver {
	
	SkyRunner msky;
	public LocalAgenda() {
		
		// 20 sec. timeout
		mSky = new SkyRunner(20);
		
		String url = "http://myserver.com/rest/contacts.php";

		mSky.addObserver(this);
		RequestR2D2 req = HandleRequests.getRequest(url, RequestR2D2.GET);
		req.addParameter("contactId", "*"); 
		
		mSky.sendRequest(req, Skyrunner.RequestTag.KPOSONE);
	}
	
	public void receivedResponse(ResponseR2D2 response) {
		int req = mSky.getRequestIds().get(response.getId());
		
		switch (req) {
			case SkyRunner.RequestTag.KPOSONE : {
				// do something with
				JSONObject jobj = response.getJSONObj();
				break;
			}	
			// (...)
		}
	}

	// Eg.: Initialize a spinner/
	public void startConnecting() {
	}
	 // Eg.: End a spinner
	public void endConnecting(){
	}

	// Inform the user that a timeout occured | try again
	public void requestTimeout() {
	}
}
```
