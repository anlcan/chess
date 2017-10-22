package com.baykal;

import com.baykal.model.Game;
import com.baykal.model.Player;
import com.baykal.model.Type;
import com.baykal.strategy.CaptureOptimizeStrategy;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static final Player WHITE = new Player("white", Type.WHITE);
    public static final Player BLACK = new Player("black", Type.BLACK);

    public static void main(String[] args ) {

        WHITE.setStategy(new CaptureOptimizeStrategy(Type.WHITE));
        Game g = new Game(
                WHITE,
                BLACK);
        g.start();

        try (FileWriter fw = new FileWriter("src/main/resources/pgn.html")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
             bufferedWriter.write(g.getBoard().toPgnString());
            bufferedWriter.close();
            // ptyhon -m SimpleHTTPServer
            Desktop.getDesktop().browse(new URI("http://localhost:8000/pgnviewer.html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
