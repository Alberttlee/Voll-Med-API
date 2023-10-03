package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //Para que se encuentre disponible
public class AgendaDeConsultaService { //clase que tiene reglas de negocio

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository  medicoRepository;

    @Autowired //inyectamos, si no se agrega es null
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos){ //method que nos permitir agregar elementos a la bd
        if (pacienteRepository.findById(datos.idPaciente()).isPresent()) {//true si existe
            throw new ValidacionDeIntegridad("Este id para paciente no fue encontrado");
        }

        if (datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())) { //existsById true si se encuentra
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null,medico,paciente,datos.fecha());//medico, paciente en la bd. Buscar con repository propios

        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if (datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidadd para el medico");

        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }

}
