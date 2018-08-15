package it.ding.sonar.config;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

import java.util.List;

import static it.ding.sonar.config.WebDriverRulesDefinition.REPOSITORY_KEY;

@SonarLintSide
public class WebDriverFileCheckRegistrar implements CheckRegistrar {

  @Override
  public void register(RegistrarContext registrarContext) {
    // Call to registerClassesForRepository to associate the classes with the correct repository key
    registrarContext.registerClassesForRepository(REPOSITORY_KEY, checkClasses(), testCheckClasses());
  }

  private static List<Class<? extends JavaCheck>> checkClasses() {
    return RulesList.getJavaChecks();
  }

  private static List<Class<? extends JavaCheck>> testCheckClasses() {
    return RulesList.getJavaTestChecks();
  }
}
