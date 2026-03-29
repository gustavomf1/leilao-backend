package backstage.project.erpleilao.config;

import backstage.project.erpleilao.entity.enums.Acao;
import backstage.project.erpleilao.entity.enums.Ambiente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    Acao acao();
    Ambiente ambiente();
}
