package io.loopcamp.runner;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.loopcamp.stepdefs")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
//@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value = "@minionNoAuthApi")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,value = "json:target/cucumber-report/cucumber.json")
public class RunCucumberTest {
}