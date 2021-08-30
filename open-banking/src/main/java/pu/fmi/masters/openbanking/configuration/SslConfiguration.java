package pu.fmi.masters.openbanking.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	 * This method creates a rest template instance using a client configured for
	 * mutual TLS.
	 * 
	 * @return - instance of RestTemplate to be used for requests where mutual TLS
	 *         is required.
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	@Bean
	public RestTemplate restTemplate() throws UnrecoverableKeyException, KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(createCloseableHttpClient()));
	}

	private CloseableHttpClient createCloseableHttpClient() throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException, KeyManagementException {
		return HttpClients.custom().setSSLContext(createSSLContext()).disableContentCompression().build();
	}

	private KeyStore createKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(createKeyStoreFileInputStream(), this.keyStorePass.toCharArray());
		return keyStore;
	}

	private FileInputStream createKeyStoreFileInputStream() throws FileNotFoundException, IOException {
		return new FileInputStream(this.keyStore.getFile());
	}

	private SSLContext createSSLContext() throws NoSuchAlgorithmException, KeyManagementException,
			UnrecoverableKeyException, KeyStoreException, CertificateException, FileNotFoundException, IOException {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(new KeyManager[] { createCustomX509KeyManager() },
				createTrustManagerFactory().getTrustManagers(), null);
		return sslContext;
	}

	private KeyManagerFactory createKeyManagerFactory() throws NoSuchAlgorithmException, UnrecoverableKeyException,
			KeyStoreException, CertificateException, FileNotFoundException, IOException {
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(createKeyStore(), this.keyPass.toCharArray());
		return keyManagerFactory;
	}

	private TrustManagerFactory createTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException {
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init((KeyStore) null);
		return trustManagerFactory;
	}

	private CustomX509ExtendedKeyManager createCustomX509KeyManager() throws UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, CertificateException, FileNotFoundException, IOException {
		return new CustomX509ExtendedKeyManager((X509KeyManager) (createKeyManagerFactory().getKeyManagers()[0]));
	}

}
