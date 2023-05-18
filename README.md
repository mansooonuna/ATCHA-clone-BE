# 🍿WATCHA(왓챠) 클론 코딩 - 앗챠!  
> OTT 서비스의 선두주자 '왓챠'를 클론코딩 해보자 
>
>
> 개발 기간 : 2023년 5월 12일 ~ 2023년 5월 18일

<div align=center> 
  <img src="https://github.com/hh14-1-cloneCoding/ATCHA-clone-BE/assets/102853354/4628e055-f8ee-4bc8-8ae4-12453bdc984a.png" width="300" center />
  </div>

# 🫡 Team
> 항해99 14기 클론코딩주차 1조
> 
> BE : 김덕인 박다솜 송현진 이호정




# 💻 Tech Stack

<br>
<div align=center> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/SPRING BOOT-6DB33F?style=for-the-badge&logo=SPRING BOOT&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">
<img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/amazon aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">  
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
</div>

# 📖 S.A 문서
https://zigzag-morning-cd7.notion.site/S-A-dc57b27df46645f0a2a201a9574db3d1



# 🎞️ ERD
![image](https://github.com/hh14-1-cloneCoding/ATCHA-clone-BE/assets/102853354/4fb8def5-6995-471d-b588-d791a1545be3) 

# 🍋 주요기능
<ol>
  <li> 회원가입 </li>
  <li> 로그인 / 로그아웃 </li>
  <li> 마이페이지 프로필 사진 수정 기능 </li>
  <li> 영화/TV 프로그램 리뷰 CRUD </li>
  <li> 영화/TV 프로그램 제목으로 검색 </li>
</ol>

# 🤟🏻 프로젝트 주요 관심사
  - 데이터 크롤링
    ```
    Selenium을 사용하여 왓챠피디아의 데이터를 크롤링하여 영화/TV 프로그램의 정보와 이미지 등 데이터를 받아온다.
    ```
    
    
  - 이메일 인증 기능


    ![22](https://github.com/hh14-1-cloneCoding/ATCHA-clone-BE/assets/102853354/41105682-e21f-40a5-a4b7-59c138173d85)
    ```
    왓챠에서 제공하고 있는 것 처럼 이메일 인증을 통한 비밀번호 변경 로직을 그대로 구현했다.
    ```
    
    
  - Access Token & Refresh Token
    ```
    Access Token의 만료시간을 최소화하고 Refresh Token을 발급해줌으로써 보안을 강화했다.
    ```
    
  - Swagger UI

![0222](https://github.com/hh14-1-cloneCoding/ATCHA-clone-BE/assets/102853354/e2e85130-535f-45e8-8d60-0adc2204b2d1)

    
    로그인을 하지 않으면 모든 기능을 사용할 수 없는 왓챠의 정책을 그대로 적용하여 토큰이 없이 기능을 테스트 할 수 없다. 
    
    프론트엔드와 유연한 협업을 위해 Swagger UI에 토큰을 넣어주는 방식을 채택했다. 
    

