package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.exceptions.UrlNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)
public class CourseControllerUnitTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    private List<Course> courseList;

//    private ArrayList<Course> courseList = new ArrayList<>();
//    private ArrayList<Instructor> instructList = new ArrayList<>();


    @Before
    public void setUp() throws Exception
    {
        courseList = new ArrayList<>();

        Instructor instructor1 = new Instructor("John");
        Instructor instructor2 = new Instructor("Mary");
        Instructor instructor3 = new Instructor("Anna");

        Student student1 = new Student("Melissa");
        Student student2 = new Student("Rebecca");
        Student student3 = new Student("Alice");


        Course course1 = new Course("Data Science");
        Course course2 = new Course("JavaScript");
        Course course3 = new Course("Node.js");
        Course course4 = new Course("Java Back End");
        Course course5 = new Course("Mobile IOS");
        Course course6 = new Course("Mobile Android");

        course1.setInstructor(instructor1);
        List<Student> studList = new ArrayList<>();
        studList.add(student1);
        course1.setStudents(studList);
        courseList.add(course1);

        course2.setInstructor(instructor2);
        studList.add(student2);
        course2.setStudents(studList);
        courseList.add(course2);

        course3.setInstructor(instructor3);
        studList.add(student3);
        course3.setStudents(studList);
        courseList.add(course3);

        course4.setInstructor(instructor1);
        course4.setStudents(studList);
        courseList.add(course4);

        course5.setInstructor(instructor2);
        course5.setStudents(studList);
        courseList.add(course5);

        course6.setInstructor(instructor3);
        course6.setStudents(studList);
        courseList.add(course6);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl = "/courses/courses";
//        Mockito.when(courseService.findAll(Pageable.unpaged())).thenReturn(courseList);
        Mockito.when(courseService.findAll(Mockito.any(Pageable.class))).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
//        MvcResult r = (MvcResult) mockMvc.perform(rb).andDo(MockMvcResultHandlers.print());
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        System.out.println("Expected " + er);
        System.out.println("Actual   " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

}