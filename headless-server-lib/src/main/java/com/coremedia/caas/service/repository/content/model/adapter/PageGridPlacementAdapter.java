package com.coremedia.caas.service.repository.content.model.adapter;

import com.coremedia.blueprint.base.pagegrid.ContentBackedPageGridPlacement;
import com.coremedia.blueprint.base.pagegrid.PageGridContentKeywords;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.repository.content.ContentProxy;
import com.coremedia.cap.common.CapStructHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.coremedia.caas.service.repository.content.util.ContentUtil.toZonedDateTime;

public class PageGridPlacementAdapter {

  private static boolean isVisible(AnnotatedLinkWrapper linkWrapper, ZonedDateTime time) {
    return (linkWrapper.getVisibleFrom() == null || !linkWrapper.getVisibleFrom().isAfter(time)) &&
           (linkWrapper.getVisibleTo() == null || linkWrapper.getVisibleTo().isAfter(time));
  }


  private String name;
  private ContentBackedPageGridPlacement pageGridPlacement;
  private RootContext rootContext;


  @SuppressWarnings("WeakerAccess")
  public PageGridPlacementAdapter(String name, ContentBackedPageGridPlacement pageGridPlacement, RootContext rootContext) {
    this.name = name;
    this.pageGridPlacement = pageGridPlacement;
    this.rootContext = rootContext;
  }


  public String getName() {
    return name;
  }

  @SuppressWarnings("unused")
  public String getViewtype() {
    Content viewtype = pageGridPlacement.getViewtype();
    return viewtype != null ? viewtype.getString("layout") : "default";
  }

  @SuppressWarnings("unused")
  public List<ContentProxy> getItems() {
    ZonedDateTime requestDate = rootContext.getRequestContext().getRequestTime();
    List<Content> items = pageGridPlacement.getExtendedItems().stream()
            .map(AnnotatedLinkWrapper::new)
            .filter(al -> al.getTarget() != null)
            .filter(al -> al.getTarget().isInProduction())
            .filter(al -> isVisible(al, requestDate))
            .map(AnnotatedLinkWrapper::getTarget)
            .collect(Collectors.toList());
    return rootContext.getProxyFactory().makeContentProxyList(items);
  }


  private class AnnotatedLinkWrapper {

    @Nullable
    private Content target;

    @Nullable
    private final ZonedDateTime visibleFrom;

    @Nullable
    private final ZonedDateTime visibleTo;

    AnnotatedLinkWrapper(@NonNull Struct annotatedLink) {
      target = CapStructHelper.getLink(annotatedLink, PageGridContentKeywords.ANNOTATED_LINK_LIST_TARGET_PROPERTY_NAME);
      visibleFrom = toZonedDateTime(CapStructHelper.getDate(annotatedLink, PageGridContentKeywords.ANNOTATED_LINK_LIST_VISIBLE_FROM_PROPERTY_NAME));
      visibleTo = toZonedDateTime(CapStructHelper.getDate(annotatedLink, PageGridContentKeywords.ANNOTATED_LINK_LIST_VISIBLE_TO_PROPERTY_NAME));
    }

    @Nullable
    Content getTarget() {
      return target;
    }

    @Nullable
    ZonedDateTime getVisibleFrom() {
      return visibleFrom;
    }

    @Nullable
    ZonedDateTime getVisibleTo() {
      return visibleTo;
    }
  }
}
