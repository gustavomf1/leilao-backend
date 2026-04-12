package backstage.project.erpleilao.entity.enums;

public enum StatusLote {
    AGUARDANDO_ESCRITORIO, // Manejo criou, aguarda validação do escritório
    AGUARDANDO_LANCE,      // Escritório aprovou, lote em leilão — escritório preenche o valor
    FINALIZADO             // Valor do lance registrado, processo encerrado
}
