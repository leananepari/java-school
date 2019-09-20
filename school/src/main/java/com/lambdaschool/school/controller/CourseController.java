package com.lambdaschool.school.controller;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.UrlNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    // http://localhost:2019/courses/courses/?page=0&size=1
    // http://localhost:2019/courses/courses/?sort=name
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(@PageableDefault(page = 0, size = 10) Pageable pageable)
    {
        logger.info("accessed");
        List<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @GetMapping(value = "/course/{courseId}", produces = {"application/json"})
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId)
    {
        Course c = courseService.findCourseById(courseId);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        ArrayList<CountStudentsInCourses> myList = courseService.getCountStudentsInCourse();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PostMapping(value = "/course/add",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid
                                           @RequestBody
                                                   Course newCourse) throws URISyntaxException
    {
        logger.info("POST /courses/course/add accessed. NewCourse: " + newCourse);

        if (newCourse.getInstructor() == null || newCourse.getInstructor().getInstructname() == null)
        {
            return new ResponseEntity<>("Instructor is required.", HttpStatus.BAD_REQUEST);
        } else {
            newCourse = courseService.save(newCourse);

            // set the location header for the newly created resource
            HttpHeaders responseHeaders = new HttpHeaders();
            URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/course/{courseId}").buildAndExpand(newCourse.getCourseid()).toUri();
            responseHeaders.setLocation(newStudentURI);

            return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
