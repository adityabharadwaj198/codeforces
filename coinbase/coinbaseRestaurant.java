package coinbase;
import java.util.*;
public class coinbaseRestaurant {
    
    public enum food {
        pavBhaji,
        VadaPav,
        hakka_noodles,
        pizza,
    }

    public class Restaurant {
        String restaurantId;
        Double x, y;
        List<food> menu;

        public Restaurant(String id, Double x, Double y, List<food> menu) {
            this.restaurantId = id;
            this.x = x;
            this.y = y;
            this.menu = menu;
        }

        public Double getDistance(Double x, Double y) {
            return Math.sqrt(Math.pow(this.x - x,2) + Math.pow(this.y-y, 2));
        }
    }

    List<Restaurant> allRestaurants;
    HashMap<String, List<Restaurant>> foodToRest;

    public coinbaseRestaurant() {
        allRestaurants = new ArrayList<>();
        foodToRest = new HashMap<>();
    }

    public void addNewRestaurant(String id, Double x, Double y, List<food> menu) {
        Restaurant newRest = new Restaurant(id, x, y, menu);
        allRestaurants.add(newRest);
        for (food f: menu) {
            if (foodToRest.get(f.toString()) == null) {
                foodToRest.put(f.toString(), new ArrayList<>());
            }
            foodToRest.get(f.toString()).add(newRest);
        }
    }


    public List<Restaurant> getRestaurants(food fname, Double x, Double y) {
        List<Restaurant> restaurants = getAllRestaurants(fname, x, y);
        return restaurants;
    }

    private List<Restaurant> getAllRestaurants(food fname, Double x, Double y) {
        List<Restaurant> restaurants = foodToRest.get(fname.toString());
        Collections.sort(restaurants, (Restaurant r1, Restaurant r2) -> {
            Double dist = r1.getDistance(x, y);
            return dist.compareTo(r2.getDistance(x,y));
        });
        return restaurants;
    }

    public List<Restaurant> getRestaurants(food fname, Double x, Double y, Double radius) {
        List<Restaurant> restaurants = getAllRestaurants(fname, x, y);
        for (int i=0; i<restaurants.size(); i++) {
            if (restaurants.get(i).getDistance(x, y) > radius) {
                restaurants.remove(i);
            }
        }
        return restaurants;
    }
    

    public static void main (String[] args) {
        coinbaseRestaurant cRestaurant = new coinbaseRestaurant();
        cRestaurant.addNewRestaurant("1", 1.00, 2.00, List.of(food.VadaPav, food.pavBhaji));
        cRestaurant.addNewRestaurant("2", 3.00, 5.00, List.of(food.pizza, food.pavBhaji));
        cRestaurant.addNewRestaurant("3", 6.00, 5.00, List.of(food.hakka_noodles, food.pavBhaji));
        cRestaurant.addNewRestaurant("4", -6.00, -2.00, List.of(food.pizza, food.hakka_noodles));
        
        // List<Restaurant> results = cRestaurant.getRestaurants(food.hakka_noodles, 0.00, 0.00);
        // for (Restaurant r: results) {
        //     System.out.println(r.restaurantId);
        // }
        
        List<Restaurant> results = cRestaurant.getRestaurants(food.hakka_noodles, 0.00, 0.00, 8.00);
        for (Restaurant r: results) {
            System.out.println(r.restaurantId);
        }
    }
}
