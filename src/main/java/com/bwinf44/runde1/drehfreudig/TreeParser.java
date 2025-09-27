package main.java.com.bwinf44.runde1.drehfreudig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeParser {
    public Tree parseTree(String treeAsString)
    {
        if( !validateTree(treeAsString) )
        {
            throw new IllegalArgumentException("Invalid expression!");
        }

        Node rootNode = parseNode( treeAsString );

        return new Tree(treeAsString, rootNode.siblings());
    }

    public List<Node> splitSiblings(String siblingExpression)
    {
        List<Node> siblings = new ArrayList<>();

        int stack = 0;
        int start = 0;
        for( int i=0; i < siblingExpression.length(); i++ )
        {
            char currentChar = siblingExpression.charAt(i);

            if(currentChar == '(')
            {
                stack++;
            }
            else if(currentChar == ')')
            {
                stack--;
            }


            if(stack == 0)
            {
                int end = i+1;
                String nodeExpression = siblingExpression.substring(start,end );

                Node sibling = parseNode( nodeExpression );
                siblings.add( sibling );
                start = end;
            }
        }

        return siblings;
    }

    public Node parseNode(String nodeAsString)
    {
        Node node;

        if(nodeAsString.length() > 2)
        {
            List<Node> nodeSiblings = splitSiblings( nodeAsString.substring(1, nodeAsString.length()-1 ) );

            node = new Node(nodeSiblings);
        }
        else
        {
            node = new Node(Collections.emptyList());
        }

        return node;
    }


    private boolean validateTree(String treeAsString)
    {
        int stack = 0;

        if(treeAsString.length() < 2)
        {
            return false;
        }

        for( int i=0; i < treeAsString.length(); i++ )
        {
            char currentChar = treeAsString.charAt(i);

            if(currentChar == '(')
            {
                stack++;
            }
            else if(currentChar == ')')
            {
                stack--;
            }
            else
            {
                return false;
            }

            if(stack < 0)
            {
                return false;
            }
        }

        return stack == 0;
    }
}
