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


  public ObjectType getParent(TypeDefinitionRegistry registry) {
    if (parent != null) {
      return registry.getObjectType(getParent());
    }
    return null;
  }


  protected Set<InterfaceType> getInterfaces(TypeDefinitionRegistry registry) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    for (String name : getInterfaces()) {
      InterfaceType type = registry.getInterfaceType(name);
      builder.add(type);
    }
    return builder.build();
  }


  @Override
  public Set<InterfaceType> getAllInterfaces(TypeDefinitionRegistry registry) {
    ImmutableSet.Builder<InterfaceType> builder = ImmutableSet.builder();
    for (String name : getInterfaces()) {
      InterfaceType type = registry.getInterfaceType(name);
      builder.addAll(type.getAllInterfaces(registry));
    }
    if (parent != null) {
      builder.addAll(getParent(registry).getAllInterfaces(registry));
    }
    return builder.build();
  }


  @Override
  public List<FieldBuilder> getFields(TypeDefinitionRegistry registry) throws InvalidTypeDefinition {
    Map<String, FieldBuilder> builderMap = Maps.newHashMap();
    for (InterfaceType type : getInterfaces(registry)) {
      for (FieldBuilder builder : type.getFields(registry)) {
        builderMap.put(builder.getName(), builder);
      }
    }
    if (getParent() != null) {
      for (FieldBuilder builder : getParent(registry).getFields(registry)) {
        builderMap.put(builder.getName(), builder);
      }
    }
    for (FieldBuilder builder : getFields()) {
      builderMap.put(builder.getName(), builder);
    }
    return ImmutableList.copyOf(builderMap.values());
  }


  @Override
  public GraphQLObjectType build(TypeDefinitionRegistry registry) throws InvalidTypeDefinition {
    GraphQLObjectType.Builder builder = GraphQLObjectType.newObject();
    builder.name(getName());
    for (AbstractType interfaceType : getAllInterfaces(registry)) {
      builder.withInterface(new GraphQLTypeReference(interfaceType.getName()));
    }
    List<FieldBuilder> fieldDefinitions = getFields(registry);
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
