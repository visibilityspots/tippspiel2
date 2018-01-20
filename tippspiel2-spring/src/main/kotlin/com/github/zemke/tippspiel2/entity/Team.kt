package com.github.zemke.tippspiel2.entity

import org.hibernate.validator.constraints.NotBlank
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Team(

        @Id val id: Long,
        @NotBlank val name: String,
        val squadMarketValue: String,
        @ManyToOne(optional = false) val competition: Competition
)