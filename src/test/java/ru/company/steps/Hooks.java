package ru.company.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.company.framework.managers.InitManager;

public class Hooks {

    @Before
    public void beforeEach() {
        InitManager.initFramework();
    }

    @After
    public void afterEach() {
        InitManager.quitFramework();
    }
}