package sideproject.junior.gamego.model.entity;

import lombok.*;
import sideproject.junior.gamego.model.dto.ImageDTO;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Images extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "images_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityBoard_id")
    private CommunityBoard communityBoard;

    private String imgURL;

    public ImageDTO toDTO(){
        return ImageDTO.builder()
                .id(this.id)
                .imgURL(this.imgURL)
                .build();
    }
}
