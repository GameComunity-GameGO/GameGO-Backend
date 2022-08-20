package sideproject.junior.gamego.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {

    private Long id;

    private Long boardId;

    private Long memberId;
}
