package it.ding.sonar.config;

import org.sonar.api.Plugin;

public class WebDriverPlugin implements Plugin {

  @Override
  public void define(Context context) {

    // server extensions -> objects are instantiated during server startup
    context.addExtension(WebDriverRulesDefinition.class);

    // batch extensions -> objects are instantiated during code analysis
    context.addExtension(WebDriverFileCheckRegistrar.class);

    // add WebDriver Quality Profile
    context.addExtension(WebDriverQualityProfile.class);
  }
}
