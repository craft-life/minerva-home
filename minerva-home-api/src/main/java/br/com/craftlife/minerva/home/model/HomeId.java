package br.com.craftlife.minerva.home.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
public class HomeId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "name", length = 16, nullable = false, updatable = false)
    private String name;

    @Column(name = "player_owner", length = 16, nullable = false, updatable = false)
    private String owner;

    @Override
    public String toString() {
        return name + "." + owner;
    }
}
