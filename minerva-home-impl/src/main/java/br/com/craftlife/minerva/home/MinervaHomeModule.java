package br.com.craftlife.minerva.home;

import br.com.craftlife.eureka.loader.types.WithParent;
import br.com.craftlife.eureka.module.EurekaModule;
import br.com.craftlife.minerva.MinervaModule;
import br.com.craftlife.minerva.home.command.HomeCommand;
import com.google.inject.Inject;

@WithParent(MinervaModule.class)
public class MinervaHomeModule extends EurekaModule {

    @Inject
    HomeCommand homeCommand;

    @Override
    public void init() {
        saveDefaultConfig();
    }

}
