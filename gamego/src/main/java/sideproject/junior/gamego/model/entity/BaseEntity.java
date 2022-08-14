package sideproject.junior.gamego.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @CreatedDate
    public LocalDateTime createdDate;

    @LastModifiedDate
    public LocalDateTime lastModifiedDate;
}
