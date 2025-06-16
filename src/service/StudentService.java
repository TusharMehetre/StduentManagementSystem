package service;

import java.util.List;

import model.Student;

public interface StudentService {

	
	boolean addStudent(Student student);
	boolean deleteStudent(int id);
	Student getStudentById(int id);
	List<Student> getAllStudents();
	List<Student> searchStudentsByName(String name);
	void saveToCSV(String filePath);
	void loadFromCSV(String filePath);
}
