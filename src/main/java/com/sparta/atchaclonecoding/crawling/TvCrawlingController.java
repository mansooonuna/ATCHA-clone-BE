package com.sparta.atchaclonecoding.crawling;

import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import com.sparta.atchaclonecoding.domain.person.entity.PersonTv;
import com.sparta.atchaclonecoding.domain.person.repository.PersonTvRepository;
import com.sparta.atchaclonecoding.domain.tv.entity.Tv;
import com.sparta.atchaclonecoding.domain.tv.repository.TvRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TvCrawlingController {
    private WebDriver driver;
    @Autowired
    private TvRepository tvRepository;
    @Autowired
    private PersonTvRepository personTvRepository;

    private static final String url = "https://pedia.watcha.com/ko-KR/decks/gcdNmrQxJ9";

    public void process() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver");

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 헤드리스 모드로 실행
//        options.addArguments("--disable-gpu"); // GPU 사용 안함
//        options.addArguments("--disable-popup-blocking");//팝업 창 무시

        driver = new ChromeDriver(options);

        try {
            getDataList();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        driver.close();
        driver.quit();
    }

    /**
     * data가져오기 : title, star, genre, information, age, image, personName, personJob
     */
    private List<String> getDataList() throws InterruptedException {
        List<String> list = new ArrayList<>();

        driver.get(url);
        Thread.sleep(1000);
        WebElement nextButton = driver.findElement(By.className("css-1d4r906-StylelessButton"));
        nextButton.click();
        Thread.sleep(1000);
        int count = 23;
        while (count <= 23) {
            if(count==14){
                count++;
                continue;
            }
            WebElement images = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > section > div > div > div > section.css-1tywu13 > div:nth-child(2) > div > ul > li:nth-child("+count+")"));
            images.click();
            Thread.sleep(2000);

            WebElement titleElement = driver.findElement(By.className("css-171k8ad-Title"));
            WebElement starElement = driver.findElement(By.className("css-og1gu8-ContentRatings"));
            WebElement genreElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(2)"));
            WebElement ageElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(4)"));
            WebElement imageElement = driver.findElement(By.className("css-qhzw1o-StyledImg"));
            WebElement informationElement = driver.findElement(By.className("css-kywn6v-StyledText"));
            List<WebElement> personElements = driver.findElements(By.className("css-1aaqvgs-InnerPartOfListWithImage"));

            String title = titleElement.getText();
            double star = Double.parseDouble(starElement.getText().substring(4,7));
            String genre = genreElement.getText();
            genre = genre.substring(genre.lastIndexOf('·')+2);
            String age = ageElement.getText();
            age = age.substring(age.indexOf('·')+2); //
            String image = imageElement.getAttribute("src");
            String information = informationElement.getText();

            System.out.println(title);
            System.out.println(star);
            System.out.println(genre);
            System.out.println(age);
            System.out.println(image);
            System.out.println(information);

            Tv tv = Tv.builder()
                    .title(title)
                    .star(star)
                    .genre(genre)
                    .age(age)
                    .image(image)
                    .information(information)
                    .build();

            tvRepository.save(tv);
            for (int i = 0; i < 6; i++) {
                String personName = personElements.get(i).getAttribute("title");
                personName = personName.substring(0,personName.indexOf('('));
                String personJob = personElements.get(i).getAttribute("title");
                personJob = personJob.substring(personJob.indexOf('(')+1,personJob.lastIndexOf(')'));
                System.out.println(personName);
                System.out.println(personJob);
                PersonTv personTv = PersonTv.builder()
                        .name(personName)
                        .role(personJob)
                        .build();
                personTv.addTv(tv);
                personTvRepository.save(personTv);
            }

            driver.navigate().back();
            Thread.sleep(2000);
            if (count % 12 == 0) {
//                WebElement nextButton = driver.findElement(By.className("css-1d4r906-StylelessButton"));
//                nextButton.click();
                Thread.sleep(1000);
            }
            count++;
        }
        return list;
    }
}
