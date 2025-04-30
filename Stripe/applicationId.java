package Stripe;
import java.util.*;

public class applicationId {
    

        List<String> getApplicationIds (String input) {
        if (!Character.isDigit(input.charAt(0))) {
            return new ArrayList<>();
        }
        List<String> answer = new ArrayList<>();
        int i = 0;

        while(i < input.length()) {
            int number = 0; 
            int starter = i;
            int numberLength = 0;
            while (i<input.length() && Character.isDigit(input.charAt(i))) {
                number = number * 10 + (input.charAt(i) - '0');
                i++;
                numberLength++;
            }
            int appIdStarterIndex = starter + numberLength; 
            int appIdEnderIndex = appIdStarterIndex + number; 
            String appId = input.substring(appIdStarterIndex, appIdEnderIndex);
            i = starter + numberLength + number; 
            answer.add(appId);
        }
        return answer;
    }

    Map<String, String> getApplicationIdsWithTimestamp (String input) {
        if (!Character.isDigit(input.charAt(0))) {
            return new HashMap<>();
        }
        Map<String, String> answer = new HashMap<>();
        int i = 0;

        while(i < input.length()) {
            int number = 0; 
            int starter = i;
            int numberLength = 0;
            while (i<input.length() && Character.isDigit(input.charAt(i))) {
                number = number * 10 + (input.charAt(i) - '0');
                i++;
                numberLength++;
            }
            int appIdStarterIndex = starter + numberLength; 
            int appIdEnderIndex = appIdStarterIndex + number; 
            String appId = input.substring(appIdStarterIndex, appIdEnderIndex);
            int timestampStarterIndex = appIdEnderIndex;
            int timestampEnderIndex = appIdEnderIndex + 10; 
            String timestamp = input.substring(timestampStarterIndex, Math.min(timestampEnderIndex, input.length()));
            i = starter + numberLength + number + 10; 
            answer.put(appId, timestamp);
        }
        return answer;
    }

    HashSet<String> getApplHashSet(List<String> applicationIds) {
        return new HashSet<>(applicationIds);
    }

    List<String> filter (String applicationIdsString) {
        HashSet<String> whitelist = new HashSet<>(Arrays.asList("A134141242"));
        HashSet<String> applHashSet = getApplHashSet(getApplicationIds(applicationIdsString));
        List<String> answer = new ArrayList<>();
        for (String i: whitelist) {
            if (applHashSet.contains(i)) {
                answer.add(i);
            }
        }
        return answer;
    }

    Map<String, String> filterWithTimeStamp(String applicationIdsString) {
        HashSet<String> whitelist = new HashSet<>(Arrays.asList("A134141242"));
        Map<String, String> applHashMap = getApplicationIdsWithTimestamp(applicationIdsString);
        Map<String, String> answer = new HashMap<>();
        for (String i: whitelist) {
            answer.put(i, applHashMap.get(i));
        }
        return answer;
    }

    

    

    public static void main(String[] args) {
        applicationId aid = new applicationId();
        System.out.println(aid.getApplicationIds("10A13414124218B124564356434567430"));
        System.out.println(aid.filter("10A13414124218B124564356434567430"));
        System.out.println(aid.getApplicationIdsWithTimestamp("10A134141242167845320018B1245643564345674316784532000"));        
        System.out.println(aid.getApplicationIdsWithTimestamp("10A134141242167845320018B124564356434567431678453200010A13414124216789999990"));        
    }
}
