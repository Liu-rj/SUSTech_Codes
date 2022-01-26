package abstractFactory;

import dao.*;

public class AFPSqlServerDaoFactory implements AFPDaoFactory {
	private static AFPDaoFactory instance=new AFPSqlServerDaoFactory();
	@Override
	public StaffDao createStaffDao() {
		return new SqlServerStaffDao();
	}

	@Override
	public ComputerDao createComputerDao() {
		return new SqlServerComputerDao();
	}

	@Override
	public PeopleDao createPeopleDao() {
		return new SqlServerPeopleDao();
	}

	public static AFPDaoFactory getInstance(){
		return instance;
	}

	private AFPSqlServerDaoFactory(){}

}
