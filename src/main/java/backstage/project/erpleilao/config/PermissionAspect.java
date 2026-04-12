package backstage.project.erpleilao.config;

import backstage.project.erpleilao.entity.UsuarioEntity;
import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Before("@annotation(requirePermission)")
    public void checkPermission(RequirePermission requirePermission) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioEntity usuario)) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        if (Boolean.TRUE.equals(usuario.getUsu_is_admin())) {
            return;
        }

        // Manejo tem acesso automático a LOTES:CRIAR e LOTES:VISUALIZAR — sem precisar de role
        if (Boolean.TRUE.equals(usuario.getUsu_is_manejo())
                && requirePermission.ambiente() == Ambiente.LOTES
                && (requirePermission.acao() == Acao.CRIAR || requirePermission.acao() == Acao.VISUALIZAR)) {
            return;
        }

        boolean hasPermission = usuario.getUsu_roles() != null && usuario.getUsu_roles().stream()
                .flatMap(r -> r.getPermissoes().stream())
                .anyMatch(p -> p.getAcao() == requirePermission.acao()
                        && p.getAmbiente() == requirePermission.ambiente());

        if (!hasPermission) {
            throw new AccessDeniedException("Sem permissão: "
                    + requirePermission.ambiente() + ":" + requirePermission.acao());
        }
    }
}
