package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

@Route("shop")
public class ShopGui extends VerticalLayout {
    private AppUserRepo appUserRepo;

    @Autowired
    public ShopGui(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = appUserRepo.findByUsername(currentPrincipalName);

        Label water = new Label("Water (5 g)");
        Label fertilize = new Label("Fertilizer (10 g)");
        NumberField waterNumber = new NumberField();
        waterNumber.setValue(1d);
        waterNumber.setMin(0);
        waterNumber.setMax(10);
        waterNumber.setHasControls(true);
        NumberField fertilizeNumber = new NumberField();
        fertilizeNumber.setValue(1d);
        fertilizeNumber.setMin(0);
        fertilizeNumber.setMax(10);
        fertilizeNumber.setHasControls(true);
        Label accept = new Label();

        Button waterButton = new Button("Buy");
        waterButton.addClickListener(ClickEvent -> {
            accept.removeAll();
            long numberofWater = waterNumber.getValue().intValue();
            if (user.getTamagotchi().getGold() < 5 * numberofWater) {
                accept.add("Sorry, not enough gold!");
            } else {
                user.getTamagotchi().setWater(user.getTamagotchi().getWater() + numberofWater);
                user.getTamagotchi().setGold(user.getTamagotchi().getGold() - 5 * numberofWater);
                appUserRepo.save(user);
                accept.add("You've bought " + numberofWater + " watercan(s).");
            }
        });

        Button fertilizeButton = new Button("Buy");
        fertilizeButton.addClickListener(ClickEvent -> {
            accept.removeAll();
            long numberofFertilize = fertilizeNumber.getValue().intValue();
            if (user.getTamagotchi().getGold() < 10 * numberofFertilize) {
                accept.add("Sorry, not enough gold!");
            } else {
                user.getTamagotchi().setFertilize(user.getTamagotchi().getFertilize() + numberofFertilize);
                user.getTamagotchi().setGold(user.getTamagotchi().getGold() - 10 * numberofFertilize);
                appUserRepo.save(user);
                accept.add("You've bought " + numberofFertilize + " portion(s) of fertilizer.");
            }
        });
        Button tamaButton = new Button("BACK", new Icon(VaadinIcon.BACKSPACE));
        tamaButton.addClickListener(clickEvent -> getUI().get().navigate("tamagotchi"));
        HorizontalLayout waterLayout = new HorizontalLayout();
        HorizontalLayout fertilizeLayout = new HorizontalLayout();
        HorizontalLayout bot = new HorizontalLayout();
        waterLayout.add(water, waterNumber, waterButton);
        fertilizeLayout.add(fertilize, fertilizeNumber, fertilizeButton);
        bot.add(tamaButton);
        waterLayout.setVerticalComponentAlignment(Alignment.CENTER, waterLayout);
        fertilizeLayout.setVerticalComponentAlignment(Alignment.CENTER, fertilizeLayout, accept);
        bot.setVerticalComponentAlignment(Alignment.CENTER,bot);
        add(waterLayout, fertilizeLayout, accept,bot);

    }

}
