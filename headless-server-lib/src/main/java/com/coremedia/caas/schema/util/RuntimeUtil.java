package com.coremedia.caas.schema.util;

import com.coremedia.caas.schema.DirectiveDefinition;

import graphql.language.Argument;
import graphql.language.BooleanValue;
import graphql.language.Directive;
import graphql.language.Field;
import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class RuntimeUtil {

  @NotNull
  public static List<DirectiveDefinition> getFieldDirectiveDefinitions(DataFetchingEnvironment environment) {
    List<Field> fields = environment.getFields();
    // create new directive definitions from the runtime field node
    if (fields.size() == 1) {
      List<Directive> directives = fields.get(0).getDirectives();
      if (directives != null && !directives.isEmpty()) {
        return directives.stream().map(RuntimeDirectiveDefinition::new).collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }


  public static Object getArgumentValue(Argument argument) {
    Value value = argument.getValue();
    if (value instanceof BooleanValue) {
      return ((BooleanValue) value).isValue();
    }
    else if (value instanceof FloatValue) {
      return ((FloatValue) value).getValue();
    }
    else if (value instanceof IntValue) {
      return ((IntValue) value).getValue();
    }
    else if (value instanceof StringValue) {
      return ((StringValue) value).getValue();
    }
    return null;
  }


  private static class RuntimeDirectiveDefinition implements DirectiveDefinition {

    private String name;
    private Map<String, Object> arguments;

    private RuntimeDirectiveDefinition(Directive directive) {
      this.name = directive.getName();
      this.arguments = directive.getArguments().stream().collect(Collectors.toMap(Argument::getName, RuntimeUtil::getArgumentValue));
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public Map<String, Object> getArguments() {
      return arguments;
    }
  }
}
