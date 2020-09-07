package br.com.craftlife.minerva.home.command;

import br.com.craftlife.minerva.home.model.Home;
import br.com.craftlife.minerva.home.model.HomeId;
import br.com.craftlife.minerva.home.model.HomeType;
import br.com.craftlife.minerva.home.repository.HomeRepository;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import de.themoep.minedown.MineDown;
import lombok.val;
import me.rayzr522.jsonmessage.JSONMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    @Inject
    private HomeRepository homeBD;

    @Default
    @Description("go to your home or someone else's")
    @Subcommand("ir")
    public void home(Player player, @co.aikar.commands.annotation.Optional String name) {
        val id = getIdFromInput(player, StringUtils.defaultIfBlank(name, "principal"));
        Optional.ofNullable(homeBD.buscaPorId(id))
                .filter(home -> player.getName().equalsIgnoreCase(id.getOwner()))
                .map(Home::getLocation)
                .ifPresent(player::teleport);

    }

    @Description("set your home or someone else's")
    @Subcommand("set|setar")
    public void homeSet(Player player, @co.aikar.commands.annotation.Optional String name, @co.aikar.commands.annotation.Optional String typeName) {
        val homeName = StringUtils.defaultIfBlank(name, "principal");
        val type = typeName == null ? HomeType.PRIVADA : HomeType.valueOf(typeName.toUpperCase());
        if(homeBD.listarPorDono(player.getName()).size() >= 3 && !player.hasPermission("home.bypass.limite")) {
            player.spigot().sendMessage(MineDown.parse("&cVocê atingiu o limite de 3 homes compre VIP e tenha homes ilimitadas"));
            return;
        }
        HomeId id = getIdFromInput(player, homeName);
        Home home = new Home();
        home.setId(id);
        home.setHomeType(type);
        home.setLocation(player.getPlayer().getLocation().clone());
        if(!player.getName().equalsIgnoreCase(id.getOwner()) && !player.hasPermission("home.set.others")) {
            player.spigot().sendMessage(MineDown.parse("&cVocê atingiu o limite de 3 homes compre VIP e tenha homes ilimitadas"));
            return;
        }
        homeBD.altera(home);
        player.spigot().sendMessage(MineDown.parse("&aHome definida com sucesso!"));
    }


    @Description("delete your home or someone else's")
    @Subcommand("delete")
    public void homeDelete(Player player, @co.aikar.commands.annotation.Optional String name) {
        val id = getIdFromInput(player, StringUtils.defaultIfBlank(name, "principal"));
        Optional.ofNullable(homeBD.buscaPorId(id))
                .filter(home -> player.getName().equalsIgnoreCase(id.getOwner()))
                .ifPresent(home -> deleteAndSendMessage(player, id, home));

    }

    @Description("see your homes or someone else's")
    @Subcommand("list|listar")
    @CommandCompletion("@players @nothing")
    public void homeList(Player player, @co.aikar.commands.annotation.Optional String target) {
        val playerName = StringUtils.defaultIfBlank(target, player.getName());
        val homes = homeBD.listarPorDono(playerName);
        val msg = JSONMessage.create();
        homes.forEach(backpack -> {
            msg.then(backpack.getId().getName());
            msg.runCommand("/home " + backpack.getId().getName());
            val type = backpack.getHomeType();
            msg.tooltip(
                    JSONMessage.create("Visibilidade: ")
                            .color(ChatColor.WHITE)
                            .then(type.getDescricao())
                            .color(getTypeColor(type))
            );
            msg.color(getTypeColor(type)).then(", ").color(ChatColor.GOLD);
        });
        player.spigot().sendMessage(MineDown.parse("&aLista de homes : (clique no nome para ir)"));
        msg.send(player.getPlayer());
        if (homes.isEmpty()) {
            player.spigot().sendMessage(MineDown.parse("&cnão possui nenhuma home."));
        }

    }

    private ChatColor getTypeColor(HomeType type) {
        if (HomeType.PUBLICA.equals(type)) {
            return ChatColor.GREEN;
        } else if (HomeType.TEMPORARIA.equals(type)) {
            return ChatColor.GOLD;
        } else if (HomeType.PRIVADA.equals(type)) {
            return ChatColor.RED;
        }
        return ChatColor.YELLOW;
    }


    private void deleteAndSendMessage(Player player, HomeId id, Home home) {
        homeBD.remove(home);
        player.spigot().sendMessage(MineDown.parse("&cA home foi deletada com sucesso!"));
    }

    private static final Pattern PATTTERN = Pattern.compile("[^a-zA-Z0-9_]+");

    private boolean validate(String homename) {
        if (homename.length() > 16) {
            return false;
        }
        Matcher matcher = PATTTERN.matcher(homename);
        return !matcher.find();
    }

    private HomeId getIdFromInput(Player player, String nome) {
        String dono = player.getName();

        if (nome.contains(":")) {
            String[] parts = nome.split(":");
            if (parts.length == 2) {
                dono = parts[0];
                nome = parts[1];
            }
        }
        HomeId id = new HomeId();
        id.setName(nome.toLowerCase());
        id.setOwner(dono.toLowerCase());
        return id;
    }

}
