package pu.fmi.masters.openbanking.configuration;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;

/**
 * This class extends the default key manager in order to override methods that
 * retrieve SSL certificates used during mutual authentication.
 */
public class CustomX509ExtendedKeyManager extends X509ExtendedKeyManager {

	private X509KeyManager defaultKeyManager;

	/**
	 * Constructor.
	 */
	public CustomX509ExtendedKeyManager(X509KeyManager inKeyManager) {
		defaultKeyManager = inKeyManager;
	}

	/**
	 * This method overrides the default way of choosing an SSL certificate for
	 * authentication.
	 */
	public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
		return "te-fe58b3c2-9315-4d0c-bdce-69d4cfc1af69";
	}

	/**
	 * This method overrides the default way of choosing an SSL certificate for
	 * authentication.
	 */
	public String chooseClientAlias(String[] strings, Principal[] prncpls, Socket socket) {
		return "te-fe58b3c2-9315-4d0c-bdce-69d4cfc1af69";
	}

	/**
	 * This method overrides the default way of choosing an SSL certificate for
	 * authentication.
	 */
	public String[] getClientAliases(String string, Principal[] prncpls) {
		return new String[] { "te-fe58b3c2-9315-4d0c-bdce-69d4cfc1af69" };
	}

	/**
	 * This method implements the default behavior from the parent class.
	 */
	public String[] getServerAliases(String string, Principal[] prncpls) {
		return defaultKeyManager.getServerAliases(string, prncpls);
	}

	/**
	 * This method implements the default behavior from the parent class.
	 */
	public String chooseServerAlias(String[] keyType, Principal[] issuers, Socket socket) {
		return defaultKeyManager.chooseClientAlias(keyType, issuers, socket);
	}

	/**
	 * This method implements the default behavior from the parent class.
	 */
	public X509Certificate[] getCertificateChain(String alias) {
		return defaultKeyManager.getCertificateChain(alias);
	}

	/**
	 * This method implements the default behavior from the parent class.
	 */
	public PrivateKey getPrivateKey(String alias) {
		return defaultKeyManager.getPrivateKey(alias);
	}

	/**
	 * This method implements the default behavior from the parent class.
	 */
	public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
		return defaultKeyManager.chooseServerAlias(keyType, issuers, socket);
	}

}
