package com.coremedia.caas.richtext.stax.writer.intermediate;

import com.coremedia.caas.richtext.stax.writer.intermediate.eval.EvaluationContext;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Attribute;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Block;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Characters;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Element;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.EmptyElement;
import com.coremedia.caas.richtext.stax.writer.intermediate.node.Node;

public class TreeToStringVisitor implements TreeVisitor<String> {

  @Override
  public String acceptCharacters(Characters characters, EvaluationContext context) {
    return escape(characters.getData(), false);
  }

  @Override
  public String acceptElement(Element element, EvaluationContext context) {
    StringBuilder builder = new StringBuilder();
    // open element
    builder.append('<').append(element.getName(context));
    // add attributes
    for (Attribute attribute : element.getAttributes()) {
      builder.append(' ').append(attribute.getName(context)).append("=\"").append(escape(attribute.getValue(context), true)).append("\"");
    }
    // close open element
    builder.append('>');
    // add all children
    for (Node child : element.getChildren()) {
      builder.append(child.accept(this, context));
    }
    // close element
    builder.append("</").append(element.getName(context)).append('>');
    return builder.toString();
  }

  @Override
  public String acceptEmptyElement(EmptyElement emptyElement, EvaluationContext context) {
    StringBuilder builder = new StringBuilder();
    // open element
    builder.append('<').append(emptyElement.getName(context));
    // add attributes
    for (Attribute attribute : emptyElement.getAttributes()) {
      builder.append(' ').append(attribute.getName(context)).append("=\"").append(escape(attribute.getValue(context), true)).append("\"");
    }
    // close element
    builder.append("/>");
    return builder.toString();
  }

  @Override
  public String acceptBlock(Block block, EvaluationContext context) {
    EvaluationContext subcontext = context.createSubcontext();
    // handle children according to result
    switch (block.evaluate(subcontext)) {
      case INCLUDE: {
        return block.getChild().accept(this, subcontext);
      }
      case INCLUDE_INNER: {
        Node child = block.getChild();
        if (child.isElement() && !child.isEmptyElement()) {
          StringBuilder builder = new StringBuilder();
          for (Node node : ((Element) child).getChildren()) {
            builder.append(node.accept(this, subcontext));
          }
          return builder.toString();
        }
      }
    }
    // drop case: generate no output at all
    return "";
  }


  private String escape(String content, boolean escapeQuotes) {
    if (content == null) {
      throw new RuntimeException("no character data");
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0, e = content.length(); i < e; i++) {
      char ch = content.charAt(i);
      switch (ch) {
        case '"':
          if (escapeQuotes) {
            builder.append("&quot;");
          }
          else {
            builder.append('"');
          }
          break;
        case '&':
          builder.append("&amp;");
          break;
        case '<':
          builder.append("&lt;");
          break;
        case '>':
          builder.append("&gt;");
          break;
        default:
          builder.append(ch);
      }
    }
    return builder.toString();
  }
}
