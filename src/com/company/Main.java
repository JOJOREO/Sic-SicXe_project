package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file1= new File("E://intellj_projects//systems_programming _project_1//src//com//company//sourcecode.txt");
        Scanner reader = new Scanner(file1);
        //reader.nextLine();
        int pc=0;//recieved value from function in main function
        int PcRec=0;//updated
        int PcRec2=0;//old
        String output="";
        String output2="";
        String hexAddress="";

        while (reader.hasNext())//read from src file
        {
            String currentLine= reader.nextLine();
            String[]words=currentLine.split("\\s+");//find space as separating point in code to move between each instruction
            if(words.length<3)//for sending values of instruction into calculate pc function for the resw & resb
            {

                String value="";
                 PcRec = calculateP1(words[1], value);

            }
            else if(words.length==3)
            {
                String value=words[2];
                 PcRec2=PcRec;
                 PcRec = calculateP1(words[1], value);

            }
            //pc+=PcRec;
            pc+=PcRec2;


           for(int i=0;i<words.length;i+=3)//text results and reading array of words==================================printing
            {
               //System.out.println(" current line : "+Integer.toHexString(pc)+"\t"+currentLine+"\t" );
                 hexAddress = String.format("%1$04X",pc);
                System.out.println( hexAddress+"\t"+currentLine+"\t" );
            }
           //write into output file
            String text=hexAddress+"\t"+currentLine+"\t";
            output+=text+"\n";
            PrintWriter writer= new PrintWriter("output.txt");
            writer.write(output);
            writer.close();
            //symboltable********************************
            if(words.length==2)
            {

                String text2=""; //with pc
                output2+=text2+"\n";
                PrintWriter writer2= new PrintWriter("symbolTable.txt");
                writer2.write(output2);
                writer2.close();

            }
            else if(words.length==3)
            {
                String text2=hexAddress+"\t"+words[0]; //with pc
                if(words[0]=="")
                {
                    text2="";
                    output2+=text2;
                }
                else if(words[0]!="")
                {
                    output2+=text2+"\n";
                }
                output2 = output2.replaceAll ("^[ |\t]*\n$", "");

                PrintWriter writer2= new PrintWriter("symbolTable.txt");
                writer2.write(output2);
                writer2.close();
            }
        }

    }



    public static int calculateP1(String instruction,String value) throws FileNotFoundException {

        int PcCounter=0;

        String[] arr1 = {"FIX", "FLOAT", "SIO", "HIO","TIO"};
        String[] arr2 = {"MULR", "SHIFTR", "SHIFTL", "SVC","TIXR","SUBR","CLEAR","ADDR","DIVR","COMPR","RMO"};
        String[] instructionSet ={};
        File file2= new File("E://intellj_projects//systems_programming _project_1//src//com//company//instruction set.txt");
        Scanner reader2 = new Scanner(file2);
        while (reader2.hasNext())//read from src file
        {
            String currentLine2= reader2.nextLine();
            String[]words2=currentLine2.split("\\s+");//find space as separating point in code to move between each instruction
            instructionSet= new String[words2.length/2];
            for(int i=0;i<words2.length/2;i+=2)
            {
                instructionSet[i]=words2[i];
            }
            for(int i=0;i<instructionSet.length;i++)
            {
                //System.out.println(instructionSet[i]);
            }

        }



        int j=0;
        String[] splitted = instruction.split("(?<=.)");// divide word into characters for format 5, 6
        String[] splitted2 = value.split("(?<=.)");//divide value of byets and words , resw, etc into pieces to get value
        if(splitted[0].equals("+"))//format 4
        {
            PcCounter+=4;
            //System.out.print("\t"+"format 4 ");
            return(PcCounter);

        }
        else if(splitted[0].equals("&"))//format 5
        {
            PcCounter+=3;
            //System.out.print("\t"+"format 5 ");
            return(PcCounter);
        }
        else if(splitted[0].equals("$"))//format 6
            {
            PcCounter+=4;
            //System.out.print("\t"+"format 6 ");
            return(PcCounter);
            }

        else if(instruction.equals("RESB"))
            {
                PcCounter+= Integer.parseInt(value);
                //System.out.print("\t"+"RESB "+"\t");
                return (PcCounter);

            }
        else if(instruction.equals("RESW"))
            {
                PcCounter+= Integer.parseInt(value)*3;
                //System.out.print("\t"+"RESW "+"\t");
                return (PcCounter);

            }
        else if(instruction.equals("RESDW"))
        {
            PcCounter+= Integer.parseInt(value)*6;
            //System.out.print("\t"+"RESDW "+"\t");
            return (PcCounter);

        }
        else if(instruction.equals("BYTE")&&splitted2[0].equals("C"))
            {


                    PcCounter+= (value.length()-3) ;
                    //System.out.print("\t"+" BYTE C "+"\t");
                    return (PcCounter);

                }

        else if(instruction.equals("BYTE")&&splitted2[0].equals("X"))
                {
                PcCounter+= (value.length()-3)/2 ;
                //System.out.print("\t"+" BYTE X "+"\t");
                return (PcCounter);
                }



        else if(instruction.equals("WORD"))
        {
            PcCounter+= 3 ;
            //System.out.print("\t"+"WORD "+"\t");
            return (PcCounter);
        }
        else
        {
            if(instruction.equals("START")||instruction.equals("BASE")||instruction.equals("EQU")||instruction.equals("LTORG"))
            {
                //System.out.print("\t"+"no pass1 "+"\t");

                return(0);
            }
                int flag=3;
                for (j = 0; j < arr2.length; j++)
                {
                    if (instruction.equals(arr2[j]))//format 2
                    {
                        flag=2;
                        break;
                    }
                }

                for (j = 0; j < arr1.length; j++)
                {
                if (instruction.equals(arr2[j]))//format 1
                {

                    flag=1;
                    break;
                }
            }
            int flagtemp=flag;
            int flagCorrect=2;
            if(flag==1||flag==2||flag==3||flag==4||flag==5||flag==6)
            {
                flagCorrect=0;
            }


            for(j=0;j< instructionSet.length;j++)
            {
                if(!instruction.equals(instructionSet[j]))
                {
                    flagCorrect=1;

                    break;

                }
            }
            if(flagCorrect!=1)
            {
                flag=0;
            }



                if(flag==1)
                {PcCounter += 1;
                   //System.out.print("\t"+"format 1"+"\t");
                    return(PcCounter);
            }
                else if (flag==2)
                {
                    PcCounter += 2;
                    //System.out.print("\t"+"format 2"+"\t");
                    return(PcCounter);
                }
                else if(flag==3)
                {
                    PcCounter += 3;
                    //System.out.print("\t"+"format 3"+"\t");
                    return(PcCounter);
                }
                else {
                    //System.out.print("\t"+"wrong instruction "+"\t");

                    return(0);
                }

        }

    }
}


