package edu.mirmil.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access
import edu.mirmil.domain.component.CryptConverter
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(
    val id: String,
    @JsonProperty(access = Access.WRITE_ONLY)
    @Convert(converter = CryptConverter::class)
    val pass: String,
    val name: String
) : BaseEntityUpdated()