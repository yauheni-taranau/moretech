package com.moretech.vtb.entity.courses

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Question(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        var questionText: String,

        @JsonIgnore
        @OneToOne
        @JoinColumn(name = "correct_answer_id", referencedColumnName = "id")
        var correctAnswer: Answer,

        @OneToMany(cascade = [CascadeType.ALL])
        var answers: List<Answer>,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="test_id", nullable=false)
        var test: Test
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Question

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id?.hashCode() ?: 0
        }

        override fun toString(): String {
                return "Question(id=$id)"
        }
}