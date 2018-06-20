package com.coremedia.caas.doc;

import com.coremedia.caas.server.CaasServletInitializer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureRestDocs(outputDir = "target/asciidoc/snippets")
@AutoConfigureMockMvc
@SpringBootTest(classes = {CaasServletInitializer.class})
public class SwaggerOpenApiTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  public void createOpenApiJson() throws Exception {
    String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");
    MvcResult mvcResult = this.mockMvc
            .perform(get("/v2/api-docs?group=caas").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    Files.createDirectories(Paths.get(outputDir));
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)) {
      writer.write(mvcResult.getResponse().getContentAsString());
    }
  }
}
