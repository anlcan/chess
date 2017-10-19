package com.baykal;

import com.baykal.model.Game;
import com.baykal.model.Player;
import com.baykal.model.Type;

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
    public static void main( String[] args )
    {
        Game g = new Game(
                new Player("white", Type.WHITE),
                new Player("black", Type.BLACK));
        g.start();

        try (FileWriter fw = new FileWriter("src/main/resources/pgn.html")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
             bufferedWriter.write(g.getBoard().toPgnString());
            bufferedWriter.close();
            // ptyhon -m SimpleHTTPServer
            Desktop.getDesktop().browse(new URI("http://localhost:8000/pgn11viewer.html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
