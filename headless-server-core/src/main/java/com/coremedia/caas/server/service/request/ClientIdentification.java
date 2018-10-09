package com.coremedia.caas.server.service.request;

import com.coremedia.blueprint.base.settings.SettingsService;
import com.coremedia.caas.server.controller.base.ResponseStatusException;
import com.coremedia.caas.service.repository.RootContext;
import com.coremedia.cap.common.CapStructHelper;
import com.coremedia.cap.struct.Struct;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Objects;
import java.util.UUID;

import static com.coremedia.caas.server.service.request.ApplicationHeaders.CLIENTID;

public class ClientIdentification {

  private static final String KEY_REGISTERED_CLIENTS = "caasClients";

  private static final String UNDEFINED_CLIENTID = "00000000-0000-0000-0000-000000000000";

  private static final String OPTION_DEFINITION_NAME = "pd";
  private static final String OPTION_DESCRIPTION = "description";


  public static ClientIdentification from(RootContext rootContext, SettingsService settingsService, ServletWebRequest request) {
    String clientId = request.getHeader(CLIENTID);
    if (Strings.isNullOrEmpty(clientId)) {
      // generic client identifier
      clientId = UNDEFINED_CLIENTID;
    }
    UUID uuid = UUID.fromString(clientId);
    // fetch client settings from site indicator
    Struct clientSettings = settingsService.nestedSetting(ImmutableList.of(KEY_REGISTERED_CLIENTS, uuid.toString()), Struct.class, rootContext.getSite().getSiteIndicator());
    if (clientSettings != null && CapStructHelper.hasProperty(clientSettings, OPTION_DEFINITION_NAME)) {
      return new ClientIdentification(uuid, clientSettings);
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }


  private UUID uuid;
  private Struct clientSettings;


  private ClientIdentification(UUID uuid, Struct clientSettings) {
    this.uuid = uuid;
    this.clientSettings = clientSettings;
  }


  public UUID getId() {
    return uuid;
  }

  public String getDefinitionName() {
    return clientSettings.getString(OPTION_DEFINITION_NAME);
  }

  public String getDescription() {
    return getOption(OPTION_DESCRIPTION, String.class);
  }


  public <E> E getOption(String key, Class<E> type) {
    Object value = clientSettings.get(key);
    if (type.isInstance(value)) {
      return type.cast(value);
    }
    return null;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientIdentification that = (ClientIdentification) o;
    return Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", getId())
            .add("definitionName", getDefinitionName())
            .toString();
  }
}
