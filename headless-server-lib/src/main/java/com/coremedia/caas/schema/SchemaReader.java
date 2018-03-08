package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.ConfigResource;
import com.coremedia.caas.config.reader.YamlConfigReader;
import com.coremedia.caas.schema.field.common.ConstantField;
import com.coremedia.caas.schema.field.common.MetaPropertyField;
import com.coremedia.caas.schema.field.grid.PageGridField;
import com.coremedia.caas.schema.field.navigation.ContextField;
import com.coremedia.caas.schema.field.navigation.NavigationPathField;
import com.coremedia.caas.schema.field.property.AbstractPropertyField;
import com.coremedia.caas.schema.field.property.BeanPropertyField;
import com.coremedia.caas.schema.field.property.ContentPropertyField;
import com.coremedia.caas.schema.field.property.LinkPropertyField;
import com.coremedia.caas.schema.field.property.LinklistPropertyField;
import com.coremedia.caas.schema.field.property.MarkupPropertyField;
import com.coremedia.caas.schema.field.property.RichtextPropertyField;
import com.coremedia.caas.schema.field.property.StructPropertyField;
import com.coremedia.caas.schema.field.property.UriPropertyField;
import com.coremedia.caas.schema.field.settings.SettingsField;
import com.coremedia.caas.schema.type.object.StructObjectType;
import com.coremedia.cap.content.ContentRepository;
import com.google.common.collect.ImmutableList;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;

public class SchemaReader extends YamlConfigReader {

  public SchemaReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public SchemaService read(ContentRepository contentRepository) throws IOException, InvalidTypeDefinition {
    Constructor constructor = new Constructor();
    constructor.addTypeDescription(new TypeDescription(InterfaceType.class, new Tag("!InterfaceType")));
    constructor.addTypeDescription(new TypeDescription(ObjectType.class, new Tag("!ObjectType")));
    constructor.addTypeDescription(new TypeDescription(ContentPropertyField.class, new Tag("!Property")));
    constructor.addTypeDescription(new TypeDescription(AbstractPropertyField.class, new Tag("!AbstractProperty")));
    constructor.addTypeDescription(new TypeDescription(RichtextPropertyField.class, new Tag("!Richtext")));
    constructor.addTypeDescription(new TypeDescription(MarkupPropertyField.class, new Tag("!Markup")));
    constructor.addTypeDescription(new TypeDescription(SettingsField.class, new Tag("!Settings")));
    constructor.addTypeDescription(new TypeDescription(StructPropertyField.class, new Tag("!Struct")));
    constructor.addTypeDescription(new TypeDescription(UriPropertyField.class, new Tag("!Uri")));
    constructor.addTypeDescription(new TypeDescription(BeanPropertyField.class, new Tag("!Builtin")));
    constructor.addTypeDescription(new TypeDescription(ConstantField.class, new Tag("!Constant")));
    constructor.addTypeDescription(new TypeDescription(PageGridField.class, new Tag("!PageGrid")));
    constructor.addTypeDescription(new TypeDescription(LinkPropertyField.class, new Tag("!Link")));
    constructor.addTypeDescription(new TypeDescription(LinklistPropertyField.class, new Tag("!Linklist")));
    constructor.addTypeDescription(new TypeDescription(MetaPropertyField.class, new Tag("!Meta")));
    constructor.addTypeDescription(new TypeDescription(ContextField.class, new Tag("!Context")));
    constructor.addTypeDescription(new TypeDescription(NavigationPathField.class, new Tag("!NavigationPath")));
    Yaml yaml = new Yaml(constructor);

    ImmutableList.Builder<TypeDefinition> builder = ImmutableList.builder();
    for (ConfigResource resource : getResources("schema/*.yml")) {
      builder.add((TypeDefinition) yaml.load(resource.asString()));
    }
    // add builtin types
    builder.add(new StructObjectType());
    // create schema registry and service
    return new TypeDefinitionRegistry(builder.build()).createSchemaService(contentRepository);
  }
}
