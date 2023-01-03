package com.laBoutique.appuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository underappUserRepository;

    @Test
    void itshouldcheckIfEmailExist(){
        //given
        String email="ali@gmail.com";
        AppUser appUser = new AppUser();
        appUser.setEmail(email);
        appUser.setFirstName("ali");
        appUser.setLastName("back");

        underappUserRepository.save(appUser);

        //when
        Optional<AppUser> exists = underappUserRepository.findByEmail(email);

        //then
        assertThat(exists).isNotEmpty();
        assertThat(exists.get().getEmail()).isEqualTo(email);


    }


}