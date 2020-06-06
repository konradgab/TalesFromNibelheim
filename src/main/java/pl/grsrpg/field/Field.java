package pl.grsrpg.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import pl.grsrpg.action.IAction;
import pl.grsrpg.card.ICard;
import pl.grsrpg.player.IPlayer;
import pl.grsrpg.utils.DiceRoll;

import java.util.List;

@Getter
@ToString
public class Field implements IField {
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty
    protected List<IAction> actions;
    @JsonProperty
    private ICard undefeatedCard;
    @JsonProperty
    protected int mapLevel;

    @Override
    public void execute(IPlayer player) {
        int actionNumber = DiceRoll.rollPrivate(1, actions.size());
        System.out.println();
        actions.get(actionNumber - 1).execute(player);
    }

    @Override
    public void setUndefeatedCard(ICard undefeatedCard) {
        this.undefeatedCard = undefeatedCard;
    }
}
