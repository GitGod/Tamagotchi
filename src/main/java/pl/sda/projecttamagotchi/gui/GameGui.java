package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

@Route("game")
public class GameGui extends VerticalLayout {
    private AppUserRepo appUserRepo;

    @Autowired
    public GameGui(AppUserRepo appUserRepo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = appUserRepo.findByUsername(currentPrincipalName);
        double randNumber = Math.random();
        double d = randNumber * 100;
        int randomInt = (int) d + 1;
        int tries = 7;
        TextField number = new TextField();
        Label text = new Label();


        Button checkButton = new Button("Check");
        checkButton.addClickListener(ClickEvent -> {
                    text.removeAll();
                    if (Integer.parseInt(number.getValue()) < randomInt) {
                        text.add("More");
                    } else if (Integer.parseInt(number.getValue()) > randomInt) {
                        text.add("Less");
                    } else {
                        text.add("Good Job");
                        user.getTamagotchi().setGold(user.getTamagotchi().getGold() + 3);
                        appUserRepo.save(user);
                        checkButton.setEnabled(false);
                    }
                }
        );
        Button newGameButton = new Button("New Game");
        newGameButton.addClickListener(ClickEvent -> {
                    text.removeAll();
                    UI.getCurrent().getPage().reload();
                }
        );
        Button tamaButton = new Button("BACK", new Icon(VaadinIcon.BACKSPACE));
        tamaButton.addClickListener(clickEvent -> getUI().get().navigate("tamagotchi"));
        HorizontalLayout up = new HorizontalLayout();
        HorizontalLayout middle = new HorizontalLayout();
        HorizontalLayout bottom = new HorizontalLayout();
        up.add(number, checkButton);
        middle.add(text);
        bottom.add(newGameButton, tamaButton);
        up.setVerticalComponentAlignment(Alignment.CENTER,up,middle,bottom);

        add(up,middle,bottom);

    }


}
