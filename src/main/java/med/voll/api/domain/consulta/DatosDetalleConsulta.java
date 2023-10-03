package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public record DatosDetalleConsulta( //Dto para retornar datos
        Long id,
        @JsonAlias({"id_paciente","id-paciente"}) Long idPaciente,
        Long idMedico,
        LocalDateTime fecha
) {
}
