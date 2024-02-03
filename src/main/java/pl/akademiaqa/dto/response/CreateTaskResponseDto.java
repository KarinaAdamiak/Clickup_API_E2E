package pl.akademiaqa.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskResponseDto {

    private String id;
    private String name;
    private String text_content ;
    private String description;
    private CreateTaskStatusResponseDto status;
    private CreateTaskCreatorResponseDto creator;


    }

