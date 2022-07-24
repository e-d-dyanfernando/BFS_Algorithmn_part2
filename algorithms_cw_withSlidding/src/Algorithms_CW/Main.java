// IIT ID - 20200473

// UoW ID - w1839054

// Student Name - E. D. Dyan Fernando

package Algorithms_CW;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int  step_count = 0;
    static int paths_in_line = 1;
    static int paths_in_next_line = 0;

    static List<List<String>> grid = new ArrayList<>();
    static List<String> r_list=new ArrayList<String>();
    static List<String> c_list=new ArrayList<String>();
    static List<List<Boolean>> visited= new ArrayList<>();
    static List<List<Integer>> cell_list=new ArrayList<List<Integer>>();
    static List<List<Integer>> dir_list=new ArrayList<List<Integer>>();
    static List<Integer> steps_list=new ArrayList<Integer>();

    static Scanner input = new Scanner(System.in);
    static int sr = 0;
    static int sc = 0;
    static int er = 0;
    static int ec = 0;

    static  int m = 0;
    static  int n = 0;

    public static void input_rows(){

        try {
            File myObj = new File("puzzle_2560.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                grid.add(List.of(data.strip().split("")));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (List<String> row: grid) {
            System.out.println(row);
        }
    }

    public static int[] find_location(String symbol){
        m = grid.size();
        n = grid.get(0).size();

        int[] temp = new int[2];

        for(int i =0; i < m; i++){
            for(int j =0; j < n; j++){
                if(grid.get(i).get(j).equals(symbol)){
                    temp = new int[]{i,j};
                    break;
                }
            }
        }
        return temp;
    }

    public static void find_shortest_path(){
        while(r_list.size() > 0){
            int cr = Integer.parseInt(r_list.get(0));
            int cc = Integer.parseInt(c_list.get(0));

            if(er == cc && ec == cc){
                break;
            }
            paths_in_line = paths_in_line - 1;
            r_list.remove(0);
            c_list.remove(0);

            next_cells(cr,cc);

            if(paths_in_line == 0){
                paths_in_line = paths_in_next_line;
                paths_in_next_line = 0;
                step_count = step_count + 1;
            }
        }
    }

    public static void next_cells(int r, int c){
        int[] rf = new int[]{0,1,0,-1};
        int[] cf = new int[]{-1,0,1,0};

        for (int i =0; i <4; i++){
            if(rf[i] == 0){
                if(cf[i] == 1){
                    for(int step = 1; step < n; step++){
                        if(c+step >= n || grid.get(r).get(c+step).equals("0") || grid.get(r).get(c+step-1).equals("F")){
                            if(!visited.get(r).get(c+step-1)){
                                visited.get(r).set(c+step-1, true);
                                paths_in_next_line = paths_in_next_line + 1;
                                r_list.add(String.valueOf(r));
                                c_list.add(String.valueOf((c+step)-1));
                                cell_list.add(Arrays.asList(r,(c+step-1)));
                                dir_list.add(Arrays.asList(rf[i], cf[i]));
                                steps_list.add((step-1));
                            }
                            break;
                        }
                    }
                }else{
                    for(int step = 1; step < n; step++){
                        if(c-step < 0 || grid.get(r).get(c-step).equals("0") || grid.get(r).get(c-step+1).equals("F")){
                            if(!visited.get(r).get(c-step+1)){
                                visited.get(r).set(c-step+1, true);
                                paths_in_next_line = paths_in_next_line +1;
                                r_list.add(String.valueOf(r));
                                c_list.add(String.valueOf(c-step+1));
                                cell_list.add(Arrays.asList(r,(c-step+1)));
                                dir_list.add(Arrays.asList(rf[i],cf[i]));
                                steps_list.add((step-1));
                            }
                            break;
                        }
                    }
                }
            }else if(rf[i] == 1){
                for(int step = 1; step < m; step++){
                    if(r+step >=m || grid.get(r+step).get(c).equals("0")|| grid.get(r+step-1).get(c).equals("F") ){
                        if(!visited.get(r+step-1).get(c)){
                            visited.get(r+step-1).set(c, true);
                            paths_in_next_line= paths_in_next_line+1;
                            r_list.add(String.valueOf(r+step-1));
                            c_list.add(String.valueOf(c));
                            cell_list.add(Arrays.asList(r+step-1, c));
                            dir_list.add(Arrays.asList(rf[i], cf[i]));
                            steps_list.add(step-1);
                        }
                        break;
                    }
                }
            }
            else{
                for(int step = 1; step < m; step++){

                    if(r-step < 0 || grid.get(r-step).get(c).equals("0") || grid.get(r-step+1).get(c).equals("F")){
                        if(!visited.get(r-step+1).get(c)){
                            visited.get(r-step+1).set(c, true);
                            paths_in_next_line = paths_in_next_line +1;
                            r_list.add(String.valueOf(r-step+1));
                            c_list.add(String.valueOf(c));

                            cell_list.add(Arrays.asList(r-step+1,c));
                            dir_list.add(Arrays.asList(rf[i],cf[i]));
                            steps_list.add(step-1);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        input_rows();

        for(int i =0; i < grid.size(); i++){
            List<Boolean> temp= new ArrayList<>();
            for(int j=0; j < grid.get(0).size(); j++){
                temp.add(false);
            }
            visited.add(temp);
        }

        int[] temp = find_location("S");
        sr = temp[0];
        sc = temp[1];

        temp = find_location("F");
        er = temp[0];
        ec = temp[1];

        System.out.println("\nStarting at : "+"("+sc+","+sr+")");

        System.out.println("Finishing at : "+"("+ec+","+er+")");

        r_list.add(String.valueOf(sr));
        c_list.add(String.valueOf(sc));

        visited.get(sr).set(sc, true);

        find_shortest_path();

        List<String> direction=new ArrayList<String>();
        List<List<Integer>> path=new ArrayList<List<Integer>>();

        while (er != sr || ec != sc){
            //System.out.println(Arrays.asList(er,ec));
            path.add(Arrays.asList(ec+1,er+1));
            int index = cell_list.indexOf(Arrays.asList(er,ec));
            //System.out.println("index :"+index);
            List<Integer> tempList = dir_list.get(index);
            int r_factor = tempList.get(0);
            int c_factor = tempList.get(1);
            int step_factor = steps_list.get(index);

            if(r_factor == 0){
                if (c_factor == 1){
                    direction.add("right");
                }else{
                    direction.add("left");
                }
            }else if(r_factor == 1){
                direction.add("down");
            }else{
                direction.add("Up");
            }

            er = er-r_factor*step_factor;
            ec = ec-c_factor*step_factor;
        }

        int l = direction.size();

        System.out.println(" ");

        int count = 1;

        System.out.println(count+". start at ["+ (sc) + ","+(sr)+"]");

        count++;
        for(int i = (l-1); i > 0; i--){
            System.out.println(count+". move "+direction.get(i)+ " to ["+ (path.get(i).get(0)-1)+","+ (path.get(i).get(1)-1)+"]");
            count++;
        }

        System.out.println(count+". move "+direction.get(0)+ " to ["+ (path.get(0).get(0)-1)+","+ (path.get(0).get(1)-1)+"]");
        count++;
        System.out.println(count+". done");

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        double time = totalTime/ 1000000000.0;
        System.out.println("\nrunning Time : "+time+" Seconds");
    }
}