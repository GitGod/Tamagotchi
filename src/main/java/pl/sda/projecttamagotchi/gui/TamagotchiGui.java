package pl.sda.projecttamagotchi.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sda.projecttamagotchi.model.AppUser;
import pl.sda.projecttamagotchi.repo.AppUserRepo;

import java.time.Duration;
import java.time.LocalTime;

@Route("tamagotchi")
public class TamagotchiGui extends VerticalLayout {
    private AppUserRepo appUserRepo;

    public Image check(AppUserRepo appUserRepo){
        this.appUserRepo = appUserRepo;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = appUserRepo.findByUsername(currentPrincipalName);
        Image imageTree = new Image();
        Image imageGround = new Image("https://www.fototapety24.net/fotografia/400/54435121/pole-natura-trawa-niebo.jpg", "Welcome, that's your start ground :)");
        Image imageSeed = new Image("https://media.mnn.com/assets/images/2017/09/acorns_ground.jpg.838x0_q80.jpg", "You sowed your aok tree!");
        Image imageSeedling = new Image("https://www.wlin.pl/assets/277/D%C4%85b%20szypu%C5%82kowy-siewka_normal.png", "Congrats! That's your oak seedling!");
        Image imageYoungOak = new Image("https://s0.geograph.org.uk/geophotos/05/39/26/5392656_b070e5d9.jpg", "Your oak is growing!");
        Image imageGrownOak = new Image("https://thumbs.dreamstime.com/z/young-oak-tree-25131905.jpg", "Your oak has grown a little again!");
        Image imageAdultOak = new Image("https://media-cdn.tripadvisor.com/media/photo-s/14/c8/42/77/the-ewing-young-oak-an.jpg", "Congrats! you own a healthy oak!");
        Image imageSickOak1 = new Image("https://i.pinimg.com/originals/be/96/fa/be96fac2221da19684e53612ea8aa78c.jpg", "Your oak is week. Water it or use some fertilizer");
        Image imageSickOak2 = new Image("https://ak1.picdn.net/shutterstock/videos/1090321/thumb/1.jpg", "Your oak is even weaker. Water it or use some fertilizer.");
        Image imageDyingOak = new Image("https://www.warrenphotographic.co.uk/photography/bigs/01131-Ockley-Oak-Autumn.jpg", "Your oak is dying. Water it or use some fertilizer.");
        Image imageDeadOak = new Image("https://www.warrenphotographic.co.uk/photography/bigs/12013-Ockley-oak-Autumn.jpg", "Your oak is dead.");

        int treeImage = (int) Duration.between(user.getTamagotchi().getAge(), LocalTime.now()).toMinutes();
        if (treeImage == 0) {
            imageTree = imageGround;
        } else {
            if (treeImage == 1) {
                imageTree = imageSeed;
            } else {
                if (treeImage == 2) {
                    imageTree = imageSeedling;
                } else {
                    if (treeImage > 2 && treeImage < 5) {
                        imageTree = imageYoungOak;
                    } else {
                        if (treeImage >= 5 && treeImage < 10) {
                            imageTree = imageGrownOak;
                        } else {
                            if (treeImage >= 10) {
                                imageTree = imageAdultOak;
                                int howHealthy = user.getTamagotchi().getHealth();
                                if (howHealthy > 50) {
                                    imageTree = imageAdultOak;
                                } else {
                                    if (howHealthy <= 50 && howHealthy > 30) {
                                        imageTree = imageSickOak1;
                                    } else {
                                        if (howHealthy <= 30 && howHealthy > 15) {
                                            imageTree = imageSickOak2;
                                        } else {
                                            if (howHealthy <= 15 && howHealthy > 0) {
                                                imageTree = imageDyingOak;
                                            } else {
                                                if (howHealthy == 0) {
                                                    imageTree = imageDeadOak;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        imageTree.setHeight("300px");
        imageTree.setWidth("300px");
        return imageTree;
    }


    @Autowired
    public TamagotchiGui(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = appUserRepo.findByUsername(currentPrincipalName);
        if (user.getTamagotchi().getHealth() < 0) {
            user.getTamagotchi().setHealth(0);
            appUserRepo.save(user);
        }
        Label nameTree = new Label("Name: " + user.getTamagotchi().getName());
        VerticalLayout leftPanel = new VerticalLayout();
        Label healthLabel = new Label("Health: " + String.valueOf(user.getTamagotchi().getHealth()) + " %");
        Label ageLabel = new Label("Age: " + String.valueOf(Duration.between(user.getTamagotchi().getAge(), LocalTime.now()).toMinutes() + " days"));
        Label leavesLabel = new Label("Leaves: " + String.valueOf(user.getTamagotchi().getLeaves()));
        Label goldLabel = new Label("Your gold: " + String.valueOf(user.getTamagotchi().getGold()));
        leftPanel.add(nameTree,healthLabel, ageLabel, leavesLabel, goldLabel);


        Image imageTree = new Image();
        Image imageGround = new Image("https://www.fototapety24.net/fotografia/400/54435121/pole-natura-trawa-niebo.jpg", "Welcome, that's your start ground :)");
        Image imageSeed = new Image("https://media.mnn.com/assets/images/2017/09/acorns_ground.jpg.838x0_q80.jpg", "You sowed your aok tree!");
        Image imageSeedling = new Image("https://www.wlin.pl/assets/277/D%C4%85b%20szypu%C5%82kowy-siewka_normal.png", "Congrats! That's your oak seedling!");
        Image imageYoungOak = new Image("https://s0.geograph.org.uk/geophotos/05/39/26/5392656_b070e5d9.jpg", "Your oak is growing!");
        Image imageGrownOak = new Image("https://thumbs.dreamstime.com/z/young-oak-tree-25131905.jpg", "Your oak has grown a little again!");
        Image imageAdultOak = new Image("https://media-cdn.tripadvisor.com/media/photo-s/14/c8/42/77/the-ewing-young-oak-an.jpg", "Congrats! you own a healthy oak!");
        Image imageSickOak1 = new Image("https://i.pinimg.com/originals/be/96/fa/be96fac2221da19684e53612ea8aa78c.jpg", "Your oak is week. Water it or use some fertilizer");
        Image imageSickOak2 = new Image("https://ak1.picdn.net/shutterstock/videos/1090321/thumb/1.jpg", "Your oak is even weaker. Water it or use some fertilizer.");
        Image imageDyingOak = new Image("https://www.warrenphotographic.co.uk/photography/bigs/01131-Ockley-Oak-Autumn.jpg", "Your oak is dying. Water it or use some fertilizer.");
        Image imageDeadOak = new Image("https://www.warrenphotographic.co.uk/photography/bigs/12013-Ockley-oak-Autumn.jpg", "Your oak is dead.");

        int treeImage = (int) Duration.between(user.getTamagotchi().getAge(), LocalTime.now()).toMinutes();
        if (treeImage == 0) {
            imageTree = imageGround;
        } else {
            if (treeImage == 1) {
                imageTree = imageSeed;
            } else {
                if (treeImage == 2) {
                    imageTree = imageSeedling;
                } else {
                    if (treeImage > 2 && treeImage < 5) {
                        imageTree = imageYoungOak;
                    } else {
                        if (treeImage >= 5 && treeImage < 10) {
                            imageTree = imageGrownOak;
                        } else {
                            if (treeImage >= 10) {
                                imageTree = imageAdultOak;
                                int howHealthy = user.getTamagotchi().getHealth();
                                if (howHealthy > 50) {
                                    imageTree = imageAdultOak;
                                } else {
                                    if (howHealthy <= 50 && howHealthy > 30) {
                                        imageTree = imageSickOak1;
                                    } else {
                                        if (howHealthy <= 30 && howHealthy > 15) {
                                            imageTree = imageSickOak2;
                                        } else {
                                            if (howHealthy <= 15 && howHealthy > 0) {
                                                imageTree = imageDyingOak;
                                            } else {
                                                if (howHealthy == 0) {
                                                    imageTree = imageDeadOak;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        imageTree.setHeight("300px");
        imageTree.setWidth("300px");
        HorizontalLayout centerPanel = new HorizontalLayout();
        VerticalLayout middlePanel = new VerticalLayout();
        middlePanel.add(imageTree);
        VerticalLayout rightPanel = new VerticalLayout();
        Button rankingButton = new Button("Ranking", new Icon(VaadinIcon.TROPHY));
        rankingButton.addClickListener(clickEvent -> getUI().get().navigate("ranking"));
        Button gameButton = new Button("Game", new Icon(VaadinIcon.GAMEPAD));
        gameButton.addClickListener(clickEvent -> getUI().get().navigate("game"));
        Button shopButton = new Button("Shop", new Icon(VaadinIcon.SHOP));
        shopButton.addClickListener(clickEvent -> getUI().get().navigate("shop"));
        Button changeButton = new Button("Change name", new Icon(VaadinIcon.EXCHANGE));
        changeButton.addClickListener(clickEvent -> getUI().get().navigate("changename"));
        Button logoutButton = new Button("Logout", new Icon(VaadinIcon.BED));
        logoutButton.addClickListener(clickEvent -> getUI().get().navigate(""));
        rightPanel.add(rankingButton, gameButton, shopButton, changeButton, logoutButton);


        HorizontalLayout bottomPanel = new HorizontalLayout();
        Button waterButton = new Button("Water (" + user.getTamagotchi().getWater() + ")");
        if (user.getTamagotchi().getWater() == 0) {
            waterButton.setEnabled(false);
        }
        waterButton.addClickListener(ClickEvent -> {
            if (user.getTamagotchi().getHealth() < 96) {
                user.getTamagotchi().setHealth(user.getTamagotchi().getHealth() + 5);
                user.getTamagotchi().setWater(user.getTamagotchi().getWater() - 1);
                user.getTamagotchi().setLeaves(user.getTamagotchi().getLeaves() + 1);
                appUserRepo.save(user);
                waterButton.setText("Water " + user.getTamagotchi().getWater());
                leavesLabel.setText("Leaves: " + String.valueOf(user.getTamagotchi().getLeaves()));
                healthLabel.setText("Health: " + String.valueOf(user.getTamagotchi().getHealth()) + " %");
                middlePanel.removeAll();
                middlePanel.add(check(appUserRepo));
               // centerPanel.add(leftPanel, middlePanel, rightPanel);
              //  add(centerPanel, bottomPanel);
                if (user.getTamagotchi().getWater() == 0) {
                    waterButton.setEnabled(false);
                }
            } else {
                user.getTamagotchi().setHealth(100);
                user.getTamagotchi().setLeaves(user.getTamagotchi().getLeaves() + 1);
                user.getTamagotchi().setWater(user.getTamagotchi().getWater() - 1);
                appUserRepo.save(user);
                leavesLabel.setText("Leaves: " + String.valueOf(user.getTamagotchi().getLeaves()));
                healthLabel.setText("Health: " + String.valueOf(user.getTamagotchi().getHealth()) + " %");
                Dialog waterDialog = new Dialog();
                waterDialog.add(new Label("Your tree is healthy, no need to water it."));

                waterDialog.setWidth("350px");
                waterDialog.setHeight("50px");

                waterButton.addClickListener(event -> waterDialog.open());
            }
        });

        Button fertilizeButton = new Button("Fertilize (" + user.getTamagotchi().getFertilize() + ")");
        if (user.getTamagotchi().getFertilize() == 0) {
            fertilizeButton.setEnabled(false);
        }
        fertilizeButton.addClickListener(ClickEvent -> {
            if (user.getTamagotchi().getHealth() < 91) {
                user.getTamagotchi().setHealth(user.getTamagotchi().getHealth() + 10);
                user.getTamagotchi().setFertilize(user.getTamagotchi().getFertilize() - 1);
                user.getTamagotchi().setLeaves(user.getTamagotchi().getLeaves() + 2);
                appUserRepo.save(user);
                fertilizeButton.setText("Fertilize " + user.getTamagotchi().getFertilize());
                leavesLabel.setText("Leaves: " + String.valueOf(user.getTamagotchi().getLeaves()));
                healthLabel.setText("Health: " + String.valueOf(user.getTamagotchi().getHealth()) + " %");
                middlePanel.removeAll();
                middlePanel.add(check(appUserRepo));
                if (user.getTamagotchi().getFertilize() == 0) {
                    fertilizeButton.setEnabled(false);
                }
            } else {
                user.getTamagotchi().setHealth(100);
                user.getTamagotchi().setLeaves(user.getTamagotchi().getLeaves() + 2);
                user.getTamagotchi().setFertilize(user.getTamagotchi().getFertilize() - 1);
                appUserRepo.save(user);
                leavesLabel.setText("Leaves: " + String.valueOf(user.getTamagotchi().getLeaves()));
                healthLabel.setText("Health: " + String.valueOf(user.getTamagotchi().getHealth()) + " %");
                Dialog fertilizeDialog = new Dialog();
                fertilizeDialog.add(new Label("Your tree is healthy, no need to fertilize it."));
                fertilizeDialog.setWidth("350px");
                fertilizeDialog.setHeight("50px");
                fertilizeButton.addClickListener(event -> fertilizeDialog.open());
            }
        });


        bottomPanel.setVerticalComponentAlignment(Alignment.CENTER, bottomPanel);
        bottomPanel.add(waterButton, fertilizeButton);
        centerPanel.setVerticalComponentAlignment(Alignment.CENTER, centerPanel);
        centerPanel.add(leftPanel, middlePanel, rightPanel);

        add(centerPanel, bottomPanel);

    }
}

