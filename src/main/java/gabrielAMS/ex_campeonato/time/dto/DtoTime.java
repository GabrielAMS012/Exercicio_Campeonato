package gabrielAMS.ex_campeonato.time.dto;

import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "times_tb")
public class DtoTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_time")
    private long id;
    @ManyToOne
    @JoinColumn(name = "cod_time")
    private DtoTabela tabela;


}
