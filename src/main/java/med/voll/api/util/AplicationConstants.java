package med.voll.api.util;

public class AplicationConstants {

    // medicos
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;
    public static final String API_MEDICOS_PATH = API_BASE_PATH + "/medicos";
    public static final String API_PACIENTES_PATH = API_BASE_PATH + "/pacientes";
    public static final String API_AUTH_PATH = API_BASE_PATH + "/auth";
    public static final String API_CONSULTAS_PATH = API_BASE_PATH + "/consultas";

    public static final String[] SWAGGER_PATHS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };
}
