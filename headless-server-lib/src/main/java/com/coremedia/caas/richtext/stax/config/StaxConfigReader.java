package com.coremedia.caas.richtext.stax.config;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.ConfigResource;
import com.coremedia.caas.config.reader.YamlConfigReader;
import com.coremedia.caas.richtext.stax.context.RootContext;
import com.coremedia.caas.richtext.stax.context.SimpleContext;
import com.coremedia.caas.richtext.stax.handler.context.PushContext;
import com.coremedia.caas.richtext.stax.handler.context.ReplaceAndPushContext;
import com.coremedia.caas.richtext.stax.handler.event.DefaultEventHandler;
import com.coremedia.caas.richtext.stax.handler.event.NestedEventHandler;
import com.coremedia.caas.richtext.stax.handler.matcher.SimpleTagMatcher;
import com.coremedia.caas.richtext.stax.handler.output.ElementWriter;
import com.coremedia.caas.richtext.stax.handler.output.EmptyElementWriter;
import com.coremedia.caas.richtext.stax.handler.output.ImgWriter;
import com.coremedia.caas.richtext.stax.handler.output.LinkWriter;
import com.coremedia.caas.richtext.stax.transformer.attribute.PassStyles;
import com.coremedia.caas.richtext.stax.transformer.element.ElementFromClass;
import com.coremedia.caas.richtext.stax.writer.StringWriterFactory;
import com.google.common.collect.ImmutableList;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.List;

public class StaxConfigReader extends YamlConfigReader {

  public StaxConfigReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public List<StaxTransformationConfig<?>> readTransformationConfigs() throws IOException {
    Constructor constructor = new Constructor();
    constructor.addTypeDescription(new TypeDescription(StringWriterFactory.class, new Tag("!StringWriterFactory")));
    constructor.addTypeDescription(new TypeDescription(RootContext.class, new Tag("!RootContext")));
    constructor.addTypeDescription(new TypeDescription(SimpleContext.class, new Tag("!Context")));
    constructor.addTypeDescription(new TypeDescription(DefaultEventHandler.class, new Tag("!Handler")));
    constructor.addTypeDescription(new TypeDescription(NestedEventHandler.class, new Tag("!NestedHandler")));
    constructor.addTypeDescription(new TypeDescription(ElementFromClass.class, new Tag("!ElementFromClass")));
    constructor.addTypeDescription(new TypeDescription(PassStyles.class, new Tag("!PassStyles")));
    constructor.addTypeDescription(new TypeDescription(ElementWriter.class, new Tag("!ElementWriter")));
    constructor.addTypeDescription(new TypeDescription(EmptyElementWriter.class, new Tag("!EmptyElementWriter")));
    constructor.addTypeDescription(new TypeDescription(LinkWriter.class, new Tag("!LinkWriter")));
    constructor.addTypeDescription(new TypeDescription(ImgWriter.class, new Tag("!ImgWriter")));
    constructor.addTypeDescription(new TypeDescription(PushContext.class, new Tag("!Push")));
    constructor.addTypeDescription(new TypeDescription(ReplaceAndPushContext.class, new Tag("!ReplacePush")));
    constructor.addTypeDescription(new TypeDescription(QName.class, new Tag("!QName")));
    constructor.addTypeDescription(new TypeDescription(SimpleTagMatcher.class, new Tag("!Matcher")));
    Yaml yaml = new Yaml(constructor);

    ImmutableList.Builder<StaxTransformationConfig<?>> builder = ImmutableList.builder();
    for (ConfigResource resource : getResources("richtext/*.yml")) {
      builder.add(yaml.loadAs(resource.asString(), StaxTransformationConfig.class).resolve());
    }
    return builder.build();
  }
}
