package gtcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class OkHttpClientConfiguration {

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}
}
