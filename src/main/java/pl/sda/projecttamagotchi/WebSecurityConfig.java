package pl.sda.projecttamagotchi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.model.Tamagotchi;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

import java.time.LocalTime;
import java.util.Date;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepo appUserRepo;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepo appUserRepo) {
        this.userDetailsService = userDetailsService;
        this.appUserRepo = appUserRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/tree").hasRole("USER")
                .and()
              //  .formLogin().permitAll()
               // .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        AppUser appUserUser = new AppUser("kappa", passwordEncoder().encode("kappa"), "ROLE_USER");
        AppUser appUserAdmin = new AppUser("admino", passwordEncoder().encode("admino"), "ROLE_ADMIN");
      LocalTime x= LocalTime.now();
        appUserUser.setTamagotchi(new Tamagotchi("ff",x,22,22,22,0,0));
appUserUser.setLastLogin(x);
       // appUserRepo.save(appUserUser);
    }
}