package edu.mirmil.domain.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntityUpdated : BaseEntityCreated() {
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updated: LocalDateTime? = null
}
