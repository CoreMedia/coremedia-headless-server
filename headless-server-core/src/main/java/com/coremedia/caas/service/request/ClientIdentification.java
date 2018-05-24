package com.coremedia.caas.service.request;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.controller.base.ResponseStatusException;
import com.coremedia.caas.services.repository.RootContext;
import com.coremedia.cap.struct.Struct;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.springframework.http.HttpStatus;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import static com.coremedia.caas.service.request.ApplicationHeaders.CLIENTID;

public class ClientIdentification {

  private static final String KEY_REGISTERED_CLIENTS = "caasClients";

  private static final String UNDEFINED_CLIENTID = "00000000-0000-0000-0000-000000000000";


  public static ClientIdentification from(RootContext rootContext, SettingsService settingsService, HttpServletRequest request) {
    String clientId = request.getHeader(CLIENTID);
    if (clientId == null) {
      // generic client identifier
      clientId = UNDEFINED_CLIENTID;
    }
    UUID uuid = UUID.fromString(clientId);
    // fetch client settings from site indicator
    Struct clientSettings = settingsService.nestedSetting(ImmutableList.of(KEY_REGISTERED_CLIENTS, uuid.toString()), Struct.class, rootContext.getSiteIndicator());
    if (clientSettings != null) {
      String definitionName = clientSettings.getString("pd");
      if (definitionName != null) {
        return new ClientIdentification(uuid, definitionName);
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }


  private UUID uuid;
  private String definitionName;


  ClientIdentification(UUID uuid, String definitionName) {
    this.uuid = uuid;
    this.definitionName = definitionName;
  }


  public UUID getId() {
    return uuid;
  }

  public String getDefinitionName() {
    return definitionName;
  }


  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", getId())
            .add("definitionName", getDefinitionName())
            .toString();
  }
}
