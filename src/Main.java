import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    CS 230 Project 1 - Compiler + Assembler
    Team Ran Liu
    Members: Qingyang Ran, Yixiao Liu
*/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        //First input
        command = scanner.nextLine();
        String regex_output = "println\\(\"(.*)\"\\);";
        Pattern pattern_output = Pattern.compile(regex_output);
        Matcher matcher_output = pattern_output.matcher(command);
        if (matcher_output.find()) {

            String content_output = matcher_output.group(1);
            //System.out.println("[Debug] content_output is: " + content_output);

            //Assembly code
            System.out.println("[Assembly code]");
            StringBuilder assemblyCode_output = new StringBuilder();
            for (char c : content_output.toCharArray()) {
                assemblyCode_output.append("LDBA ");
                assemblyCode_output.append(String.format("%#06X", (int) c));
                assemblyCode_output.append(", i\n" +
                        "STBA 0xFC16, d\n");
            }
            assemblyCode_output.append("STOP\n" +
                    ".END\n");
            System.out.print(assemblyCode_output.toString());

            //Hexadecimal machine code
            System.out.println("[Hexadecimal machine code]");
            StringBuilder hexadecimal_output = new StringBuilder();
            for (char c : content_output.toCharArray()) {
                hexadecimal_output.append("D0 ");
                String hex = String.format("%04X", (int) c);
                hexadecimal_output.append(hex.substring(0, 2) + " " + hex.substring(2, 4) + " ");
                hexadecimal_output.append("F1 FC 16 ");
            }
            hexadecimal_output.append("00");
            System.out.println(hexadecimal_output.toString());
        }

        //Second input
        command = scanner.nextLine();
        String regex_plus = "result\\s*=\\s*(\\d+)\\s*\\+\\s*(\\d+);";
        Pattern pattern_plus = Pattern.compile(regex_plus);
        Matcher matcher_plus = pattern_plus.matcher(command);
        if (matcher_plus.find()) {
            int num_plus_first = Integer.parseInt(matcher_plus.group(1));
            //System.out.println("[Debug] num_plus_first is: " + num_plus_first);
            int num_plus_second = Integer.parseInt(matcher_plus.group(2));
            //System.out.println("[Debug] num_plus_second is: " + num_plus_second);

            //Assembly code
            System.out.println("[Assembly code]");
            StringBuilder assemblyCode_plus = new StringBuilder();
            assemblyCode_plus.append("LDWA " + num_plus_first + ", i\n" +
                    "ADDA " + num_plus_second + ", i\n" +
                    "STWA result, d\n" +
                    "STOP\n" +
                    "result: .WORD 0\n" +
                    ".END\n");
            System.out.print(assemblyCode_plus.toString());

            //Hexadecimal machine code
            System.out.println("[Hexadecimal machine code]");
            StringBuilder hexadecimal_plus = new StringBuilder();
            hexadecimal_plus.append("C0 ");
            String hex = String.format("%04X", num_plus_first);
            hexadecimal_plus.append(hex.substring(0, 2) + " " + hex.substring(2, 4) + " ");
            hexadecimal_plus.append("60 ");
            hex = String.format("%04X", num_plus_second);
            hexadecimal_plus.append(hex.substring(0, 2) + " " + hex.substring(2, 4) + " ");
            hexadecimal_plus.append("E1 00 0A 00 00 00");
            System.out.println(hexadecimal_plus.toString());
        }
    }
}
