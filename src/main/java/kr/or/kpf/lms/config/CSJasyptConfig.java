package kr.or.kpf.lms.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Jasypt Config
 */
@Configuration
public class CSJasyptConfig {

	private static final Logger logger = LoggerFactory.getLogger(CSJasyptConfig.class);

	@Autowired
	private Environment environment;

	private static final String ALGORITHM = "PBEWithMD5AndDES";
	private static final String POOL_SIZE = "1";
	private static final String KEY_OBTENTION_ITERATIONS = "1000";
	private static final String STRING_OUTPUT_TYPE = "base64";

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();

		config.setPassword(environment.getProperty("jasypt.encryptor.password")); // 암호화 키
		config.setAlgorithm(ALGORITHM);
		config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS);
		config.setPoolSize(POOL_SIZE);
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType(STRING_OUTPUT_TYPE);

		encryptor.setConfig(config);

		return encryptor;
	}
}