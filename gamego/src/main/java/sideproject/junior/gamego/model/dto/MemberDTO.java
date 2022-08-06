package sideproject.junior.gamego.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    @Getter
    @Setter
    public static class SignUpDTO{
        @NotBlank(message = "아이디는 필수 입력값입니다")
        private String username;
        @NotBlank(message = "비밀번호는 필수 입력값입니다")
        @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        private String password;

        private Role role;

        private String nickname;
        public Member toEntity(){
            return Member.builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .role(role)
                    .build();
        }
    }

    @Getter
    public static class DeleteDTO{
        private Long id;
    }

    @Getter
    @Setter
    public static class ChangeStateDTO{
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class OnlyUsernameDTO{
        String username;
    }

    @Getter
    @Setter
    public static class SamePasswordCheckDTO{
        String password;
        String repassword;
    }
}
