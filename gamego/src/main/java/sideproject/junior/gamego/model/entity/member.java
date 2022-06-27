package sideproject.junior.gamego.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
}
