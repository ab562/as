package com.as.shiro.realm;

import org.apache.shiro.authc.AuthenticationToken;



public class StatelessToken implements AuthenticationToken {

    private String clientDigest;
    private String username;


    public StatelessToken(String clientDigest, String username) {
		this.clientDigest = clientDigest;
		this.username = username;
	}

	public StatelessToken() {
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
       return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }
}
