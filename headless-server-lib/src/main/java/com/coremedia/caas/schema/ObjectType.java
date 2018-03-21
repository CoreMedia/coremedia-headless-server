package com.coremedia.caas.schema;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectType extends AbstractType {

  private static final Logger LOG = LoggerFactory.getLogger(ObjectType.class);


  protected String parent;
  protected List<String> interfaces = ImmutableList.of();


  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public List<String> getInterfaces() {
    return interfaces;
  }

  public void setInterfaces(List<String> interfaces) {
    this.interfaces = interfaces;
  }


  public ObjectType getParent(SchemaService schemaService) {
    if (parent != null) {
      return schemaService.getObjectTypeDefinition(getParent());
    }
    return null;
  }


  protected Set<InterfaceType> getInterfaces(SchemaService schemaService) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    for (String name : getInterfaces()) {
      InterfaceType type = schemaService.getInterfaceTypeDefinition(name);
      builder.add(type);
    }
    return builder.build();
  }


  @Override
  public Set<InterfaceType> getAllInterfaces(SchemaService schemaService) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    for (String name : getInterfaces()) {
      InterfaceType type = schemaService.getInterfaceTypeDefinition(name);
      builder.addAll(type.getAllInterfaces(schemaService));
    }
    if (parent != null) {
      builder.addAll(getParent(schemaService).getAllInterfaces(schemaService));
    }
    return builder.build();
  }


  @Override
  public List<FieldBuilder> getFields(SchemaService schemaService) throws InvalidTypeDefinition {
    Map<String, FieldBuilder> builderMap = Maps.newHashMap();
    for (InterfaceType type : getInterfaces(schemaService)) {
      for (FieldBuilder builder : type.getFields(schemaService)) {
        builderMap.put(builder.getName(), builder);
      }
    }
    if (getParent() != null) {
      for (FieldBuilder builder : getParent(schemaService).getFields(schemaService)) {
        builderMap.put(builder.getName(), builder);
      }
    }
    for (FieldBuilder builder : getFields()) {
      builderMap.put(builder.getName(), builder);
    }
    return ImmutableList.copyOf(builderMap.values());
  }


  @Override
  public GraphQLObjectType build(SchemaService schemaService) throws InvalidTypeDefinition {
    GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
    builder.name(getName());
    for (AbstractType interfaceType : getAllInterfaces(schemaService)) {
      builder.withInterface(new GraphQLTypeReference(interfaceType.getName()));
    }
    List<FieldBuilder> fieldDefinitions = getFields(schemaService);
    for (FieldBuilder definition : fieldDefinitions) {
      for (GraphQLFieldDefinition fieldDefinition : definition.build()) {
        builder.field(fieldDefinition);
      }
    }
    return builder.build();
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", getName())
            .add("parent", getParent())
            .add("interfaces", getInterfaces())
            .add("fields", getFields())
            .toString();
  }
}
