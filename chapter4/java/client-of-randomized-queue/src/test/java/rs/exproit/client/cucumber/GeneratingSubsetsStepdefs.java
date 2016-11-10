package rs.exproit.client.cucumber;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import rs.exproit.client.ClientApplication;
import rs.exproit.client.PowerSet;

public final class GeneratingSubsetsStepdefs {
    private Exception lastThrownException;
    private Set<Set<String>> lastGeneratedPowerSet;
    
    @Before("@ClientApplicationTest")
    public void cleanupBeforeScenario() {
        lastThrownException = null;
        lastGeneratedPowerSet = null;
    }
    
    @Given("^the client application is running$")
    public void the_client_application_is_running() {
        // Do nothing here, as our client application is trivial.
    }

    @When("^I do not provide input$")
    public void i_do_not_provide_input() {
        try {
            ClientApplication.main(new String[0]);
        } catch (IllegalArgumentException e) {
            lastThrownException = e;
        }
    }

    @Then("^the application should show an error$")
    public void the_application_should_show_an_error() {
        assertNotNull("application should generate an exception", lastThrownException);
        assertTrue("the exception should be for illegal arguments", 
                lastThrownException instanceof IllegalArgumentException);
    }    

    private static Collection<Set<String>> lastGeneratedRandomOutput;
    
    public static final class TestOutputBuilder implements ClientApplication.OutputBuilder<String> {
        @Override
        public void startOutput() {
            lastGeneratedRandomOutput = new HashSet<>();
        }

        @Override
        public void addContent(Set<String> subset) {
            lastGeneratedRandomOutput.add(subset);
        }

        @Override
        public String finalizeOutput() {
            return lastGeneratedRandomOutput.toString();
        }
    }
    
    @When("^I read an input: (.+)$")
    public void i_read_an_input(String inputSeq) {
        String[] args = inputSeq.split("\\s+");
        lastGeneratedPowerSet = new PowerSet(new HashSet<>(Arrays.asList(args))).subsets();
        ClientApplication.setOutputBuilder(TestOutputBuilder.class);
        ClientApplication.main(args);
    }

    @Then("^the response should contain: (\\d+) subsets$")
    public void the_response_should_contain(int outputCardinality) {
        assertEquals(outputCardinality, lastGeneratedRandomOutput.size());
    }

    @And("^the subsets should be in random order$")
    public void the_subsets_should_be_in_random_order() {
        // For a very small power set it might be the case that the shuffled
        // set is the same as the original.
        if (lastGeneratedPowerSet.size() > 4) {
            boolean allSame = true;
            Iterator<Set<String>> powerSetIter = lastGeneratedPowerSet.iterator();
            Iterator<Set<String>> randomOutputIter = lastGeneratedRandomOutput.iterator();

            while (randomOutputIter.hasNext()) {
                allSame = randomOutputIter.next().equals(powerSetIter.next());
            }
            assertFalse(allSame);
        }
    }
    
    @And("^the subsets should be members of the power set$")
    public void the_subsets_should_be_members_of_the_power_set() {
        assertTrue(lastGeneratedPowerSet.containsAll(lastGeneratedRandomOutput));
    }
}