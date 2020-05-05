package enterprises.inwaiders.plames.bootloader.utils;

public class DatabaseType {

	private String name = null;
	private String driverClass = null;
	
	public DatabaseType() {}
	
	public DatabaseType(String name, String driverClass) {
		
		this.name = name;
		this.driverClass = driverClass;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getDriverClass() {
		
		return this.driverClass;
	}
}
