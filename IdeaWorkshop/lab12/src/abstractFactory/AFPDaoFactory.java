package abstractFactory;

import dao.ComputerDao;
import dao.PeopleDao;
import dao.StaffDao;

public interface AFPDaoFactory {
	public StaffDao createStaffDao();
	public ComputerDao createComputerDao();
	public PeopleDao createPeopleDao();
}
