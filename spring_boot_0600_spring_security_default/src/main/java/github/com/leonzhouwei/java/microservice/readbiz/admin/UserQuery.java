package github.com.leonzhouwei.java.microservice.readbiz.admin;

public class UserQuery {
	
	public class UserInfo {
		private final String name;
		private final String password;
		
		public UserInfo(String name, String password) {
			this.name = name;
			this.password = password;
		}
		
		public String name() {
			return name;
		}
		
		public String password() {
			return password;
		}
	}
	
	public UserInfo userInfo(String name) {
		// TODO
		return null;
	}
	
}
