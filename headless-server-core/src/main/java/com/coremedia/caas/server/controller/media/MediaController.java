package com.coremedia.caas.server.controller.media;

import com.coremedia.caas.server.controller.base.ControllerBase;
import com.coremedia.caas.server.controller.base.ResponseStatusException;
import com.coremedia.caas.server.service.media.ImageVariantsDescriptor;
import com.coremedia.caas.server.service.media.ImageVariantsResolver;
import com.coremedia.caas.server.service.media.MediaResource;
import com.coremedia.caas.server.service.media.MediaResourceModel;
import com.coremedia.caas.server.service.media.MediaResourceModelFactory;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.caas.service.security.AccessControlViolation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/caas/v1/{tenantId}/sites/{siteId}/media")
@Api(value = "/caas/v1/{tenantId}/sites/{siteId}/media", tags = "Media", description = "Operations for media objects")
public class MediaController extends ControllerBase {

  private ImageVariantsResolver imageVariantsResolver;


  public MediaController(ImageVariantsResolver imageVariantsResolver) {
    super("caas.server.media.requests");
    this.imageVariantsResolver = imageVariantsResolver;
  }


  @ResponseBody
  @RequestMapping(value = "/{mediaId}/{propertyName}", method = RequestMethod.GET)
  @ApiOperation(
          value = "Media.Blob",
          notes = "Return the binary data of a media object.\n" +
                  "Images can be cropped and scaled according to the responsive image settings of the site.",
          response = Byte.class,
          responseContainer = "Array"
  )
  @ApiResponses(value = {
          @ApiResponse(code = 400, message = "Invalid tenant or site"),
          @ApiResponse(code = 404, message = "Media object not found")
  })
  public ResponseEntity getMedia(@ApiParam(value = "The tenant's unique ID", required = true) @PathVariable String tenantId,
                                 @ApiParam(value = "The site's unique ID", required = true) @PathVariable String siteId,
                                 @ApiParam(value = "The media object's numeric ID or alias", required = true) @PathVariable String mediaId,
                                 @ApiParam(value = "The blob property name", required = true) @PathVariable String propertyName,
                                 @ApiParam(value = "The required ratio if requesting an image") @RequestParam(required = false) String ratio,
                                 @ApiParam(value = "The required minimum width if requesting an image") @RequestParam(required = false, defaultValue = "-1") int minWidth,
                                 @ApiParam(value = "The required minimum height if requesting an image") @RequestParam(required = false, defaultValue = "-1") int minHeight,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
    try {
      RootContext rootContext = resolveRootContext(tenantId, siteId, mediaId, request, response);
      // create model for media data
      MediaResourceModel resourceModel = rootContext.getModelFactory().createModel(MediaResourceModelFactory.MODEL_NAME, propertyName, rootContext.getTarget());
      if (resourceModel != null) {
        String contentType = resourceModel.getType();
        String requestedRatio = ratio != null ? ratio : "none";
        return execute(() -> {
          MediaResource resource = resourceModel.getMediaResource(ratio, minWidth, minHeight);
          if (resource != null) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS).noTransform())
                    .contentType(resource.getMediaType())
                    .eTag(resource.getETag())
                    .body(resource);
          }
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }, "tenant", tenantId, "site", siteId, "type", contentType, "ratio", requestedRatio);
      }
      return null;
    } catch (AccessControlViolation e) {
      return handleError(e, request, response);
    } catch (ResponseStatusException e) {
      return handleError(e, request, response);
    } catch (Exception e) {
      return handleError(e, request, response);
    }
  }


  @ResponseBody
  @RequestMapping(value = "/image/variants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(
          value = "Media.ImageVariants",
          notes = "Return the delivery variants of an image.\n" +
                  "The variants consist of specified aspect ratios in different resolutions.",
          response = ImageVariantsDescriptor.class
  )
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "The transformation map", response = ImageVariantsDescriptor.class),
          @ApiResponse(code = 400, message = "Invalid tenant or site")
  })
  public ResponseEntity<ImageVariantsDescriptor> getMediaVariants(@ApiParam(value = "The tenant's unique ID", required = true) @PathVariable String tenantId,
                                                                  @ApiParam(value = "The site's unique ID", required = true) @PathVariable String siteId,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) {
    try {
      RootContext rootContext = resolveRootContext(tenantId, siteId, request, response);
      return ResponseEntity.ok()
              .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS))
              .body(imageVariantsResolver.getVariantsDescriptor(rootContext.getSite()));
    } catch (AccessControlViolation e) {
      return handleError(e, request, response);
    } catch (ResponseStatusException e) {
      return handleError(e, request, response);
    } catch (Exception e) {
      return handleError(e, request, response);
    }
  }
}
