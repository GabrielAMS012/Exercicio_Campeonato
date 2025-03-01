package gabrielAMS.ex_campeonato.campeonato.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "campeonatos_tb")
public class DomainCampeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campeonato")
    private long id_campeonato;

    @NotBlank
    @Column(name = "nome_campeonato")
    private String nomeCamp;

    @NotNull
    @Column(name = "ano_campeonato")
    private int ano;

    @Column(name = "Iniciado")
    private boolean campeonatoIniciado = false;


    @Column(name = "Finalizado")
    private boolean campeonatoFinalizado = false;

}
