package auth.dto;

import lombok.Data;

@Data
public class Response {
    private boolean successful;
    private Object data;
}