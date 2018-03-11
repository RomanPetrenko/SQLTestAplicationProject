package com.roman.petrenko;

import java.util.List;

public interface DAOModel {
    void connect();
    void disconnect();
    List<Student> getALLStudents();

    void updateStudentById(int id);
    void removeStudentById(int id);
    void insertStudent(Student student);

}
