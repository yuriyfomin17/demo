package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @GetMapping
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudentId(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Long studentId, Optional<String> nameOpional, Optional<String>  emailOptional){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
        ));

        StudentChecker studentChecker = (Optional<String> parameter, String parameter2) -> parameter.isPresent() && parameter.get().length() > 0 && !Objects.equals(parameter2, parameter.get());

        if (studentChecker.notSame(nameOpional, student.getName())) nameOpional.ifPresent(student::setName);

        if (studentChecker.notSame(emailOptional, student.getEmail())) {
            Optional<Student> optionalStudent = studentRepository.findStudentByEmail(emailOptional.get());
            if (optionalStudent.isPresent()) throw new IllegalStateException("email taken");
            student.setEmail(emailOptional.get());
        }

    }

}
