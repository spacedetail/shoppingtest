package com.boot.shopping.dto;

/*import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;*/
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {
    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;

    @Email(message = "이메일 형식을 지켜주세요.")
    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    private String email;

    @Length(min = 8, max = 16, message =  "8자 이상, 16자 이하로 입력해주세요.")
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotEmpty(message = "주소는 필수 입력값입니다.")
    private String address;

}
