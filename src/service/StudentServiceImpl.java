package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Student;

public class StudentServiceImpl implements StudentService{

	private Set<Student> studentSet;

	public StudentServiceImpl() {
	    this.studentSet = new HashSet<>();
	}

	@Override
	public boolean addStudent(Student student) {
	    for (Student s : studentSet) {
	        if (s.getEmail().equalsIgnoreCase(student.getEmail())) {
	            System.out.println("Student with email " + student.getEmail() + " already exists.");
	            return false;
	        }
	    }

	    if (studentSet.contains(student)) {
	        System.out.println("Student with ID " + student.getId() + " already exists.");
	        return false;
	    }

	    studentSet.add(student);
	    return true;
	}


	@Override
	public boolean deleteStudent(int id) {
	    Iterator<Student> iterator = studentSet.iterator();
	    while (iterator.hasNext()) {
	        Student s = iterator.next();
	        if (s.getId() == id) {
	            iterator.remove();
	            return true;
	        }
	    }
	    return false;
	}

	@Override
	public Student getStudentById(int id) {
	    for (Student s : studentSet) {
	        if (s.getId() == id) {
	            return s;
	        }
	    }
	    return null;
	}

	@Override
	public List<Student> getAllStudents() {
	    List<Student> studentList = new ArrayList<>();
	    for (Student s : studentSet) {
	        studentList.add(s);
	    }
	    return studentList;
	}

	@Override
	public List<Student> searchStudentsByName(String name) {
	    List<Student> result = new ArrayList<>();
	    for (Student s : studentSet) {
	        if (s.getName().toLowerCase().contains(name.toLowerCase())) {
	            result.add(s);
	        }
	    }
	    return result;
	}

	@Override
	public void saveToCSV(String filePath) {
	    BufferedWriter writer = null;
	    try {
	        writer = new BufferedWriter(new FileWriter(filePath,true));
	        for (Student s : studentSet) {
	        	String line = s.getId() + "," + s.getName() + "," + s.getEmail();
	            writer.write(line);
	            writer.newLine();
	        }
	        System.out.println("Data saved successfully to " + filePath);
	    } catch (IOException e) {
	        System.out.println("Failed to save data: " + e.getMessage());
	    } finally {
	        try {
	            if (writer != null) writer.close();
	        } catch (IOException e) {
	            System.out.println("Error closing writer: " + e.getMessage());
	        }
	    }
	}

	@Override
	public void loadFromCSV(String filePath) {
	    BufferedReader reader = null;
	    studentSet.clear();
	    try {
	        reader = new BufferedReader(new FileReader(filePath));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(","); // ← Fix here (was \\t)
	            if (parts.length == 3) {
	                int id = Integer.parseInt(parts[0].trim());
	                String name = parts[1].trim();
	                String email = parts[2].trim();
	                studentSet.add(new Student(id, name, email));
	            }
	        }
	        System.out.println("Data loaded successfully from " + filePath);
	    } catch (IOException e) {
	        System.out.println("Failed to load data: " + e.getMessage());
	    } finally {
	        try {
	            if (reader != null) reader.close();
	        } catch (IOException e) {
	            System.out.println("Error closing reader: " + e.getMessage());
	        }
	    }
	}


}
