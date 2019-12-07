package com.wordpress.carledwinti.sistema.ponto.eletronico.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PasswordUtilsTest {

    private static final String SENHA = "Senha@123";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void senhaNullTest(){
        Assert.assertNull(PasswordUtils.getNewBCrypt(null));
    }

    @Test
    public void generateHashSenha(){
        String hash = PasswordUtils.getNewBCrypt(SENHA);
        Assert.assertTrue(bCryptPasswordEncoder.matches(SENHA, hash));
    }
}
