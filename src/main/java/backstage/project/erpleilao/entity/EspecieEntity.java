package backstage.project.erpleilao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "especies")
@Getter
@Setter
public class EspecieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "esp_id")
    private Long id;

    @Column(name = "esp_nome", nullable = false, length = 50, unique = true)
    private String nome;

    @Column(name = "esp_inativo", nullable = false, length = 1)
    private String inativo = "N";
}
