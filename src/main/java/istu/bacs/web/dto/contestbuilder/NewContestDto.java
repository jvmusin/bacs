package istu.bacs.web.dto.contestbuilder;

import istu.bacs.web.dto.DtoUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewContestDto {
    private String name;
    @DateTimeFormat(pattern = DtoUtils.DATE_TIME_FORMAT)
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = DtoUtils.DATE_TIME_FORMAT)
    private LocalDateTime finishTime;
    private List<String> problemIds;
}