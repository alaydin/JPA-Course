package com.udemy.cruddemo.dao;

import com.udemy.cruddemo.entity.Course;
import com.udemy.cruddemo.entity.Instructor;
import com.udemy.cruddemo.entity.InstructorDetail;
import com.udemy.cruddemo.entity.Review;

import java.util.List;


public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);

    List<Course> findCoursesById(int id);

    Instructor findInstructorByIdJoinFetch(int id);

    void update(Instructor tempInstructor);

    void save(Course course);

    Course findCourseByID(int id);

    void update(Course tempCourse);

    void deleteCourseById(int id);

    void save(Review review);

    Course findCourseAndReviewsByCourseId(int id);

}
