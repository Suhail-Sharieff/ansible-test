import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        String[] subscriptions = {
                "user_1,sub_a,1000",
                "user_1,sub_b,2500",
                "user_2,sub_c,500",
                "user_3,sub_d,2000"
        }, changes = {
                "user_1,sub_a,sub_a_plus,1500,11",
                "user_1,sub_a_plus,sub_a_pro,3000,21",
                "user_2,sub_c,sub_c_premium,1200,16"
        };
        new Main().calculateMonthlyCharges(subscriptions, changes, null, null);
    }

    public String[][] calculateMonthlyCharges(String[] subscriptions, String[] changes, String[] pricing,
            String[] usage) {
        // Write your code here.
        TreeMap<String, TreeMap<String,int[]>> subs = new TreeMap<>();
        //store curr subsriptions
        for (var e : subscriptions) {
            String arr[] = e.split(",");
            String usr = arr[0], sub = arr[1];
            int cost = Integer.parseInt(arr[2]);
            subs.putIfAbsent(usr, new TreeMap<>());
            subs.get(usr).put(sub, new int[]{1,30,cost});
        }
        //calc changes
        for(String e:changes){
            String arr[]=e.split(",");
            String usr=arr[0];
            String fromSub=arr[1],toSub=arr[2];
            int monthyCost=Integer.parseInt(arr[3]);
            int changeDate=Integer.parseInt(arr[4]);

            subs.get(usr).get(fromSub)[1]=changeDate-1;
            subs.get(usr).put(toSub, new int[]{changeDate,30,monthyCost});

        }
        for(String user:subs.keySet()){
            System.out.println("******"+user+"*****");
            TreeMap<String,int[]>hisSub=subs.get(user);
            int total=0;
            for(var e:hisSub.entrySet()){
                int subStart=e.getValue()[0];
                int subEnd=e.getValue()[1];
                int duration=(subEnd-subStart+1);
                int cost=e.getValue()[2];
                // System.out.println(e.getKey()+" "+Arrays.toString(e.getValue()));
                total+=(duration*cost)/30;
            }
            System.err.println(total);
        }
        return null;
    }
}