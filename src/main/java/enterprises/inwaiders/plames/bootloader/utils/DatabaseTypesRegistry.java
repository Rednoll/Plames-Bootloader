package enterprises.inwaiders.plames.bootloader.utils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTypesRegistry {

	private static List<DatabaseType> types = new ArrayList<>();
	
	static {
		
		types.add(new DatabaseType("PostgreSQL", "org.hibernate.dialect.PostgreSQL9Dialect"));
	}

	public static DatabaseType findByName(String name) {
		
		for(DatabaseType type : types) {
			
			if(type.getName().equals(name)) {
				
				return type;
			}
		}
		
		return null;
	}
	
	public static List<DatabaseType> getTypes() {
		
		return types;
	}
}
