package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.user.AuthenticationRequest;
import med.voll.api.domain.user.TokenDataDto;
import med.voll.api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static med.voll.api.util.AplicationConstants.API_AUTH_PATH;

@RestController
@RequestMapping(API_AUTH_PATH)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDataDto> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return ResponseEntity.ok(new TokenDataDto(tokenService.generateToken((UserDetails) authentication.getPrincipal())));
    }
}
