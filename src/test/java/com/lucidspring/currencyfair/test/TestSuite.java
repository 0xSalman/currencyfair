package com.lucidspring.currencyfair.test;

import com.lucidspring.currencyfair.test.standalone.ConsumerControllerTest;
import com.lucidspring.currencyfair.test.standalone.IndexControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConsumerControllerTest.class,
		IndexControllerTest.class
})
public class TestSuite {}
