package tn.esprit.spring.Dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class JustificationDTO {
    private Long idJustification;
    private String name;
    private String reason;
    private int status;
    private Date submissionDate;
    private Date validationDate;
    private String filePath;
    private List<Long> presenceIds;
}
