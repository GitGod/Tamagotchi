package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.projecttamagotchi.model.Tamagotchi;
import pl.sda.projecttamagotchi.repo.TamagotchiRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Route("ranking")
public class RankingGui extends VerticalLayout {

    @Autowired
    public RankingGui(TamagotchiRepo tamagotchiRepo) {

        List<Tamagotchi> tamagotchiList = tamagotchiRepo.findAll();
        tamagotchiList = tamagotchiList.stream().sorted(Comparator.comparingLong(Tamagotchi::getLeaves).reversed()).collect(Collectors.toList());
        Grid<Tamagotchi> tamaGrid = new Grid<>(Tamagotchi.class);
        tamaGrid.setItems(tamagotchiList);
        tamaGrid.removeColumnByKey("age");
        tamaGrid.removeColumnByKey("health");
        tamaGrid.removeColumnByKey("id");
        tamaGrid.removeColumnByKey("gold");
        Button tamaButton = new Button("BACK", new Icon(VaadinIcon.BACKSPACE));
        tamaButton.addClickListener(clickEvent -> getUI().get().navigate("tamagotchi"));
        tamaGrid.removeColumnByKey("fertilize");
        tamaGrid.removeColumnByKey("water");
        add(tamaGrid,tamaButton);
    }
}
