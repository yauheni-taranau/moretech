package com.moretech.vtb.controller.dto

data class FinishCourseDto(
        var questions: List<QuestionDto>)

data class QuestionDto(
        var id: Long,
        var answer: AnswerDto
)

data class AnswerDto(
        var id: Long
)