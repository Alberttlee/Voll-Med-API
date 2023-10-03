package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(

        @NotBlank(message = "{nombre.obligatorio}")//evitar parametros nulos o blancos
        String nombre,
        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")//valide el formato de email
        String email,
        @NotBlank(message = "{phone.obligatorio}")
        String telefono,
        @NotBlank(message="{documento.obligatorio}")
        @Pattern(regexp = "\\d{4,6}",message = "{documento.invalido}") //Anotacion que define una expresion regular (solo numeros de 4 a 6 digitos por reglas de negocio)
        String documento,
        @NotNull(message = "{especialidad.obligatorio}") // por ser enum
        Especialidad especialidad,
        @NotNull(message = "{address.obligatorio}")
        @Valid
        DatosDireccion direccion
) {

}
