package med.voll.api.infra.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private String token;

    public InvalidTokenException(String token) {
        super("Invalid token");
        this.token = token;
    }

}
