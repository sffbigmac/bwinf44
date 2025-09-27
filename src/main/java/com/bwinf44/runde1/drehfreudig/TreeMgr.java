package main.java.com.bwinf44.runde1.drehfreudig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TreeMgr {
    public static void main(String[] args) throws IOException {
        long now = System.currentTimeMillis();

        TreeParser treeParser = new TreeParser();

        for(File file : new File("samples/drehfreudig").listFiles())
        {
            String treeAsString = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).getFirst();

            Tree tree = treeParser.parseTree(treeAsString);

            List<String> leafWeights = new ArrayList<>();
            calculateLeafWeights( leafWeights, tree.siblings(), tree.siblings().size() );

            System.out.println( "Baum " + tree.source() + " aus " + file.getName() + " ist drehfreudig: " + leafWeights.equals( leafWeights.reversed() ));
        }

        System.out.println("Berechnung hat " + (System.currentTimeMillis() - now) + "ms gedauert.");
    }

    private static void calculateLeafWeights( List<String> leafWeights, List<Node> nodes, int weight )
    {
        for(Node node : nodes)
        {
            if( node.siblings().isEmpty() )
            {
                leafWeights.add( String.valueOf( weight ) );
            }
            else
            {
                calculateLeafWeights(leafWeights, node.siblings(), node.siblings().size() * weight);
            }
        }
    }
}
