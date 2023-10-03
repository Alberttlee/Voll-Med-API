package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.Direccion;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Direccion direccion
) {
    public DatosRespuestaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(), paciente.getDireccion());
    }
}
