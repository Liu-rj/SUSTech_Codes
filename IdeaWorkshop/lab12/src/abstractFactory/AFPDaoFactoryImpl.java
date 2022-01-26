package abstractFactory;

import dao.ComputerDao;
import dao.PeopleDao;
import dao.StaffDao;
import utils.PropertiesReader;

import java.util.Properties;

public class AFPDaoFactoryImpl implements AFPDaoFactory {
	private static AFPDaoFactory daoFactory=new AFPDaoFactoryImpl();
	private Properties properties;
	@Override
	public StaffDao createStaffDao() {
		String className=properties.getProperty("StaffDao");
		//String className="dao."+properties.getProperty("DataBase")+"StaffDao";
		try {
			Class clz=Class.forName(className);
			return (StaffDao) clz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ComputerDao createComputerDao() {
		String className=properties.getProperty("ComputerDao");
		//String className="dao."+properties.getProperty("DataBase")+"ComputerDao";
		try {
			Class clz=Class.forName(className);
			return (ComputerDao) clz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PeopleDao createPeopleDao() {
		String className=properties.getProperty("PeopleDao");
		try {
			Class clz=Class.forName(className);
			return (PeopleDao) clz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//singleton
	private AFPDaoFactoryImpl(){
		properties= PropertiesReader.readProperties("resources.properties");
	}

	public static AFPDaoFactory getInstance(){
		return daoFactory;
	}
}
