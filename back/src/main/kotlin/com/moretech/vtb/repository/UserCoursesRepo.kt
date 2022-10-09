package com.moretech.vtb.repository

import com.moretech.vtb.entity.news.UserCourses
import org.springframework.data.repository.CrudRepository

interface UserCoursesRepo : CrudRepository<UserCourses, Long> {

    fun findByCourseIdAndUserId(courseId: Long, userId: Long): UserCourses
}