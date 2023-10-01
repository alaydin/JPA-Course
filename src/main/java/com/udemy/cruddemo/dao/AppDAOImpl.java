package com.udemy.cruddemo.dao;

import com.udemy.cruddemo.entity.Course;
import com.udemy.cruddemo.entity.Instructor;
import com.udemy.cruddemo.entity.InstructorDetail;
import com.udemy.cruddemo.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {
        Instructor tempInst = entityManager.find(Instructor.class, id);

        List<Course> tempCourses = tempInst.getCourses();

        for(Course course : tempCourses) {
            course.setInstructor(null);
        }

        entityManager.remove(tempInst);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail tempDetail = entityManager.find(InstructorDetail.class, id);

        // remove the associated object reference
        // break bidirectional link
        tempDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(tempDetail);
    }

    @Override
    public List<Course> findCoursesById(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "FROM Course WHERE instructor.id = :data", Course.class);
        query.setParameter("data", id);

        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        // create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                "SELECT i FROM Instructor i JOIN FETCH i.courses WHERE i.id = :data", Instructor.class
        );
        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseByID(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course tempCourse = entityManager.find(Course.class, id);
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void save(Review review) {
        entityManager.persist(review);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {
        // create query
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                + "JOIN FETCH c.reviews "
                + "where c.id = :data", Course.class);

        query.setParameter("data", id);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

}
