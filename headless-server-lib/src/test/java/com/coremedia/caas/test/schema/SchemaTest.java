package com.coremedia.caas.test.schema;

import com.coremedia.caas.config.ProcessingDefinition;
import com.coremedia.caas.config.ProcessingDefinitionLoader;
import com.coremedia.caas.config.loader.ClasspathConfigResourceLoader;
import com.coremedia.cap.content.ContentRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SchemaTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Mock
  ContentRepository contentRepository;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  @Ignore
  public void loadMinimalSchema() throws IOException {

    when(contentRepository.getContentTypes()).thenReturn(Collections.emptyList());

    ProcessingDefinition minimal = new ProcessingDefinitionLoader("minimal", new ClasspathConfigResourceLoader("pd/test/minimal/"), contentRepository, applicationContext).load();

    assertTrue(minimal.getSchemaService().hasType("Content_"));
    assertTrue(minimal.getSchemaService().hasType("Content_Impl"));
  }
}
