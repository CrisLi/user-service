package org.chris.service.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthBody {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
