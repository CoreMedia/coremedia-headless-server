package com.coremedia.caas.schema;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLOutputType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static graphql.schema.GraphQLInterfaceType.newInterface;

public class InterfaceType extends AbstractType {

  private List<String> parents = ImmutableList.of();


  public List<String> getParents() {
    return parents;
  }

  public void setParents(List<String> parents) {
    this.parents = parents;
  }


  protected InterfaceType getType(String name, SchemaService schemaService) {
    return schemaService.getInterfaceTypeDefinition(name);
  }

  @Override
  public Set<InterfaceType> getAllInterfaces(SchemaService schemaService) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    builder.add(this);
    for (String name : getParents()) {
      builder.addAll(getType(name, schemaService).getAllInterfaces(schemaService));
    }
    return builder.build();
  }

  @Override
  public List<FieldBuilder> getFields(SchemaService schemaService) throws InvalidTypeDefinition {
    Map<String, FieldBuilder> builderMap = Maps.newHashMap();
    for (String name : getParents()) {
      for (FieldBuilder builder : getType(name, schemaService).getFields(schemaService)) {
        builderMap.put(builder.getName(), builder);
      }
    }
    for (FieldBuilder builder : getFields()) {
      if (builderMap.containsKey(builder.getName())) {
        throw new InvalidTypeDefinition("Cannot override field " + builder.getName() + " for type " + getName());
      }
      builderMap.put(builder.getName(), builder);
    }
    return ImmutableList.copyOf(builderMap.values());
  }


  @Override
  public GraphQLOutputType build(SchemaService schemaService) throws InvalidTypeDefinition {
    GraphQLInterfaceType.Builder builder = newInterface();
    builder.name(getName());
    List<FieldBuilder> fieldBuilders = getFields(schemaService);
    for (FieldBuilder fieldBuilder : fieldBuilders) {
      for (GraphQLFieldDefinition fieldDefinition : fieldBuilder.build(schemaService)) {
        builder.field(fieldDefinition);
      }
    }
    builder.typeResolver(schemaService.getTypeResolver());
    return builder.build();
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("name", getName())
            .add("parents", getParents())
            .add("fields", getFields())
            .toString();
  }
}
