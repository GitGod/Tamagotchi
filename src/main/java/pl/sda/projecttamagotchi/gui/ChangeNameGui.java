package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

@Route("changename")
public class ChangeNameGui extends VerticalLayout {
    private AppUserRepo appUserRepo;
    @Autowired
    public ChangeNameGui(AppUserRepo appUserRepo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = appUserRepo.findByUsername(currentPrincipalName);
        TextField name = new TextField();
        Label text = new Label();
        Button checkButton = new Button("Change");
        checkButton.addClickListener(ClickEvent -> {
                    text.removeAll();
                    if (name.getValue().equals("")) {
                        text.add("U can't be a NoName");
                    } else {
                        text.add("Name changed");
                        user.getTamagotchi().setName(name.getValue());
                        appUserRepo.save(user);
                        checkButton.setEnabled(false);
                    }
                }
        );
        HorizontalLayout up = new HorizontalLayout();
        HorizontalLayout middle = new HorizontalLayout();
        HorizontalLayout bottom = new HorizontalLayout();
        up.add(name,checkButton);
        Button tamaButton = new Button("BACK", new Icon(VaadinIcon.BACKSPACE));
        tamaButton.addClickListener(clickEvent -> getUI().get().navigate("tamagotchi"));
        middle.add(text);
        bottom.add(tamaButton);
        up.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER,up,middle,bottom);
        add(up,middle,bottom);
    }

}
