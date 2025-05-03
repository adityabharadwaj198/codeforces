package rippling;
import java.util.*;

/*
 * This problem involves designing a system to manage and analyze driver deliveries and their associated earnings.

Part 1
Implement the following methods:

Add_driver(driver_id, usd_hourly_rate): Adds a driver and their hourly rate.
Record_delivery(driver_id, start_time, end_time): Records a completed delivery. This is entered into the system only after the order is completed.
Get_Total_Cost(): Prints the total amount the platform needs to pay the drivers.
For this question, just use a global total_pay variable and update it whenever a record is added.
Also, separately store each driver’s delivery records in the instance. Since the records are added in order of time, this list is naturally sorted.

Part 2
Implement the following methods:

Pay_Up_To(pay_time: int): Pays out completed deliveries up to the given timestamp. This mutates the state (records before this time are marked as paid, and should not be paid again).
Total_Cost_Unpaid(): Accumulates the total cost of unpaid deliveries.
You can give each driver a pointer (the index up to which records have already been paid). Go through the delivery list from left to right and pay those before the given time.
Track how much has already been paid using a global total_paid variable. Then use Get_Total_Cost - total_paid to get the total unpaid cost.

Part 3
Given a timestamp, calculate the number of drivers who were online during the 24 hours before this timestamp.
You only need to check all drivers once, then binary search in their record list to see if the driver was online near that time.
If yes, count that driver.

Followup: Identify the time interval during which the maximum number of deliveries are active. Each delivery is defined by its start_time and end_time.


 */
public class deliverysystem {
    
    public class Driver {
        String driverId;
        int rate;
        public Driver(String driverId, int rate) {
            this.driverId = driverId;
            this.rate = rate;
        }
    }

    public enum deliveryDriverPaymentState {
        paid,
        unpaid,
    }
    public class Delivery {
        String driverId;
        int start;
        int end;
        deliveryDriverPaymentState d;

        public Delivery( String driverId,
        int start,
        int end) {
            if (end <= start) {
                throw new IllegalArgumentException("Start cannot be greater than or equal to end");
            }
            this.driverId = driverId;
            this.start = start;
            this.end = end;
            this.d = deliveryDriverPaymentState.unpaid;
        }
    }

    public Map<String, Driver> idToDriver = new HashMap<>(); // index for storing driver details 
    public Map<String, List<Delivery>> idToDelivery = new HashMap<>();

    void addDriver(String driver, int rate) {
        idToDriver.putIfAbsent(driver, new Driver(driver, rate));
    }

    void recordDelivery(String driver, int start, int end) {
        List<Delivery> deliveries = idToDelivery.getOrDefault(driver, new ArrayList<>());
        deliveries.add(new Delivery(driver, start, end));
        idToDelivery.put(driver, deliveries);
    }

    public int totalCost() {
        int total = 0;
        for (String driver: idToDelivery.keySet()) {
            int rate = idToDriver.get(driver).rate;
            for (Delivery d: idToDelivery.get(driver)) {
                total += (d.end - d.start) * rate;
            }
        }
        System.out.println(total);
        return total;
    }

    public int payUpTo(int timestamp) {
        int totalPayUpto = 0;
        for (String driver: idToDelivery.keySet()) {
            idToDelivery.get(driver).sort((Delivery d1, Delivery d2) -> { // just sorting in case the transactions are processed out of order. 
                return d1.start - d2.start;
            });
            for (Delivery d: idToDelivery.get(driver)) {
                if (d.end <= timestamp && d.d != deliveryDriverPaymentState.paid) {
                    d.d = deliveryDriverPaymentState.paid;
                    totalPayUpto += (d.end - d.start) * idToDriver.get(driver).rate;
                }
            }
        }
        return totalPayUpto;
    }

    public int calculateDriversOnline(int timestamp) {
        int count = 0;
        for (String driverID: idToDelivery.keySet()) {
            if (wasDriverOnline(timestamp, idToDelivery.get(driverID))) {
                count++;
            }
        }
        return count;
    }

    public boolean wasDriverOnline(int timestamp, List<Delivery> deliveries) {
        int upperbound = timestamp;
        int lowerbound = timestamp - 86400;

        int l = 0;
        int r = deliveries.size();

        while (l < r) {
            int mid = (l + r) / 2;

            if (intervalsIntersect(deliveries.get(mid), upperbound, lowerbound)) {
                return true;
            } 
            else if (deliveries.get(mid).end > upperbound) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return false;
    }

    public boolean intervalsIntersect(Delivery delivery, int upperbound, int lowerbound) {
        if (delivery.start >= lowerbound) {
            return delivery.start <= upperbound;
        }
        if (delivery.end >= lowerbound) {
            return delivery.end <= upperbound;
        }
        return false;
    }

    public static void main (String[] args) {
        deliverysystem ds = new deliverysystem();
        
        ds.addDriver("a", 10);
        ds.addDriver("b", 5);

        ds.recordDelivery("a", 1, 5);
        ds.recordDelivery("a", 6, 7);
        ds.recordDelivery("a", 6, 10);
        ds.recordDelivery("b", 3, 4);
        ds.recordDelivery("b", 5, 6);
        ds.recordDelivery("b", 7, 10);

        int totalCost = ds.totalCost();
        int totalpaid = ds.payUpTo(6);
        
        int totalUnpaid = totalCost - totalpaid;
        System.out.println(totalUnpaid);

        System.out.println(ds.calculateDriversOnline(86411));

    }
}
