package com.mutant.prueba_meli.controller;

import com.mutant.prueba_meli.service.DnaService;
import com.mutant.prueba_meli.service.DnaStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(DnaController.class)
class DnaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaService dnaService;

    @MockBean
    private DnaStatistics dnaStatistics;

    @Test
    void testIsMutantReturnsOk() throws Exception {
        String[] dna = {"ATCG", "CAGT", "TTAT", "AGAC"};
        Map<String, String[]> requestBody = new HashMap<>();
        requestBody.put("dna", dna);

        when(dnaService.verifyAndSaveDna(dna)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/meli/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"ATCG\", \"CAGT\", \"TTAT\", \"AGAC\"]}"))
                .andExpect(status().isOk());
    }

    @Test
    void testIsMutantReturnsForbidden() throws Exception {
        String[] dna = {"ATCG", "CAGT", "TTAT", "AGAC"};
        Map<String, String[]> requestBody = new HashMap<>();
        requestBody.put("dna", dna);

        when(dnaService.verifyAndSaveDna(dna)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/meli/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\": [\"ATCG\", \"CAGT\", \"TTAT\", \"AGAC\"]}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetStats() throws Exception {
        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", 10L);
        stats.put("count_human_dna", 20L);
        stats.put("ratio", 0.5);

        when(dnaStatistics.getStatistics()).thenReturn(stats);

        mockMvc.perform(MockMvcRequestBuilders.get("/meli/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(10))
                .andExpect(jsonPath("$.count_human_dna").value(20))
                .andExpect(jsonPath("$.ratio").value(0.5));
    }
}
