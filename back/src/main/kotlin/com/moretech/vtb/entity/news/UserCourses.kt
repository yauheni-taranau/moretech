package com.moretech.vtb.entity.news

import com.moretech.vtb.entity.User
import com.moretech.vtb.entity.courses.Course
import javax.persistence.*

@Entity
data class UserCourses(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @OneToOne
        @JoinColumn(name = "course_id")
        var course: Course,

        @Enumerated(EnumType.STRING)
        var courseStatus: CourseStatus
)

enum class CourseStatus {
        IN_PROGRESS, FINISHED
}