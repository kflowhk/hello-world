import java.io.*;
import java.util.*;

public class SplitMODSXML {

   public static void main(String args[]) throws Exception
   {
     // Program for generating the MOD files

     FileInputStream fis = new FileInputStream(args[0]);
     InputStreamReader isr = new InputStreamReader(fis,"utf-8");
     BufferedReader br = new BufferedReader(isr);
     
     // This is the default XML header
     
     String modsStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<modsCollection xmlns=\"http://www.loc.gov/mods/v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd\">\n";
     
     String str = "";
     
     String allXML = "";
     
     str = br.readLine();
     
     // replace all the null tags
     
     while(str!=null)
     {
       str = str.replaceAll(">null<","><");
       allXML = allXML + str + "\n";
       str = br.readLine();
     }
     
     String XMLfiles [] = allXML.split("/mods><mods");
     
     int count = 1;
     
     String identifier = XMLfiles [0].substring(XMLfiles [0].indexOf("<identifier type=\"local\">")+25,XMLfiles [0].indexOf("</identifier"));
     
     String identifierCut = XMLfiles [0].substring(XMLfiles [0].indexOf("</identifier")+12);
     
     while(!identifier.matches("[0-9]{9}"))
     {
      identifier = identifierCut.substring(identifierCut.indexOf("<identifier type=\"local\">")+25,identifierCut.indexOf("</identifier"));
     
      identifierCut = identifierCut.substring(identifierCut.indexOf("</identifier")+12);
     }
     
     FileOutputStream fos = new FileOutputStream(identifier + "\\MODS.xml");
     OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
     BufferedWriter bw = new BufferedWriter(osw);
     
     for(int i=0;i<XMLfiles.length;i++)
     {
       if(i!=0&&i!=XMLfiles.length-1)
       {
        XMLfiles[i] = modsStr + "<mods" + XMLfiles[i] + "/mods>\n</modsCollection>";
       }
       if(i==0)
       {
        XMLfiles[i] = XMLfiles[i] + "/mods>\n</modsCollection>";
       }
       if(i==XMLfiles.length-1)
       {
        XMLfiles[i] = modsStr + "<mods" + XMLfiles[i];
       }
       bw.write(XMLfiles[i]);
       bw.flush();
       count++;
       if(count<=XMLfiles.length)
       {
       
        if(XMLfiles.length>i+1)
        {
         if(XMLfiles[i+1].indexOf("></identifier")>-1)
         {
          System.out.println("break");
          break;
         }
        }
        
        System.out.println(XMLfiles [i+1]);
        
        identifier = XMLfiles [i+1].substring(XMLfiles [i+1].indexOf("<identifier type=\"local\">")+25,XMLfiles [i+1].indexOf("</identifier"));
     
        identifierCut = XMLfiles [i+1].substring(XMLfiles [i+1].indexOf("</identifier")+12);

        
        while(!identifier.matches("[0-9]{9}"))
        {
         identifier = identifierCut.substring(identifierCut.indexOf("<identifier type=\"local\">")+25,identifierCut.indexOf("</identifier"));
     
         identifierCut = identifierCut.substring(identifierCut.indexOf("</identifier")+12);
        }
        fos = new FileOutputStream(identifier + "\\MODS.xml");
        osw = new OutputStreamWriter(fos,"utf-8");
        bw = new BufferedWriter(osw);
       }
     }
   }
}
