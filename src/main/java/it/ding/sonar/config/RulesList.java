package it.ding.sonar.config;

import com.google.common.collect.ImmutableList;
import it.ding.sonar.check.WebDriverMethodInvocationInTestCheck;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.List;

public final class RulesList {

  private RulesList() {
  }

  public static List<Class> getChecks() {
    return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
  }

  public static List<Class<? extends JavaCheck>> getJavaChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .add(WebDriverMethodInvocationInTestCheck.class)
      .build();
  }

  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .build();
  }
}
