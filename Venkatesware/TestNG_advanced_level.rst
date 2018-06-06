**Parameterization using testng.xml**

For Parameterization using testng.xml http://automationtesting.in/parameterization-using-testng-xml-file

**TestNG Time-Out Test**

1.At Suite Level: This will be applicable for all the tests in the TestNG test suite

<suite name="Suite" time-out="3000">

2.At Method Level: This will be applicable for the said test method.

@Test(timeOut = 3000)

**Enabling and Disabling TestNG Test**

1.Using Test annotation enabled property/attribute:
   @Test(enabled=true) - This test will be executed as the enabled property is true.
   @Test(enabled=false) - This test will NOT be executed as the enabled property is false.
   
2.Using Include and Exclude tags in the testng.xml file:

<suite name="Suite">
  <test name="Test">
    <classes>
        <class name="DisableAndEnableTests">
              <methods>
                <include name="firstTest"/>
                
                <exclude name="secondTest"/> 
                
                <exclude name="thirdTest"/> 
            </methods>
        </class>
    </classes>
  </test>
</suite>

But after execution we are seeing only one test is getting executed(i.e. firstTest) and remaining tests (i.e. secondTest and thirdTest) are skipped from the execution.

**expectedExceptions in TestNG**


@Test(expectedExceptions={ArithmeticException.class})
   
    public void exceptionTesting()
    {
        int i = 1/0;
      System.out.println("Value of i :" + i);
    }

