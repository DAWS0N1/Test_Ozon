package ru.company;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"ru.company.framework.utils.AllureListener",
                "progress",
                "summary"},
        glue = {"ru.company.steps"},
        features = {"src/test/resources/"},
        tags = "@checkBudsItems"
)
public class CucumberRunner {
}
