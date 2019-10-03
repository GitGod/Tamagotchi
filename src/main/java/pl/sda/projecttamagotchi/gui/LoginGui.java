package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

import java.time.Duration;
import java.time.LocalTime;


/**
 * UI content when the user is not logged in yet.
 */
@Route("")
@PageTitle("Login")

public class LoginGui extends FlexLayout {
    private AppUserRepo appUserRepo;

    @Autowired
    public LoginGui(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
        buildUI();
    }


    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Create new Tree :)"));
        Button buttonHello6 = new Button("Registration", new Icon(VaadinIcon.PANEL));
        buttonHello6.addClickListener(clickEvent -> getUI().get().navigate("registration-panel"));
        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        VerticalLayout verticalLayout = new VerticalLayout();

        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginForm);
        verticalLayout.add(centeringLayout, buttonHello6);
        verticalLayout.setAlignSelf(Alignment.END, buttonHello6);
        add(verticalLayout);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void login(LoginForm.LoginEvent event) {
        AppUser byUsername = appUserRepo.findByUsername(event.getUsername());


        //  basePass = passwordEncoder().encode(appUserRepo.findByUsername(event.getUsername()).getPassword());
        if (passwordEncoder().matches(event.getPassword(), byUsername.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(byUsername.getUsername(), null, byUsername.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            long minutes = Duration.between(byUsername.getLastLogin(), LocalTime.now()).toMinutes();
            byUsername.getTamagotchi().setHealth((int) (byUsername.getTamagotchi().getHealth()-minutes));
            byUsername.setLastLogin(LocalTime.now());
            appUserRepo.save(byUsername);
            getUI().get().navigate("tamagotchi");
        } else {
            event.getSource().setError(true);
        }
    }

}


