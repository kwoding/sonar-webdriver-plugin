package it.ding.sonar.config;

import static it.ding.sonar.data.CommonData.REPOSITORY_KEY;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.sonar.api.rule.RuleScope;
import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.api.utils.AnnotationUtils;

public class WebDriverRulesDefinition implements RulesDefinition {

  // don't change that because the path is hard coded in CheckVerifier
  private static final String RESOURCE_BASE_PATH = "/org/sonar/l10n/java/rules/squid";

  private static final String REPOSITORY_NAME = "Sonar WebDriver Plugin";

  private final Gson gson = new Gson();

  @Override
  public void define(Context context) {
    NewRepository repository = context
      .createRepository(REPOSITORY_KEY, "java")
      .setName(REPOSITORY_NAME);

    List<Class> checks = RulesList.getChecks();
    new RulesDefinitionAnnotationLoader().load(repository, Iterables.toArray(checks, Class.class));

    for (Class ruleClass : checks) {
      newRule(ruleClass, repository);
    }
    repository.done();
  }

  @VisibleForTesting
  protected void newRule(Class<?> ruleClass, NewRepository repository) {

    org.sonar.check.Rule ruleAnnotation = AnnotationUtils.getAnnotation(ruleClass, org.sonar.check.Rule.class);
    if (ruleAnnotation == null) {
      throw new IllegalArgumentException("No Rule annotation was found on " + ruleClass);
    }
    String ruleKey = ruleAnnotation.key();
    if (StringUtils.isEmpty(ruleKey)) {
      throw new IllegalArgumentException("No key is defined in Rule annotation of " + ruleClass);
    }
    NewRule rule = repository.rule(ruleKey);
    if (rule == null) {
      throw new IllegalStateException("No rule was created for " + ruleClass + " in " + repository.key());
    }
    ruleMetadata(ruleClass, rule);
  }

  private String ruleMetadata(Class<?> ruleClass, NewRule rule) {
    String metadataKey = rule.key();
    org.sonar.java.RspecKey rspecKeyAnnotation = AnnotationUtils.getAnnotation(ruleClass, org.sonar.java.RspecKey.class);
    if (rspecKeyAnnotation != null) {
      metadataKey = rspecKeyAnnotation.value();
      rule.setInternalKey(metadataKey);
    }
    addHtmlDescription(rule, metadataKey);
    addMetadata(rule, metadataKey);
    return metadataKey;
  }

  private void addMetadata(NewRule rule, String metadataKey) {
    URL resource = WebDriverRulesDefinition.class.getResource(RESOURCE_BASE_PATH + "/" + metadataKey + "_java.json");
    if (resource != null) {
      RuleMetadata metadata = gson.fromJson(readResource(resource), RuleMetadata.class);
      rule.setSeverity(metadata.defaultSeverity.toUpperCase(Locale.US));
      rule.setName(metadata.title);
      rule.addTags(metadata.tags);
      rule.setType(RuleType.valueOf(metadata.type));
      rule.setStatus(RuleStatus.valueOf(metadata.status.toUpperCase(Locale.US)));
      rule.setScope(RuleScope.valueOf(metadata.scope.toUpperCase(Locale.US)));
      if (metadata.remediation != null) {
        rule.setDebtRemediationFunction(metadata.remediation.remediationFunction(rule.debtRemediationFunctions()));
        rule.setGapDescription(metadata.remediation.linearDesc);
      }
    }
  }

  private static void addHtmlDescription(NewRule rule, String metadataKey) {
    URL resource = WebDriverRulesDefinition.class.getResource(RESOURCE_BASE_PATH + "/" + metadataKey + "_java.html");
    if (resource != null) {
      rule.setHtmlDescription(readResource(resource));
    }
  }

  private static String readResource(URL resource) {
    try {
      return Resources.toString(resource, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read: " + resource, e);
    }
  }

  private static class RuleMetadata {
    String title;
    String status;
    @Nullable
    Remediation remediation;

    String type;
    String[] tags;
    String defaultSeverity;
    String scope;
  }

  private static class Remediation {
    String func;
    String constantCost;
    String linearDesc;
    String linearOffset;
    String linearFactor;

    public DebtRemediationFunction remediationFunction(DebtRemediationFunctions drf) {
      if (func.startsWith("Constant")) {
        return drf.constantPerIssue(constantCost.replace("mn", "min"));
      }
      if ("Linear".equals(func)) {
        return drf.linear(linearFactor.replace("mn", "min"));
      }
      return drf.linearWithOffset(linearFactor.replace("mn", "min"), linearOffset.replace("mn", "min"));
    }
  }

}
