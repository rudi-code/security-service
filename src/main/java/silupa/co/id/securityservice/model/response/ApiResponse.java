package silupa.co.id.securityservice.model.response;

public class ApiResponse {
	private Boolean success;
	private String message;
	private Object payload;
	
	

	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ApiResponse(Boolean success, String message, Object payload) {
		super();
		this.success = success;
		this.message = message;
		this.payload = payload;
	}



	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}



	@Override
	public String toString() {
		return "ApiResponse [success=" + success + ", message=" + message + ", payload=" + payload + "]";
	}
	
	
	
}
