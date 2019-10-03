package pl.sda.projecttamagotchi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.projecttamagotchi.model.Tamagotchi;


@Repository
public interface TamagotchiRepo extends JpaRepository<Tamagotchi, Long> {

    Tamagotchi findByName(String name);

}