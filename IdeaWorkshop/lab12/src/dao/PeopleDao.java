package dao;

import bean.People;

public interface PeopleDao {
	public int insertPeople(People people);
	public int updatePeople(int id);
	public int deletePeople(int id);
}
