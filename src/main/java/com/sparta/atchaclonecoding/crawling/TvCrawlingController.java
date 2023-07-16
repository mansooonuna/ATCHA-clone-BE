//package com.sparta.atchaclonecoding.crawling;
//
//import com.sparta.atchaclonecoding.domain.casting.entity.Casting;
//import com.sparta.atchaclonecoding.domain.casting.repository.CastingRepository;
//import com.sparta.atchaclonecoding.domain.media.entity.Media;
//import com.sparta.atchaclonecoding.domain.media.entity.MediaType;
//import com.sparta.atchaclonecoding.domain.media.repository.MediaRepository;
//import com.sparta.atchaclonecoding.util.S3Uploader;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class TvCrawlingController {
//    private WebDriver driver;
//    @Autowired
//    private MediaRepository mediaRepository;
//    @Autowired
//    private CastingRepository castingRepository;
//    @Autowired
//    private S3Uploader s3Uploader;
//
//    private static final String url = "https://pedia.watcha.com/ko-KR/decks/gcd9zYxPaN";
//
//    public void process() {
//        System.setProperty("webdriver.chrome.driver", "./chromedriver");
//
//        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--headless"); // 헤드리스 모드로 실행
////        options.addArguments("--disable-gpu"); // GPU 사용 안함
////        options.addArguments("--disable-popup-blocking");//팝업 창 무시
//
////        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);
//
//        try {
//            getDataList();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Thread.currentThread().interrupt();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        driver.close();
//        driver.quit();
//    }
//
//    /**
//     * data가져오기 : title, star, genre, information, age, image, personName, personJob
//     */
//    private List<String> getDataList() throws InterruptedException, IOException {
//        List<String> list = new ArrayList<>();
//        driver.get(url);
//        Thread.sleep(1000);
//
//        int count = 1;
//        while (count <= 20) {
//            WebElement images = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > section > div > div > div > section.css-1tywu13 > div:nth-child(2) > div > ul > li:nth-child("+count+")"));
//            images.click();
//            Thread.sleep(2000);
//
//            WebElement titleElement, starElement, genreElement, ageElement, imageElement, informationElement = null;
//            List<WebElement> personElements = null;
//
//            try {
//                titleElement = driver.findElement(By.className("css-171k8ad-Title"));
//                starElement = driver.findElement(By.className("css-og1gu8-ContentRatings"));
//                genreElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(2)"));
//                ageElement = driver.findElement(By.cssSelector("#root > div > div.css-1xm32e0 > section > div > div.css-10ofaaw > div > div > div > div:nth-child(1) > div.css-uvsgck > div > div > section:nth-child(2) > div:nth-child(2) > div > article > div.css-wvh1uf-Summary.eokm2781 > span:nth-child(4)"));
//                imageElement = driver.findElement(By.className("css-qhzw1o-StyledImg"));
//                informationElement = driver.findElement(By.className("css-kywn6v-StyledText"));
//                personElements = driver.findElements(By.className("css-1aaqvgs-InnerPartOfListWithImage"));
//                Thread.sleep(1000);
//            } catch (NoSuchElementException e) {
//                Thread.sleep(2000);
//                driver.navigate().back();
//                Thread.sleep(1000);
//
//                //더보기를 눌러야 하는 count로 돌아갔을 때
//                if (count % 12 == 0) {
//                    WebElement nextButton = driver.findElement(By.className("css-1d4r906-StylelessButton"));
//                    nextButton.click();
//                    Thread.sleep(2000);
//                }
//                count++;
//                continue;
//        }
//
//            String title = titleElement.getText();
//            double star = Double.parseDouble(starElement.getText().substring(4,7));
//            String genre = genreElement.getText();
//            genre = genre.substring(genre.lastIndexOf('·')+2);
//            String age = ageElement.getText();
//            age = age.substring(age.indexOf('·')+2);
//            String image = imageElement.getAttribute("src");
//            String information = informationElement.getText();
//
//            String titleImageFileName = "image" + count + ".jpg";
//
//            URL titleImageUrl = new URL(image);
//            Path titleTargetPath = Path.of("/Users/ihojeong/Documents/crawlingImage/TV/titleImage/" + titleImageFileName);
//            Files.copy(titleImageUrl.openStream(), titleTargetPath, StandardCopyOption.REPLACE_EXISTING);
//            list.add(titleImageFileName);
//
//            Media media = Media.builder()
//                    .title(title)
//                    .star(star)
//                    .category(MediaType.TV)
//                    .genre(genre)
//                    .age(age)
//                    .image((s3Uploader.upload(titleTargetPath.toFile(), "images/" + titleImageFileName)))
//                    .information(information)
//                    .build();
//            mediaRepository.save(media);
//
//            List<WebElement> personImageElements = driver.findElements(By.cssSelector(".profilePhotoBlock [class*='ProfilePhotoImage']"));
//
//            int i = 0;
//            while (i < 6) {
//                String personName = null;
//                String personJob = null;
//                String personImage = null;
//
//                try {
//                    personName = personElements.get(i).getAttribute("title");
//                    personJob = personElements.get(i).getAttribute("title");
//                    personImage = personImageElements.get(i).getCssValue("background-image");
//                    personName = personName.substring(0,personName.indexOf('('));
//                    personJob = personJob.substring(personJob.indexOf('(')+1,personJob.lastIndexOf(')'));
//                    personImage = personImage.substring(5,personImage.indexOf(')')-1);
//
//                    String castingImageFileName = "image" + personName + ".jpg";
//
//                    URL castingImageUrl = new URL(personImage);
//                    Path castingTargetPath = Path.of("/Users/ihojeong/Documents/crawlingImage/TV/castingImage/" + castingImageFileName);
//                    Files.copy(castingImageUrl.openStream(), castingTargetPath, StandardCopyOption.REPLACE_EXISTING);
//                    list.add(castingImageFileName);
//
//                    Casting casting = Casting.builder()
//                            .name(personName)
//                            .image(s3Uploader.upload(titleTargetPath.toFile(), "images/" + castingImageFileName))
//                            .role(personJob)
//                            .build();
//                    casting.addMedia(media);
//                    castingRepository.save(casting);
//                } catch (NoSuchElementException | IndexOutOfBoundsException | StaleElementReferenceException e) {
//                    Thread.sleep(2000);
//                    i++;
//                    continue;
//                }
//                i++;
//            }
//
//            driver.navigate().back();
//            Thread.sleep(2000);
//            if (count % 12 == 0) {
//                WebElement nextButton = driver.findElement(By.className("css-1d4r906-StylelessButton"));
//                nextButton.click();
//                Thread.sleep(2000);
//            }
//            count++;
//        }
//        return list;
//    }
//}
//
