package dao;

import bean.People;

public class MysqlPeopleDao implements PeopleDao {
	@Override
	public int insertPeople(People people) {
		if(people==null ){
			System.out.println("people is null");
			return 0;
		}else{
			System.out.println("insert people into Mysql database successfully");
			return 1;
		}
	}

	@Override
	public int updatePeople(int id) {
		System.out.println("update people in Mysql database successfully");
		return 1;
	}

	@Override
	public int deletePeople(int id) {
		System.out.println("delete people in Mysql database successfully");
		return 1;
	}
}
