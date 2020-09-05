package edu.mirmil.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access
import edu.mirmil.domain.utils.CryptConverter
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
data class User(
    @field:NotNull
    val id: String,

    @field:NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    @Convert(converter = CryptConverter::class)
    val pass: String,

    @field:NotNull(groups = [Created::class])
    val name: String? = null
) : BaseEntityUpdated() {
    interface Created
}