package github.com.leonzhouwei.java.microservice.writebiz;

import github.com.leonzhouwei.java.microservice.domain.User;

public interface UserService {
	
	public void createUserAggregate(String name, String password);
	
	public User find(String name);
	
}
