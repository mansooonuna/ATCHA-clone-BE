package com.sparta.atchaclonecoding.crawling;

import com.sparta.atchaclonecoding.domain.movie.entity.Movie;
import com.sparta.atchaclonecoding.domain.movie.repository.MovieRepository;
import com.sparta.atchaclonecoding.domain.person.entity.PersonMovie;
import com.sparta.atchaclonecoding.domain.person.repository.PersonMovieRepository;
import com.sparta.atchaclonecoding.util.S3Uploader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieCrawlingController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PersonMovieRepository personMovieRepository;
    @Autowired
    private S3Uploader s3Uploader;
    private WebDriver driver;

    private static final String url = "https://pedia.watcha.com/ko-KR/decks/gcdNL4a4vN";

    public void process() {
        //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Song\\Desktop\\chromedriver_win32\\chromedriver.exe");

        // 크롬 드라이버 옵션 설정
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 헤드리스 모드로 실행
//        options.addArguments("--disable-gpu"); // GPU 사용 안함
//        options.addArguments("--disable-popup-blocking");//팝업 창 무시
        options.addArguments("--remote-allow-origins=*");
        // 크롬 드라이버 셋팅 (드라이버 설치한 경로 입력) 및 옵션 적용
        driver = new ChromeDriver(options);

        try {
            getDataList();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); //인터럽트 상태 복원
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.close();    //탭 닫기
//        driver.quit();    //브라우저 닫기
    }

    /**
     * data가져오기 : title, star, genre, time, age, image, information, personName, personJob
     */
    private List<String> getDataList() throws InterruptedException, IOException {
        List<String> list = new ArrayList<>();

        driver.get(url);    //브라우저에서 url로 이동한다.
        Thread.sleep(1000); //브라우저 로딩될때까지 잠시 기다린다.

        int count = 1;
        while (count <= 5) {
//            if(count == 7){
//                count++;
//                continue;
//            }
            WebElement images = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > section > div > div > div > section.css-1tywu13 > div:nth-child(2) > div > ul > li:nth-child("+count+")"));
            images.click();
            Thread.sleep(2000);

            WebElement titleElement = driver.findElement(By.className("css-171k8ad-Title"));
            WebElement starElement = driver.findElement(By.className("css-og1gu8-ContentRatings"));
            WebElement genreElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(2)"));
            WebElement timeElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(4)"));
            WebElement ageElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(4)"));
            WebElement imageElement =driver.findElement(By.className("css-qhzw1o-StyledImg"));
            WebElement informationElement = driver.findElement(By.className("css-kywn6v-StyledText"));
            List<WebElement> personElements = driver.findElements(By.className("css-1aaqvgs-InnerPartOfListWithImage"));
//            WebElement personNameEliment = driver.findElement(By.className("css-1aaqvgs-InnerPartOfListWithImage"));
//            WebElement personJobEliment = driver.findElement(By.className("css-1evnpxk-StyledSubtitle"));
//#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(2)
//#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(4)
            String title = titleElement.getText();
            double star = Double.parseDouble(starElement.getText().substring(4,7));
            String genre = genreElement.getText();
            genre = genre.substring(genre.lastIndexOf('·')+2);
            String time = timeElement.getText();
            time = time.substring(0, time.indexOf('·')-1);
            String age = ageElement.getText();
            age = age.substring(age.indexOf('·')+2);
            String image = imageElement.getAttribute("src");
            String information = informationElement.getText();

            System.out.println(title);
            System.out.println(star);
            System.out.println(genre);
            System.out.println(time);
            System.out.println(age);
            System.out.println(image);
            System.out.println(information);

            Movie movie = Movie.builder()
                    .title(title)
                    .star(star)
                    .genre(genre)
                    .time(time)
                    .age(age)
                    .image(s3Uploader.uploadImage(image))
                    .information(information)
                    .build();
            movieRepository.save(movie);

            for (int i = 0; i < 6; i++) {
                String personName = personElements.get(i).getAttribute("title");
                personName = personName.substring(0,personName.indexOf('('));
                String personJob = personElements.get(i).getAttribute("title");
                personJob = personJob.substring(personJob.indexOf('(')+1,personJob.lastIndexOf(')'));
                System.out.println(personName);
                System.out.println(personJob);
                PersonMovie personMovie = PersonMovie.builder()
                        .name(personName)
                        .role(personJob)
                        .build();
                personMovie.addMovie(movie);
                personMovieRepository.save(personMovie);
            }

            driver.navigate().back();
            Thread.sleep(2000);
            if (count % 12 == 0) {
                WebElement nextButton = driver.findElement(By.className("css-1d4r906-StylelessButton"));
                nextButton.click();
                Thread.sleep(1000);
            }
            count++;
        }
        return list;
    }
}
