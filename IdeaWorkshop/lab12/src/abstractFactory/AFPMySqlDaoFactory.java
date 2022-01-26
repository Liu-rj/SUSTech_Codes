package abstractFactory;

import dao.*;

public class AFPMySqlDaoFactory implements AFPDaoFactory {
	private static AFPDaoFactory instance=new AFPMySqlDaoFactory();
	@Override
	public StaffDao createStaffDao() {
		return new MysqlStaffDao();
	}

	@Override
	public ComputerDao createComputerDao() {
		return new MysqlComputerDao();
	}

	@Override
	public PeopleDao createPeopleDao() {
		return new MysqlPeopleDao();
	}


	public static AFPDaoFactory getInstance(){
		return instance;
	}

	private AFPMySqlDaoFactory(){}

}
