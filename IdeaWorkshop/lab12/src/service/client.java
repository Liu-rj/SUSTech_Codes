package service;

import abstractFactory.AFPDaoFactory;
import abstractFactory.AFPDaoFactoryImpl;
import abstractFactory.AFPMySqlDaoFactory;
import bean.Computer;
import bean.People;
import bean.Staff;
import dao.*;

import java.util.InputMismatchException;
import java.util.Scanner;
//存储staff的DB和存储computer的DB是成套的（都是mysql或者都是sqlserver），不能说一个是mysql一个sqlserver
//因此，我们在创建dao的时候，用相同的参数使得两者的db成套，但一旦参数错误，就会导致db不成套
//所以，为了从源头上避免这个问题，可以使用一个超级工厂。
public class client {
    public static void main(String [] args){
        //origin
//        StaffFactory staffFactory=new StaffFactory();
//        ComputerFactory computerFactory=new ComputerFactory();
//
//        StaffDao staffDao=staffFactory.createStaffDao(1);
//        ComputerDao computerDao=computerFactory.createComputerDao(1);
//
//        test(staffDao,computerDao);

        //Task1
//        AFPDaoFactory mysqlFac=AFPMySqlDaoFactory.getInstance();
//        StaffDao staffDao=mysqlFac.createStaffDao();
//        ComputerDao computerDao=mysqlFac.createComputerDao();
//        test(staffDao,computerDao);

        //Task2
        AFPDaoFactory factory= AFPDaoFactoryImpl.getInstance();
        StaffDao staffDao=factory.createStaffDao();
        ComputerDao computerDao=factory.createComputerDao();
        PeopleDao peopleDao=factory.createPeopleDao();
        test(staffDao,computerDao,peopleDao);
    }

    public static void test(StaffDao staffDao, ComputerDao computerDao, PeopleDao peopleDao){
        Scanner input=new Scanner(System.in);
        int op=-1;
        do{
            try{
                op=input.nextInt();
                switch (op){
                    case 1:
                        staffDao.insertStaff(new Staff());
                        break;
                    case 2:
                        staffDao.updateStaff(1);
                        break;
                    case 3:
                        staffDao.deleteStaff(1);
                        break;
                    case 4:
                        computerDao.insertComputer(new Computer(1));
                        break;
                    case 5:
                        computerDao.updateComputer(1);
                        break;
                    case 6:
                        computerDao.deleteComputer(1);
                        break;
                    case 7:
                        peopleDao.insertPeople(new People(1));
                        break;
                    case 8:
                        peopleDao.updatePeople(1);
                        break;
                    case 9:
                        peopleDao.deletePeople(1);
                        break;

                }
            }catch(InputMismatchException e){
                System.out.println("Exception "+e);
            }
        }while(op!=0);
    }
}
