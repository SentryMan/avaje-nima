package io.avaje.nima.provider;

import io.avaje.inject.BeanScopeBuilder;
import io.avaje.nima.Nima;
import io.helidon.nima.webserver.WebServer;
import io.helidon.nima.webserver.WebServerConfig;
import io.helidon.nima.webserver.http.HttpRouting;

/** Provides defaults for Nima HttpRouting Builder and WebServerConfig Builder */
public class DefaultNimaProvider implements io.avaje.inject.spi.Plugin {

  @Override
  public Class<?>[] provides() {
    return new Class<?>[] {Nima.class};
  }

  @Override
  public void apply(BeanScopeBuilder builder) {
    final var nima = Nima.builder();
    builder.addPostConstruct(nima::configure);
    builder.propertyPlugin().get("server.port").map(Integer::valueOf).ifPresent(nima::port);
    builder.provideDefault(Nima.class, () -> nima);
    builder.provideDefault(WebServerConfig.Builder.class, WebServer::builder);
    builder.provideDefault(HttpRouting.Builder.class, HttpRouting::builder);
  }
}
