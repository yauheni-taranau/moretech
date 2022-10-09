package com.moretech.vtb.service.courses

import com.moretech.vtb.controller.dto.FinishCourseDto
import com.moretech.vtb.controller.dto.wallet.TransferRequest
import com.moretech.vtb.entity.TransferRubblesQueue
import com.moretech.vtb.entity.courses.Course
import com.moretech.vtb.entity.courses.CourseRepo
import com.moretech.vtb.entity.news.CourseStatus
import com.moretech.vtb.entity.news.UserCourses
import com.moretech.vtb.repository.TransferRubblesQueueRepo
import com.moretech.vtb.repository.UserCoursesRepo
import com.moretech.vtb.service.UserService
import com.moretech.vtb.service.WalletService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseService(
        private val courseRepo: CourseRepo,
        private val userService: UserService,
        private val userCoursesRepo: UserCoursesRepo,
        private val transferRubblesQueueRepo: TransferRubblesQueueRepo
) {

    fun joinCourse(id: Long, token: String): Course {
        val user = userService.decryptUser(token)
        val course = courseRepo.findById(id).get()
        userCoursesRepo.save(UserCourses(
                user = user,
                course = course,
                courseStatus = CourseStatus.IN_PROGRESS
        ))
        return course
    }

    fun checkAnswers(id: Long, finishCourseDto: FinishCourseDto, token: String): Boolean {
        val course = courseRepo.findById(id).get()
        val user = userService.decryptUser(token)
        val questionSize = course.test.questions.size
        var correctAnswers = 0
        course.test.questions.forEach {question ->
            val questionDto = finishCourseDto.questions.find { questDto -> question.id == questDto.id}!!
            if (question.correctAnswer.id == questionDto.answer.id) {
                correctAnswers++
            }
        }
        return if (questionSize == correctAnswers) {
            val userCourses = userCoursesRepo.findByCourseIdAndUserId(id, user.id!!)
            userCourses.courseStatus = CourseStatus.FINISHED
            userCoursesRepo.save(userCourses)
            transferRubblesQueueRepo.save(TransferRubblesQueue(
                    transferTo = user.nftWallet!!,
                    transferFrom = user.unit!!.nftWallet!!,
                    transferAmount = course.rewardAmount
            ))
            true
        } else {
            false
        }
    }

    fun getCourses(): List<Course> {
        return courseRepo.findAll().toList()
    }

    fun getCourse(id: Long): Course {
        return courseRepo.findById(id).get()
    }
}
