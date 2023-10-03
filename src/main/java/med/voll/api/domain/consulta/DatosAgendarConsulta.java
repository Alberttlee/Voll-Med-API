package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta( //dto para la entrada de datos y validar
        Long id,
        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future //fechas posterior a la fecha actual
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") //asignar formato de tipo
        LocalDateTime fecha,
        Especialidad especialidad
) {
}
