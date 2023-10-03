package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired //Inyectamos el servicio
    private AgendaDeConsultaService service;


    @PostMapping
    @Transactional //transaction
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){ //@valid para validar los campos
        service.agendar(datos); //datos que vienen de la request
        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }

}
