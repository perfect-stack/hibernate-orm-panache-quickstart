package org.acme.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.Unremovable;
import io.quarkus.credentials.CredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Unremovable
@Named("my-credentials-provider")
public class MyCredentialsProvider implements CredentialsProvider {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Map<String, String> getCredentials(String credentialsProviderName) {

//		SecretValue secretValue = getSecretValue();
//		Map<String, String> properties = new HashMap<>();
//		properties.put(USER_PROPERTY_NAME, secretValue.getUsername());
//		properties.put(PASSWORD_PROPERTY_NAME, secretValue.getPassword());

		System.out.println("MyCredentialsProvider.getSecretValue() - hard-coded");
		Map<String, String> properties = new HashMap<>();
		properties.put(USER_PROPERTY_NAME, "postgres");
		properties.put(PASSWORD_PROPERTY_NAME, "wr35FCd8Cv7uVe");

		return properties;
	}

	public SecretValue getSecretValue() {
		try {
			System.out.println("MyCredentialsProvider.getSecretValue() - started");
			String secretName = "person_db";
			Region region = Region.of("ap-southeast-2");

			// Create a Secrets Manager client
			SecretsManagerClient client = SecretsManagerClient.builder()
					.region(region)
					.build();

			GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
					.secretId(secretName)
					.build();

			GetSecretValueResponse getSecretValueResponse;

			getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

			System.out.println("MyCredentialsProvider.getSecretValue() - GOT getSecretValueResponse");

			SecretValue secretValue = objectMapper.readValue(getSecretValueResponse.secretString(), SecretValue.class);

			System.out.println("MyCredentialsProvider.getSecretValue() - GOT secretValue as object");
			System.out.println(" - username: " + secretValue.getUsername());
			System.out.println(" - password: " + secretValue.getPassword().substring(0, 4) + "***...");

			return secretValue;
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			throw e;
		}
	}

	public static class SecretValue {
		private String username;
		private String password;
		private String engine;
		private String host;
		private Integer port;
		private String dbClusterIdentifier;

		public SecretValue() {
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEngine() {
			return engine;
		}

		public void setEngine(String engine) {
			this.engine = engine;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getDbClusterIdentifier() {
			return dbClusterIdentifier;
		}

		public void setDbClusterIdentifier(String dbClusterIdentifier) {
			this.dbClusterIdentifier = dbClusterIdentifier;
		}
	}
}