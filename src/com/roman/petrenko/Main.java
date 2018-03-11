package com.roman.petrenko;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Student> list = DAOOracleImpl.getInstance().getALLStudents();
        for (Student student: list) {
            System.out.println(student);
        }
        DAOOracleImpl.getInstance().updateStudentById(42);
        DAOOracleImpl.getInstance().removeStudentById(2);
        DAOOracleImpl.getInstance().insertStudent(new Student(4, "Vasya", "SU-51"));
    }
}
