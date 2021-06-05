package pu.fmi.masters.openbanking;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for setting up SSL context to be used by the
 * HttpClient for requests to services requiring mutual authentication.
 */
@Configuration
public class SslConfiguration {

	@Value("${server.ssl.key-store}")
	private Resource keyStore;
	@Value("${server.ssl.key-store-password}")
	private String keyStorePass;
	@Value("${server.ssl.key-password}")
	private String keyPass;

	/**
	 * This method creates a rest template instance using a client configured
	 * for mutual TLS.
	 * 
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	@Bean
	RestTemplate restTemplate() throws UnrecoverableKeyException, KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(closeableHttpClient()));
	}

	private CloseableHttpClient closeableHttpClient() throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException, KeyManagementException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream inputStream = new FileInputStream(this.keyStore.getFile());
		keyStore.load(inputStream, this.keyStorePass.toCharArray());
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init((KeyStore) null);
		keyManagerFactory.init(keyStore, this.keyPass.toCharArray());

		MyX509KeyManager customKeyManager = new MyX509KeyManager(
				(X509KeyManager) keyManagerFactory.getKeyManagers()[0]);
		sslContext.init(new KeyManager[] { customKeyManager }, trustManagerFactory.getTrustManagers(), null);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).disableContentCompression()
				.build();
		return httpClient;
	}

}
