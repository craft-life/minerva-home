package br.com.craftlife.minerva.home.model;

import br.com.craftlife.eureka.database.EurekaED;
import lombok.Data;
import org.bukkit.Location;

import javax.persistence.*;

@Data
@Entity
@Table(name = "homes")
public class Home implements EurekaED<HomeId> {

    @EmbeddedId
    private HomeId id;

    @Column(name = "location", nullable = false)
    private Location location;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "home_type", nullable = false, columnDefinition = "smallint")
    private HomeType homeType = HomeType.PADRAO;

}
