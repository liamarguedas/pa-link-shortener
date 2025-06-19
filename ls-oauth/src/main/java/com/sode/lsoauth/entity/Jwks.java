package com.sode.lsoauth.entity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.RSAKey;

public final class Jwks {

	private Jwks() {
	}

	public static RSAKey generateRsa() {

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048); // Key size
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to generate RSA key", ex);
		}
	}

}
