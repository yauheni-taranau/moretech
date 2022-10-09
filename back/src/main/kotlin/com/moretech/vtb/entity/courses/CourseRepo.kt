package com.moretech.vtb.entity.courses

import org.springframework.data.repository.CrudRepository

interface CourseRepo : CrudRepository<Course, Long> {
}