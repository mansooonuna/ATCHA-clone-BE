package com.sparta.atchaclonecoding.crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlingSample {
    private WebDriver driver;

    private static final String url = "https://pedia.watcha.com/ko-KR/?domain=movie";

    public void process() {
        //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)
        System.setProperty("webdriver.chrome.driver", "./chromedriver");

        // 크롬 드라이버 옵션 설정
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 헤드리스 모드로 실행
//        options.addArguments("--disable-gpu"); // GPU 사용 안함
        options.addArguments("--disable-popup-blocking");//팝업 창 무시

        // 크롬 드라이버 셋팅 (드라이버 설치한 경로 입력) 및 옵션 적용
        driver = new ChromeDriver(options);

        try {
            getDataList();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); //인터럽트 상태 복원
        }

        driver.close();    //탭 닫기
//        driver.quit();    //브라우저 닫기
    }

    /**
     * data가져오기
     */
    private List<String> getDataList() throws InterruptedException {
        List<String> list = new ArrayList<>();

        driver.get(url);    //브라우저에서 url로 이동한다.
        Thread.sleep(2000); //브라우저 로딩될때까지 잠시 기다린다.

        WebElement bye = driver.findElement(By.className("css-1doegd6")); //팝업창 닫기
        bye.click();


        int count = 1;
        while (count <= 30) {
            WebElement image = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > section > div:nth-child(1) > div.css-1qq59e8 > div > div.css-awu20a > div > div > ul > li:nth-child("+count+")"));
            image.click();
            Thread.sleep(2000);

            WebElement titleEliment = driver.findElement(By.className("css-171k8ad-Title"));
            String title = titleEliment.getText();
            System.out.println(title);

            driver.navigate().back();
            Thread.sleep(2000);
            if (count % 5 == 0 && count != 30) {
                WebElement rightbutton = driver.findElement(By.className("css-vp7uyl"));
                rightbutton.click();
                Thread.sleep(2000);
            }
            count++;
        }
        return list;
    }
}
