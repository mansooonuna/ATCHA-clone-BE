package com.sparta.atchaclonecoding.domain.member.email;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ConfirmationToken  {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column
    private boolean expired;

    @Column
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    public static ConfirmationToken createConfirmationToken(String email){
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.email = email;
        confirmationToken.expired = false;
        return confirmationToken;
    }

    //토큰 사용 만료
    public void useToken(){
        expired = true;
    }
}