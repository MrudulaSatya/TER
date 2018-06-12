/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ter;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.sql.ResultSet;

/**
 *
 * @author Dopt
 */
public class TER {

    /**
     * @param args the command line arguments
     */
    // number operations to convert str1 to str2

   static int min(int x,int y,int z)
    {
        if (x <= y && x <= z) return x;
        if (y <= x && y <= z) return y;
        else return z;
    }
 
    static int editDistDP(String[] str1, String[] str2, int m, int n)
    {
        // Create a table to store results of subproblems
        int dp[][] = new int[m+1][n+1];
      
        // Fill d[][] in bottom up manner
        for (int i=0; i<=m; i++)
        {
            for (int j=0; j<=n; j++)
            {
                // If first string is empty, only option is to
                // isnert all characters of second string
                if (i==0)
                    dp[i][j] = j;  // Min. operations = j
      
                // If second string is empty, only option is to
                // remove all characters of second string
                else if (j==0)
                    dp[i][j] = i; // Min. operations = i
      
                // If last characters are same, ignore last char
                // and recur for remaining string
                else if (str1[i-1].equalsIgnoreCase(str2[j-1]))
                    dp[i][j] = dp[i-1][j-1];
      
                // If last character are different, consider all
                // possibilities and find minimum
                else
                    dp[i][j] = 1 + min(dp[i][j-1],  // Insert
                                       dp[i-1][j],  // Remove
                                       dp[i-1][j-1]); // Replace
            }
        }
  
        return dp[m][n];
    }
 


    public static void main(String[] args) {
        
        //xml file reading for reference and candidate xml files
            readXML xml = new readXML();
            xml.readXMLfile();
            NodeList candList, refList;
            candList = xml.getCandList();
            refList = xml.getRefList();
            
            String[] splitC;
            String[] splitR1;
            
            // loop that reads the xml one by one  
	for (int tempcand = 0; tempcand < candList.getLength(); tempcand ++) { 

		Node candNode = candList.item(tempcand);
                Node refNode = refList.item(tempcand);

		if (candNode.getNodeType() == Node.ELEMENT_NODE && refNode.getNodeType() == Node.ELEMENT_NODE) {

			Element candElement = (Element) candNode;
                        Element refElement = (Element) refNode;

			String cand;
                        cand = candElement.getElementsByTagName("DATA").item(0).getTextContent();
                        
                        String R1; 
                        R1 = refElement.getElementsByTagName("Ref1").item(0).getTextContent();
                      
                         //Display values
                        System.out.println("cand: " + cand);
                        System.out.println("ref1: " + R1);
                       
                        // splitting the candidate and reference sentences into individual words
                        String temp = cand.replaceAll("[\\n]"," ");
                        splitC = temp.replaceAll("[.,!?:;'।]","").replaceAll("-"," ").split(" ");
                      
                        int lenCand = splitC.length;
                        
                        temp = R1.replaceAll("\\n]"," ");
                        splitR1 = temp.replaceAll("[.,!?:;'।]","").replaceAll("-"," ").split(" ");
                        
                        int lenRef = splitR1.length ;
                        int editRate = editDistDP( splitC , splitR1 , lenCand, lenRef); 
                        System.out.println( editRate );
                        System.out.println("TER Score = " + editRate/(float)lenRef);
                }       

        }                     
       
    }
    
}
