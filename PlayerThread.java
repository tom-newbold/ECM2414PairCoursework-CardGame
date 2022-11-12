public class PlayerThread extends Thread {
    public Thread MAIN_THREAD;
    private Player player;
    private Deck drawDeck;
    private Deck discardDeck;
    public PlayerThread(Thread MAIN_THREAD, Integer pId, Card[] playerHand, Deck draw, Deck discard) {
        this.MAIN_THREAD = MAIN_THREAD;
        this.player = new Player(pId,playerHand);
        this.drawDeck = draw;
        this.discardDeck = discard;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                this.player.atomicTurn(drawDeck,discardDeck);
                if(this.player.winCondition()) {
                    // notify main thread
                    this.MAIN_THREAD.notify();
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Player getPlayer() { return player; }
}