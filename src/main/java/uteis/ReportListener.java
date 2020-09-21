package uteis;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic; 

public class ReportListener implements IInvokedMethodListener {
	/**
	 * Classe incrementar o relatório Allure
	 */
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	static ThreadLocal<SoftAssert> soft = new ThreadLocal<SoftAssert>();
	private static ThreadLocal<String> steps = new ThreadLocal<String>();
	public static ThreadLocal<String> cenario = new ThreadLocal<String>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver driver) {
		ReportListener.driver.set(driver);
	}

	public static void setAsserts(SoftAssert s) {
		soft.set(s);
	}

	public static void setSteps(String step) {
		if (step.startsWith("CT")) {
			step = "</ol></p><p><b>" + step + "</b><ol>";

		} else if (step.contains("FAILED") || step.contains("PASSED")) {
			step = "<p>" + step + "</p>";

		} else {
			step = "<li>" + step + "</li>";
		}
		steps.set(steps.get() + step);
	}

	private void setCenario(String methodName) {
		cenario.set(methodName);
	}

	public static String getCenario() {
		return cenario.get();
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		steps.set(new String());
		System.gc();
		setCenario(method.toString());
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
		if (method.isTestMethod()) {
			Allure.addDescription(steps.get() + "</ol></p>");
			soft.get().assertAll();
		}
		if (!testResult.isSuccess() 
				&& !method.toString().contains("API") 
				&& !method.getTestMethod().getConstructorOrMethod().getDeclaringClass().getAnnotation(Epic.class).toString().contains("API")) {
			attachScreenshot();
		}

	}

	private void attachScreenshot() {
		Allure.addAttachment("ScreenShot-OnTestFail", new ByteArrayInputStream(((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES)));
	}

}