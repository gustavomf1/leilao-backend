package backstage.project.erpleilao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import backstage.project.erpleilao.entity.UsuarioEntity;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import backstage.project.erpleilao.entity.PermissaoEntity;
import backstage.project.erpleilao.entity.RoleEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioEntity usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            List<String> roles = usuario.getUsu_roles() != null
                    ? usuario.getUsu_roles().stream().map(RoleEntity::getRole_nome).toList()
                    : List.of();

            List<String> permissoes = usuario.getUsu_roles() != null
                    ? usuario.getUsu_roles().stream()
                    .flatMap(r -> r.getPermissoes().stream())
                    .map(p -> p.getAmbiente().name() + ":" + p.getAcao().name())
                    .distinct()
                    .toList()
                    : List.of();

            return JWT.create()
                    .withIssuer("AgroLance-ERP")
                    .withSubject(usuario.getUsu_email())
                    .withClaim("nome", usuario.getUsu_nome())
                    .withClaim("tipo", usuario.getUsu_tipo().name())
                    .withClaim("isAdmin", Boolean.TRUE.equals(usuario.getUsu_is_admin()))
                    .withClaim("isManejo", Boolean.TRUE.equals(usuario.getUsu_is_manejo()))
                    .withClaim("roles", roles)
                    .withClaim("permissoes", permissoes)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("AgroLance-ERP")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}