package com.coremedia.caas.schema;

import com.coremedia.caas.config.loader.ConfigResourceLoader;
import com.coremedia.caas.config.reader.ConfigResource;
import com.coremedia.caas.config.reader.YamlConfigReader;
import com.coremedia.caas.schema.directive.CustomDirective;
import com.coremedia.caas.schema.directive.FieldDirective;
import com.coremedia.caas.schema.directive.FilterDirective;
import com.coremedia.caas.schema.directive.IfDirective;
import com.coremedia.caas.schema.field.common.ConstantField;
import com.coremedia.caas.schema.field.common.MetaPropertyField;
import com.coremedia.caas.schema.field.common.PropertyField;
import com.coremedia.caas.schema.field.content.model.NavigationModelField;
import com.coremedia.caas.schema.field.content.model.PageGridModelField;
import com.coremedia.caas.schema.field.content.model.SettingModelField;
import com.coremedia.caas.schema.field.content.property.AbstractPropertyField;
import com.coremedia.caas.schema.field.content.property.ContentPropertyField;
import com.coremedia.caas.schema.field.content.property.LinkPropertyField;
import com.coremedia.caas.schema.field.content.property.LinklistPropertyField;
import com.coremedia.caas.schema.field.content.property.MarkupPropertyField;
import com.coremedia.caas.schema.field.content.property.RichtextPropertyField;
import com.coremedia.caas.schema.field.content.property.StructPropertyField;
import com.coremedia.caas.schema.field.content.property.UriPropertyField;
import com.coremedia.cap.content.ContentRepository;

import com.google.common.collect.ImmutableSet;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;
import java.util.Set;

public class SchemaReader extends YamlConfigReader {

  public SchemaReader(ConfigResourceLoader resourceLoader) {
    super(resourceLoader);
  }


  public SchemaService read(ContentRepository contentRepository) throws IOException, InvalidTypeDefinition {
    Set<CustomDirective> directiveDefinitions = readCustomDirectives();
    Set<TypeDefinition> typeDefinitions = readTypeDefinitions();
    // create schema registry for internal type building and public service
    return new SchemaService(directiveDefinitions, typeDefinitions, contentRepository);
  }


  private Set<CustomDirective> readCustomDirectives() {
    // only builtin directives for now
    ImmutableSet.Builder<CustomDirective> builder = ImmutableSet.builder();
    builder.add(new IfDirective());
    builder.add(new FilterDirective());
    return builder.build();
  }

  private Set<TypeDefinition> readTypeDefinitions() throws IOException {
    Constructor constructor = new Constructor();
    constructor.addTypeDescription(new TypeDescription(InterfaceType.class, new Tag("!InterfaceType")));
    constructor.addTypeDescription(new TypeDescription(ObjectType.class, new Tag("!ObjectType")));
    constructor.addTypeDescription(new TypeDescription(ContentPropertyField.class, new Tag("!ContentProperty")));
    constructor.addTypeDescription(new TypeDescription(AbstractPropertyField.class, new Tag("!AbstractProperty")));
    constructor.addTypeDescription(new TypeDescription(RichtextPropertyField.class, new Tag("!Richtext")));
    constructor.addTypeDescription(new TypeDescription(MarkupPropertyField.class, new Tag("!Markup")));
    constructor.addTypeDescription(new TypeDescription(SettingModelField.class, new Tag("!Setting")));
    constructor.addTypeDescription(new TypeDescription(StructPropertyField.class, new Tag("!Struct")));
    constructor.addTypeDescription(new TypeDescription(UriPropertyField.class, new Tag("!Uri")));
    constructor.addTypeDescription(new TypeDescription(PropertyField.class, new Tag("!Property")));
    constructor.addTypeDescription(new TypeDescription(ConstantField.class, new Tag("!Constant")));
    constructor.addTypeDescription(new TypeDescription(PageGridModelField.class, new Tag("!PageGrid")));
    constructor.addTypeDescription(new TypeDescription(LinkPropertyField.class, new Tag("!Link")));
    constructor.addTypeDescription(new TypeDescription(LinklistPropertyField.class, new Tag("!Linklist")));
    constructor.addTypeDescription(new TypeDescription(MetaPropertyField.class, new Tag("!Meta")));
    constructor.addTypeDescription(new TypeDescription(NavigationModelField.class, new Tag("!Navigation")));
    constructor.addTypeDescription(new TypeDescription(FieldDirective.class, new Tag("!Directive")));
    Yaml yaml = new Yaml(constructor);

    ImmutableSet.Builder<TypeDefinition> builder = ImmutableSet.builder();
    for (ConfigResource resource : getResources("schema/*.yml")) {
      builder.add((TypeDefinition) yaml.load(resource.asString()));
    }
    return builder.build();
  }
}
