package  atm.machine;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class PlayMusic {
    private Player player;
    public PlayMusic() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\Atm simulator\\atm machine\\src\\icon\\Cash_Withdrawal_from_ATM_1_MP3.mp3");
            player = new Player(fileInputStream);
            System.out.println("Playing MP3...");
            player.play(); // This will play music immediately when the object is created
        } catch (Exception e) {
            System.out.println("Error playing MP3: " + e);
        }
    }

    public static void main(String[] args) {
        PlayMusic music = new PlayMusic(); // Replace with your file path
    }
}
