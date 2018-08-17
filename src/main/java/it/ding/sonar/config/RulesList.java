package it.ding.sonar.config;

import com.google.common.collect.ImmutableList;
import it.ding.sonar.check.WebDriverCommandInTestCheck;
import it.ding.sonar.check.locator.LocatorCssValueCheck;
import it.ding.sonar.check.locator.LocatorStrategyByLinkTextAndTagNameCheck;
import it.ding.sonar.check.locator.LocatorStrategyByXpathCheck;
import it.ding.sonar.check.locator.LocatorXpathValueCheck;
import it.ding.sonar.check.wait.ExplicitWaitInTestCheck;
import it.ding.sonar.check.wait.HardCodedSleepCheck;
import it.ding.sonar.check.wait.ImplicitWaitCheck;
import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;

public final class RulesList {

  private RulesList() {
  }

  public static List<Class> getChecks() {
    return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
  }

  public static List<Class<? extends JavaCheck>> getJavaChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .add(LocatorCssValueCheck.class)
      .add(LocatorStrategyByLinkTextAndTagNameCheck.class)
      .add(LocatorStrategyByXpathCheck.class)
      .add(LocatorXpathValueCheck.class)
      .add(ExplicitWaitInTestCheck.class)
      .add(HardCodedSleepCheck.class)
      .add(ImplicitWaitCheck.class)
      .add(WebDriverCommandInTestCheck.class)
      .build();
  }

  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .build();
  }
}
