public class CardGame {
    /**
     * Takes input, checks validity
     * Creates n Player threads
     * Distributes cards, inital win condition check, distribute decks
     */
    public static void main(String[] args) {
        // placeholder n
        int n = 4;
        Thread[] threads = new Thread[4];
        for(int i=0;i<n;i++) {
            threads[i] = new Thread(); // anon class in here?
        }
    }
}

// https://prod.liveshare.vsengsaas.visualstudio.com/join?A318F75CBFFF3B1CDF4C95AA607BF59B2705