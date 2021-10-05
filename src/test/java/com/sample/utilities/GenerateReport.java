package com.sample.utilities;

//Listener class used to generate Extent reports

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.sample.base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateReport extends TestListenerAdapter
{
    public ExtentSparkReporter spark;
    public ExtentReports extent;
    public ExtentTest logger;
    WebDriver driver = BaseClass.driver;



    public void onStart(ITestContext testContext)
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
        String repName="Test-Report-"+timeStamp+".html";

        //specify location of the report
        spark=new ExtentSparkReporter(System.getProperty("user.dir")+ "/test-output/"+repName);

        spark.config().setDocumentTitle("Sample Test Project"); // Tile of report
        spark.config().setReportName("Functional Test Automation Report"); // name of the report
        spark.config().setTheme(Theme.DARK);

        spark.viewConfigurer()
                .viewOrder().as(new ViewName[]{
                        ViewName.TEST,
                        ViewName.DASHBOARD}).apply();

        extent=new ExtentReports();
        extent.setSystemInfo("Host name","localhost");
        extent.setSystemInfo("Environment","QA");
        extent.setSystemInfo("Tester","Rakesh");

        extent.attachReporter(spark);
    }

    public void onTestSuccess(ITestResult tr)
    {
        // create new entry in th report
        logger=extent.createTest(tr.getName());

        // send the passed information to the report with GREEN color highlighted
        logger.log(Status.PASS,MarkupHelper.createLabel(tr.getName(),ExtentColor.GREEN));
    }

    public void onTestFailure(ITestResult tr)
    {
        // create new entry in th report
        logger=extent.createTest(tr.getName());

        // send the passed information to the report with RED color highlighted
        logger.log(Status.FAIL,MarkupHelper.createLabel(tr.getName(),ExtentColor.RED));

        String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";

        File file = new File(screenshotPath);

        if(file.exists())
        {
            logger.fail("Refer attached failed screenshot:" + logger.addScreenCaptureFromPath(screenshotPath));
        }
    }

    public void onTestSkipped(ITestResult tr)
    {
        // create new entry in th report
        logger=extent.createTest(tr.getName());
        logger.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));
    }

    public void onFinish(ITestContext testContext)
    {
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", BaseClass.getBrowserNameAndVersion());
        extent.flush();
    }
}
