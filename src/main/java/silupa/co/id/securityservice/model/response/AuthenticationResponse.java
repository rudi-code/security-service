package silupa.co.id.securityservice.model.response;

public class AuthenticationResponse {
	private String accessToken;
	private String tokenType;
	
	public AuthenticationResponse(String accessToken, String tokenType) {
		this.accessToken=accessToken;
		this.tokenType=tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
