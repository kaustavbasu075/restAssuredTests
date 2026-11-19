package listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersFile implements ITestListener {

    private static final Logger logger = LogManager.getLogger(ListenersFile.class);

    public void onTestStart(ITestResult result) {
    logger.info("Starting test: " + result.getMethod().getMethodName());
  }

   public void onTestSuccess(ITestResult result) {
    logger.info("Test passed: " + result.getMethod().getMethodName());
  }

   public void onTestFailure(ITestResult result) {
    logger.error("Test failed: " + result.getMethod().getMethodName());
  }

   public void onTestSkipped(ITestResult result) {
    logger.warn("Test skipped: " + result.getMethod().getMethodName());
  }
    

}
