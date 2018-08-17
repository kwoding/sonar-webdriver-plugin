package it.ding.sonar.config;

import static it.ding.sonar.data.CommonData.REPOSITORY_KEY;

import java.util.List;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

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
