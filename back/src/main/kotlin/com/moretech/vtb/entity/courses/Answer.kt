package com.moretech.vtb.entity.courses

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Answer(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        var text: String,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="question_id", nullable=false)
        var question: Question

) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Answer

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id?.hashCode() ?: 0
        }

        override fun toString(): String {
                return "Answer(id=$id)"
        }
}