import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<LinkedList<Node>>> buildings=new ArrayList<>();
        File solutionTxt=new File("solution.txt");
        solutionTxt.createNewFile();
        FileWriter write = new FileWriter("solution.txt");
        try {
            File wayStarEmployees=new File("WaystarEmployees.txt");
            Scanner read=new Scanner(wayStarEmployees);
            ArrayList<LinkedList<Node>> list= new ArrayList<>();//stores connected components
            int num=0;
            while (read.hasNextLine()) {
                String lineData=read.nextLine();
                if(lineData.contains("==")) {
                    int index1=-1;int index2=-1;
                    String[] friends=lineData.split("==");
                    for(int y=0;y< list.size();y++) {
                        for(int a=0;a<list.get(y).size();a++){
                            if(friends[0].equals(list.get(y).get(a).getData()))
                            {
                                index1=y;
                            }
                            if(friends[1].equals(list.get(y).get(a).getData()))
                            {
                                index2=y;
                            }
                        }

                    }
                    if(index1!=-1&&index2!=-1&&index2!=index1)
                    { union(index1,index2,list);}

                }
                else if(lineData.contains(",")) {
                    String[] dataArray=lineData.split(",");
                    makeSet(dataArray,list);

                }
                else if(lineData.contains("#")){
                    System.out.println("Building: "+ num);
                    write.write("\nBuilding: "+ num);
                    System.out.println("Social Connections: "+ list.size());
                    write.write("\nSocial Connections: "+ list.size());
                    for(int x=0;x< list.size();x++){
                        Node cur=list.get(x).getFirst();
                        String connectedData=cur.getData()+" = {";
                        for(int a=1;a<list.get(x).size();a++)
                        {
                            connectedData=connectedData+cur.getData()+", ";
                            cur=list.get(x).get(a);
                        }
                        connectedData=connectedData+cur.getData()+"}";
                        System.out.println(connectedData);
                        write.write("\n"+connectedData);
                    }
                    ArrayList<String> possibleCaptains=new ArrayList<>();
                    getPossibleCaptains(list,possibleCaptains,0,"");
                    System.out.println("Nr of selections: "+possibleCaptains.size());
                    write.write("\nNr of selections: "+possibleCaptains.size());
                    for (String captainsSelection: possibleCaptains) {
                        System.out.println(captainsSelection);
                        write.write("\n"+captainsSelection);
                    }
                    num++;
                    buildings.add(list);
                    list=new ArrayList<>();}
            }
            read.close();
        }catch (FileNotFoundException e){System.out.println("error"); e.printStackTrace();}
        write.close();
        Scanner scanner=new Scanner(System.in);
        int num=-2;
        while (num!=-1) {
            System.out.println("Enter building number or -1 to exit:");
            num = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter first name:");
            String data1 = scanner.nextLine();
            System.out.println("Enter second name:");
            String data2 = scanner.nextLine();
            Node current1 = null;
            Node current2 = null;
            ArrayList<LinkedList<Node>> list = buildings.get(num);
            for (int y = 0; y < list.size(); y++) {
                for (int a = 0; a < list.get(y).size(); a++) {
                    if (list.get(y).get(a).getData().equals(data1)) {
                        current1 = list.get(y).get(a);
                    }
                    if (list.get(y).get(a).getData().equals(data2)) {
                        current2 = list.get(y).get(a);
                    }
                    if (current1 != null && current2 != null) {
                        break;
                    }
                }
                if (current1 != null && current2 != null) {
                    break;
                }
            }
            if (current1 != null && current2 != null) {
                if (find(current1).equals(find(current2))) {
                    System.out.println("In building " + num + ", " + data1 + " and " + data2 + " will use the same fire escape.");
                } else {
                    System.out.println("In building " + num + ", " + data1 + " and " + data2 + " will Not use the same fire escape.");
                }
            } else {
                System.out.println("enter valid names");
            }
        }
    }
    public static void makeSet(String[] data, ArrayList <LinkedList<Node>>list){
        for(int x=0;x<data.length;x++)
        {
            LinkedList<Node> linkedList=new LinkedList<>();
            Node newNode=new Node(data[x]);
            linkedList.add(newNode);
            newNode.setHead(linkedList.getFirst());
            list.add(linkedList);

        }
    }
    public static Node find(Node x){
        return x.getHead();
    }
    public static void union(int index1,int index2,ArrayList <LinkedList<Node>> list){
        if(list.get(index1).size()<list.get(index2).size())
        {
            for (int x=0;x<list.get(index1).size();x++) {
                Node current=list.get(index1).get(x);
                current.setNext(null);
                current.setHead(list.get(index2).getFirst());
                list.get(index2).getLast().setNext(current);
                list.get(index2).add(current);
            }
            list.remove(list.get(index1));
        }
        else
        {
            for (int x=0;x<list.get(index2).size();x++) {
                Node current=list.get(index2).get(x);
                current.setNext(null);
                current.setHead(list.get(index1).getFirst());
                list.get(index1).getLast().setNext(current);
                list.get(index1).add(current);

            }
            list.remove(list.get(index2));
        }
    }
    public static void getPossibleCaptains(ArrayList <LinkedList<Node>> list, ArrayList<String> result, int index, String current) {
        if (index == list.size()) {
            current="["+current+"]";
            result.add(current.replace(",]","]"));
            return;
        }

        for (int x = 0; x < list.get(index).size(); x++) {
            getPossibleCaptains(list, result, index + 1, current + list.get(index).get(x).getData()+",");
        }
    }
}