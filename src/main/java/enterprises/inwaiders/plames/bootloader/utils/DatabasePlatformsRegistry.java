package enterprises.inwaiders.plames.bootloader.utils;

import java.util.ArrayList;
import java.util.List;

public class DatabasePlatformsRegistry {

	private static List<DatabasePlatform> platrforms = new ArrayList<>();
	
	static {
		
		platrforms.add(new DatabasePlatform("PostgreSQL", "org.hibernate.dialect.PostgreSQL9Dialect"));
	}

	public static DatabasePlatform findByName(String name) {
		
		for(DatabasePlatform platrform : platrforms) {
			
			if(platrform.getName().equals(name)) {
				
				return platrform;
			}
		}
		
		return null;
	}
	
	public static List<DatabasePlatform> getPlatforms() {
		
		return platrforms;
	}
}
