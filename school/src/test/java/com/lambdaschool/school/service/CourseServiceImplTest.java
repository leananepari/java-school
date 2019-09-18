package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplTest {

    @Autowired
    private CourseService courseService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findCourseById()
    {
        assertEquals("Java Back End", courseService.findCourseById(4).getCoursename());
    }

    @Test
    public void deleteFound()
    {
        courseService.delete(6);
        assertEquals(5, courseService.findAll(Pageable.unpaged()).size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound()
    {
        courseService.delete(100);
        assertEquals(2, courseService.findAll(Pageable.unpaged()).size());
    }
}