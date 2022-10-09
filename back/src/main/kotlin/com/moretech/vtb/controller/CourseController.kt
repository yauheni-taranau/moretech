package com.moretech.vtb.controller

import com.moretech.vtb.controller.AuthUtils.AUTH_HEADER
import com.moretech.vtb.controller.dto.FinishCourseDto
import com.moretech.vtb.entity.courses.Course
import com.moretech.vtb.service.courses.CourseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/courses")
class CourseController(
        private val courseService: CourseService
) {

    @PostMapping("/{id}/join")
    fun joinCourse(@PathVariable id: Long, @RequestHeader(value = AUTH_HEADER) token: String): Course {
        return courseService.joinCourse(id, token)
    }

    @PostMapping("/{id}/finishCourse")
    fun finishCourse(
            @PathVariable id: Long,
            @RequestHeader(value = AUTH_HEADER) token: String,
            @RequestBody finishCourseDto: FinishCourseDto
    ): Boolean {
       return courseService.checkAnswers(id, finishCourseDto, token)
    }

    @GetMapping("/{id}")
    fun getCourse(@PathVariable id: Long): Course {
        return courseService.getCourse(id)
    }

    @GetMapping
    fun getCourses(): List<Course> {
        return courseService.getCourses()
    }


}