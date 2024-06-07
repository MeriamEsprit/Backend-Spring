package tn.esprit.spring.Dto.request;

import lombok.Data;

@Data
public class ChangePwdDto {
    private String oldPwd;
    private String newPwd;
}
