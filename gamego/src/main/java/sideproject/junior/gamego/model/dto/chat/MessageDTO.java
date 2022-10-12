package sideproject.junior.gamego.model.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO<T> {

    private int messageType; // 1 = message, 2 = member(입장)

    private T data;
}
