package it.ding.sonar.check;

import it.ding.sonar.check.locator.CssLocatorValueCheck;
import it.ding.sonar.check.locator.LocatorStrategyByLinkTextAndTagNameCheck;
import it.ding.sonar.check.locator.LocatorStrategyByXpathCheck;
import it.ding.sonar.check.locator.XpathLocatorValueCheck;
import it.ding.sonar.check.wait.ExplicitWaitInTestCheck;
import it.ding.sonar.check.wait.HardCodedSleepCheck;
import it.ding.sonar.check.wait.ImplicitWaitCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class WebDriverChecksTest {

  @Test
  public void shouldAvoidWebDriverCommandsInTest() {
    JavaCheckVerifier.verifyNoIssue("src/test/file/AvoidWebDriverCommandWithoutTestAnnotation.java", new WebDriverCommandInTestCheck());
    JavaCheckVerifier.verify("src/test/file/AvoidWebDriverCommand.java", new WebDriverCommandInTestCheck());
  }

  @Test
  public void shouldAvoidLocatorByXpath() {
    JavaCheckVerifier.verify("src/test/file/LocatorStrategyByXpath.java", new LocatorStrategyByXpathCheck());
  }

  @Test
  public void shouldAvoidXpathLocatorTiedToPageLayout() {
    JavaCheckVerifier.verify("src/test/file/LocatorXpathValue.java", new XpathLocatorValueCheck());
  }

  @Test
  public void shouldAvoidCssLocatorTiedToPageLayout() {
    JavaCheckVerifier.verify("src/test/file/LocatorCssValue.java", new CssLocatorValueCheck());
  }

  @Test
  public void shouldAvoidLocatorsByLinkTextAndTagName() {
    JavaCheckVerifier.verify("src/test/file/LocatorStrategyByLinkTextAndTagName.java", new LocatorStrategyByLinkTextAndTagNameCheck());
  }

  @Test
  public void shouldAvoidExplicitWaitsInTest() {
    JavaCheckVerifier.verify("src/test/file/ExplicitWait.java", new ExplicitWaitInTestCheck());
  }

  @Test
  public void shouldAvoidImplicitWaits() {
    JavaCheckVerifier.verify("src/test/file/ImplicitWait.java", new ImplicitWaitCheck());
  }

  @Test
  public void shouldAvoidHardCodedSleeps() {
    JavaCheckVerifier.verify("src/test/file/HardCodedSleep.java", new HardCodedSleepCheck());
  }

  @Test
  public void shouldAvoidAssertionsInNonTest() {
    JavaCheckVerifier.verifyNoIssue("src/test/file/AssertionInTest.java", new AssertionInNonTestCheck());
    JavaCheckVerifier.verify("src/test/file/AssertionInNonTest.java", new AssertionInNonTestCheck());
  }

}
