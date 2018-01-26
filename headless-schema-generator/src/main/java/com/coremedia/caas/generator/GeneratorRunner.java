package com.coremedia.caas.generator;

import com.coremedia.caas.generator.config.ConfigReader;
import com.coremedia.caas.generator.config.SchemaConfig;
import com.coremedia.caas.generator.config.TypeDefinition;
import com.coremedia.cap.content.ContentRepository;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SpringBootApplication
public class GeneratorRunner implements ApplicationRunner {

  private String configPath = "./config";
  private String outputPath = "./out";

  @Autowired
  private ContentRepository contentRepository;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (args.containsOption("configPath")) {
      configPath = args.getOptionValues("configPath").get(0);
    }
    if (args.containsOption("outputPath")) {
      outputPath = args.getOptionValues("outputPath").get(0);
    }
    File outputDirectory = new File(outputPath);
    if (!outputDirectory.exists()) {
      if (!outputDirectory.mkdirs()) {
        System.err.println("Cannot create output directory " + outputDirectory.getAbsolutePath());
        System.exit(-1);
      }
    }

    ConfigReader configReader = new ConfigReader(configPath, contentRepository);
    SchemaConfig schemaConfig = configReader.createConfig();

    // generate interfaces
    STGroup interfaceGroup = new STGroupFile("templates/interface.stg");
    interfaceGroup.registerRenderer(String.class, new StringRenderer());
    for (TypeDefinition type : schemaConfig.getInterfaceTypeDefinitions()) {
      ST template = interfaceGroup.getInstanceOf("interface");
      template.add("type", type);
      writeFile(type.getName(), template.render());
    }

    // generate objects
    STGroup objectGroup = new STGroupFile("templates/object.stg");
    objectGroup.registerRenderer(String.class, new StringRenderer());
    for (TypeDefinition type : schemaConfig.getObjectTypeDefinitions()) {
      ST template = objectGroup.getInstanceOf("object");
      template.add("type", type);
      writeFile(type.getName(), template.render());
    }

    // copy static interfaces and objects
    copyResources("file:" + configPath + "/types/static/**/*.yml");

    System.exit(0);
  }


  private void writeFile(String name, String data) throws IOException {
    File file = new File(outputPath + name + ".yml");
    FileOutputStream outputStream = new FileOutputStream(file);
    outputStream.write(data.getBytes());
    outputStream.flush();
    outputStream.close();
  }


  private void copyResources(String path) throws IOException {
    PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
    Resource[] resources = loader.getResources(path);
    for (Resource resource : resources) {
      InputStream inputStream = resource.getInputStream();
      OutputStream outputStream = new FileOutputStream(new File(outputPath + resource.getFilename()));
      ByteStreams.copy(inputStream, outputStream);
      outputStream.flush();
      outputStream.close();
      inputStream.close();
    }
  }


  public static void main(String[] args) throws Exception {
    SpringApplication.run(GeneratorRunner.class, args);
  }
}
