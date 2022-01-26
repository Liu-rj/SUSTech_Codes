package dao;

import bean.People;

public class SqlServerPeopleDao implements PeopleDao {
	@Override
	public int insertPeople(People people) {
		if(people==null ){
			System.out.println("people is null");
			return 0;
		}else{
			System.out.println("insert people into SqlServer database successfully");
			return 1;
		}
	}

	@Override
	public int updatePeople(int id) {
		System.out.println("update people in SqlServer database successfully");
		return 1;
	}

	@Override
	public int deletePeople(int id) {
		System.out.println("delete people in SqlServer database successfully");
		return 1;
	}
}
