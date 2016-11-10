package rs.exproit.client.cucumber;

import org.junit.Test;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import rs.exproit.client.ClientApplication;

@RunWith(Cucumber.class)
@CucumberOptions(
    monochrome = true,
    plugin = {"pretty", "json:target/cucumber.json", "rerun:target/rerun.txt"}, 
    dryRun = false
)
public class ClientApplicationTest {
    @Test(expected = NullPointerException.class)
    public void illegalStartupWithANullInputArgument() {
        ClientApplication.main(null);
    }
}
