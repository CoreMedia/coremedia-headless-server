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


  protected InterfaceType getType(String name, TypeDefinitionRegistry registry) {
    return registry.getInterfaceType(name);
  }

  @Override
  public Set<InterfaceType> getAllInterfaces(TypeDefinitionRegistry registry) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    builder.add(this);
    for (String name : getParents()) {
      builder.addAll(getType(name, registry).getAllInterfaces(registry));
    }
    return builder.build();
  }

  @Override
  public List<FieldBuilder> getFields(TypeDefinitionRegistry registry) throws InvalidTypeDefinition {
    Map<String, FieldBuilder> builderMap = Maps.newHashMap();
    for (String name : getParents()) {
      for (FieldBuilder builder : getType(name, registry).getFields(registry)) {
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
  public GraphQLOutputType build(TypeDefinitionRegistry registry) throws InvalidTypeDefinition {
    GraphQLInterfaceType.Builder builder = newInterface();
    builder.name(getName());
    List<FieldBuilder> fieldBuilders = getFields(registry);
    for (FieldBuilder fieldBuilder : fieldBuilders) {
      for (GraphQLFieldDefinition fieldDefinition : fieldBuilder.build()) {
        builder.field(fieldDefinition);
      }
    }
    builder.typeResolver(registry.getTypeResolver());
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
