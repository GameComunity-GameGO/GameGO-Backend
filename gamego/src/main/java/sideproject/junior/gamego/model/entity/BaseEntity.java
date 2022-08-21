package sideproject.junior.gamego.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Getter;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @CreatedDate
    public LocalDateTime createdDate;

    @LastModifiedDate
    public LocalDateTime lastModifiedDate;

    /*public ZonedDateTime createdDate;

    public ZonedDateTime lastModifiedDate;

    @PrePersist
    public void prePersist(){
        this.createdDate= ZonedDateTime.now();
        this.lastModifiedDate=ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.lastModifiedDate=ZonedDateTime.now();
    }*/
}
