package com.udemy.cruddemo;

import com.udemy.cruddemo.dao.AppDAO;
import com.udemy.cruddemo.entity.Course;
import com.udemy.cruddemo.entity.Instructor;
import com.udemy.cruddemo.entity.InstructorDetail;
import com.udemy.cruddemo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			createCourse(appDAO);
			addReviews(appDAO);
			findReviewsOfCourse(appDAO);
			// deleteCourseAndReviews(appDAO);
		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int id = 10;

		System.out.println("Deleting course with id: " + id);

		appDAO.deleteCourseById(id);

		System.out.println("Deleted!");
	}

	private void findReviewsOfCourse(AppDAO appDAO) {
		int id = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(id);

		System.out.println("Reviews for " + tempCourse.getTitle() + ":\n" + tempCourse.getReviews());
	}

	private void addReviews(AppDAO appDAO) {
		int courseId = 10; // Course ID

		// Create a new Review instance
		String reviewComment1 = "easy course";
		String reviewComment2 = "lecturer is weird";
		Review r1 = new Review(reviewComment1);
		Review r2 = new Review(reviewComment2);

		// Fetch the managed Course instance from the database
		Course managedCourse = appDAO.findCourseByID(courseId);

		r1.setCourse(managedCourse);
		r2.setCourse(managedCourse);

		appDAO.save(r1);
		appDAO.save(r2);

		System.out.println("Reviews added successfully!");
	}

	private void createCourse(AppDAO appDAO) {
		Course tempCourse = new Course("CS 531");
		appDAO.update(tempCourse);
	}

	private void deleteCourse(AppDAO appDAO) {
		int id = 10;

		System.out.println("Deleting course with id: " + id);
		appDAO.deleteCourseById(id);
		System.out.println("Deleted!");
	}

	private void updateCourse(AppDAO appDAO) {
		int course_id = 11;

		Course tempCourse = appDAO.findCourseByID(course_id);

		tempCourse.setTitle("CS 101");

		appDAO.update(tempCourse);

		System.out.println(tempCourse.getTitle());
	}

	private void updateInstructor(AppDAO appDAO) {
		int id = 1;

		Instructor tempInstructor = appDAO.findInstructorById(id);

		tempInstructor.setLastName("A.");

		appDAO.update(tempInstructor);

		System.out.println(tempInstructor);
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int id = 1;

		Instructor tempIns = appDAO.findInstructorByIdJoinFetch(id);

		System.out.println("Instructor: " + tempIns);

		System.out.println("Associated courses: " + tempIns.getCourses());

		System.out.println("Done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding with instructor id: " + id);

		// find instructor
		Instructor tempIns = appDAO.findInstructorById(id);
		System.out.println("Instructor is" + tempIns);

		// find courses
		System.out.println("Finding courses...");
		List<Course> courses = appDAO.findCoursesById(id);

		// associate the instructor and course
		tempIns.setCourses(courses);

		// print out double check
		System.out.println("Courses are: " + courses);
		System.out.println("Courses are: " + tempIns.getCourses());

		System.out.println("Found!");
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding with instructor id: " + id);

		Instructor tempIns = appDAO.findInstructorById(id);
		System.out.println("Instructor is" + tempIns);
		System.out.println("Associated courses: " + tempIns.getCourses());
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		// create the instructor and instructor detail
		Instructor tempInstructor = new Instructor("Burak", "Alaydin", "burakalaydin@gmail.com");
		InstructorDetail tempInstructorDetail = new InstructorDetail("Historia Civils","history");

		// set instructor detail for the instructor
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		Course tempCourse1 = new Course("CS 101");
		Course tempCourse2 = new Course("HIST 101");

		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		// save the instructor
		// NOTE: this will also save the courses because of CascadeType.PERSIST
		System.out.println("Saving instructor: " + tempInstructor);
		System.out.println("The courses: " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);
		System.out.println("Saved!");
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int id = 3;

		System.out.println("Deleting instructor detail id: " + id);

		appDAO.deleteInstructorDetailById(id);

		System.out.println("Deleted!");
	}

	private void findInstructorDetail(AppDAO appDAO) {
		int id = 2;

		InstructorDetail tempInsDet = appDAO.findInstructorDetailById(id);

		System.out.println(tempInsDet);
		System.out.println(tempInsDet.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("Deleting inst. with id: " + 1);

		appDAO.deleteInstructorById(id);

		System.out.println("Deleted!");
	}

	private void findInstructor(AppDAO appDAO) {
		int id = 1;
		System.out.println("Finding the instructor with id: " + id);

		Instructor tempInst = appDAO.findInstructorById(id);

		System.out.println(tempInst);
		System.out.println("instructor details only: " + tempInst.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
		// create the instructor and instructor detail
		Instructor tempInstructor = new Instructor("Burak", "Alaydin", "burakalaydin@gmail.com");
		InstructorDetail tempInstructorDetail = new InstructorDetail("Historia Civils","history");

		// set instructor detail for the instructor
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		// save the instructor
		// NOTE: this will also save the details object because of CascadeType.ALL
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);
		System.out.println("Done!");
	}
}
