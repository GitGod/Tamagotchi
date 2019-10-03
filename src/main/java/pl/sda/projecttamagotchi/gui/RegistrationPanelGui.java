package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.model.Tamagotchi;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

import java.time.LocalDate;
import java.time.LocalTime;

@Route("registration-panel")
public class RegistrationPanelGui extends VerticalLayout {
    private AppUserRepo appUserRepo;

    public RegistrationPanelGui(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
        TextField username = new TextField("Username:");
        TextField password = new TextField("Password");
        Button buttonHello6 = new Button("Registration", new Icon(VaadinIcon.ACCESSIBILITY));
        Label potwierdzenie = new Label();
        buttonHello6.addClickListener(clickEvent -> {
            potwierdzenie.removeAll();
            AppUser appUserUser = new AppUser(username.getValue(), passwordEncoder().encode(password.getValue()), "ROLE_USER");
            appUserUser.setTamagotchi(new Tamagotchi("New Tree", LocalTime.now(), 100, 0, 15,1,1));
            appUserUser.setLastLogin(LocalTime.now());
            if (!username.getValue().equals("") && appUserRepo.findByUsername(username.getValue()) == null) {
                appUserRepo.save(appUserUser);
                potwierdzenie.add("You planted a tree");
                getUI().get().navigate("");
            }else{
                potwierdzenie.add("Username taken");
            }

        });


        add(username, password, buttonHello6,potwierdzenie);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
