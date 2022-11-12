public class PlayerThread extends Thread {
    private Thread MAIN_THREAD;
    private Player player;
    private Deck drawDeck;
    private Deck discardDeck;
    public Boolean winFlag = false;
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
                    this.winFlag = true;
                    // notify main thread
                    synchronized(this.MAIN_THREAD) {
                        this.MAIN_THREAD.notify();
                    }
                    //Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Player getPlayer() { return player; }
}