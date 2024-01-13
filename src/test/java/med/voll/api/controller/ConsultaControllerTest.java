package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static med.voll.api.util.AplicationConstants.API_CONSULTAS_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
    @MockBean
    private AgendaConsultaService agendaConsultaService;

    @Test
    @WithMockUser
    void should_return_http_400_when_agendar() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post(API_CONSULTAS_PATH))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @WithMockUser
    void should_return_http_200_when_agendar() throws Exception {
        Especialidade especialidade = Especialidade.CARDIOLOGIA;
        Long medicoId = 1L;
        Long pacienteId = 1L;
        LocalDateTime dataHora = LocalDateTime.now().plusHours(1);

        DadosDetalhamentoConsulta dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, medicoId, pacienteId, dataHora);
        String expected = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoConsulta).getJson();

        when(agendaConsultaService.agendar(any(DadosAgendamentoConsulta.class))).thenReturn(dadosDetalhamentoConsulta);

        MockHttpServletResponse response = mockMvc.perform(
                        post(API_CONSULTAS_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(dadosAgendamentoConsultaJson
                                        .write(new DadosAgendamentoConsulta(medicoId, especialidade, pacienteId, dataHora)).getJson()))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(expected, response.getContentAsString());
    }

    @Test
    void cancelar() {
    }
}