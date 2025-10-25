import java.util.Random;

public class REDSimulation {
    
    // RED parameters
    static final double MIN_TH = 5;       // Minimum threshold
    static final double MAX_TH = 15;      // Maximum threshold
    static final double MAX_P = 0.1;      // Maximum drop probability
    static final double W_Q = 0.002;      // Weight for avg queue length
    static final int BUFFER_SIZE = 20;    // Max queue capacity

    // Simulation parameters
    static final int NUM_PACKETS = 200;   // Total packets in simulation
    static final double ARRIVAL_RATE = 0.7; // Probability of packet arrival

    public static void main(String[] args) {
        Random rand = new Random();

        double qAvg = 0.0; // average queue size
        int queueLen = 0;  // instantaneous queue size
        int dropped = 0;
        int accepted = 0;

        System.out.printf("%-10s %-15s %-15s %-10s%n", 
            "Time", "Queue Len", "Avg Queue", "Action");

        for (int t = 1; t <= NUM_PACKETS; t++) {
            // simulate packet arrival
            boolean packetArrived = rand.nextDouble() < ARRIVAL_RATE;

            if (packetArrived) {
                qAvg = (1 - W_Q) * qAvg + W_Q * queueLen; // update average queue length
                double dropProb = calculateDropProbability(qAvg);

                if (rand.nextDouble() < dropProb || queueLen >= BUFFER_SIZE) {
                    dropped++;
                    System.out.printf("%-10d %-15d %-15.2f %-10s%n", 
                        t, queueLen, qAvg, "DROP");
                } else {
                    accepted++;
                    queueLen++;
                    System.out.printf("%-10d %-15d %-15.2f %-10s%n", 
                        t, queueLen, qAvg, "ACCEPT");
                }
            } else {
                // dequeue one packet if the queue is not empty
                if (queueLen > 0) queueLen--;
                qAvg = (1 - W_Q) * qAvg + W_Q * queueLen;
            }
        }

        System.out.println("\n--- Simulation Summary ---");
        System.out.println("Total Packets: " + NUM_PACKETS);
        System.out.println("Accepted: " + accepted);
        System.out.println("Dropped: " + dropped);
        System.out.printf("Drop Rate: %.2f%%\n", (dropped * 100.0 / NUM_PACKETS));
    }

    // Function to calculate drop probability based on RED logic
    private static double calculateDropProbability(double qAvg) {
        if (qAvg < MIN_TH)
            return 0.0;
        else if (qAvg > MAX_TH)
            return 1.0;
        else
            return MAX_P * (qAvg - MIN_TH) / (MAX_TH - MIN_TH);
    }
}
