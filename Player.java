import java.util.LinkedList;

interface PlayerInterface {
    public void drawCard();
    public void discardCard();
    public Boolean winCondition();
}

public class Player {
    private LinkedList<Integer> cards;
    private Integer preferedDenom;
}
