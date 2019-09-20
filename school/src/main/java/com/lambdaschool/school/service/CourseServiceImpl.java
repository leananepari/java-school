package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.repository.CourseRepository;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.ResourceNotFoundException;

@Transactional
@Service(value = "courseService")
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courserepos;

    @Override
    public List<Course> findAll(Pageable pageable)
    {
        List<Course> list = new ArrayList<>();
        courserepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public ArrayList<CountStudentsInCourses> getCountStudentsInCourse()
    {
        return courserepos.getCountStudentsInCourse();
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (courserepos.findById(id).isPresent())
        {
            courserepos.deleteCourseFromStudcourses(id);
            courserepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Override
    public Course findCourseById(long id) throws ResourceNotFoundException
    {
        return courserepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public Course save(Course course) {
        Course newCourse = new Course();

        newCourse.setCoursename(course.getCoursename());
        newCourse.setInstructor(course.getInstructor());

        return courserepos.save(newCourse);
    }

}
