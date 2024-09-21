package br.com.AuthenticationMicrosservice;

import br.com.AuthenticationMicrosservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthenticationMicrosserviceApplicationTests {

	@Mock
	private UserRepository userRepository;
	@Test
	void contextLoads() {
	}

}
