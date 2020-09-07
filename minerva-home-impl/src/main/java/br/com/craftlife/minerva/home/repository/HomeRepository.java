package br.com.craftlife.minerva.home.repository;

import br.com.craftlife.eureka.database.EurekaBD;
import br.com.craftlife.minerva.home.model.Home;
import br.com.craftlife.minerva.home.model.HomeId;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HomeRepository extends EurekaBD<Home, HomeId> {

    public List<Home> listarPorDono(String player) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Home> criteria = getCriteria();
        Root<Home> root = criteria.from(Home.class);
        criteria
                .select(root)
                .where(builder.equal(root.get("id").get("owner"), player.toLowerCase()));
        return findAll(criteria);
    }

}
