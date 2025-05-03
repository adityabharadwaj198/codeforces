package coinbase;
import java.util.*;

public class coinbaseIterator {
    public interface iterator {
        
        public boolean hasNext();
        public int next();
    } 

    public class iteratorIterator implements iterator {
        List<iterator> input;
        Integer listPointer; 

        public iteratorIterator(List<iterator> input) {
            this.input = input;
            this.listPointer = 0;
        }

        public boolean hasNext() {
            int count = 0;
            while (count < input.size()) {
                if (input.get(this.listPointer).hasNext()) { 
                    return true;
                }
                this.listPointer = (this.listPointer + 1) % this.input.size();
                count++;
            }
            return false;
        }

        public int next() {
            iterator iteratorToBeIteratedOn = input.get(listPointer); 
            int returnValue = iteratorToBeIteratedOn.next();
            this.listPointer = (this.listPointer + 1) % this.input.size();
            return returnValue;
        }
    }
    public class AlternateIterator implements iterator {
        List<List<Integer>> input;
        List<Integer> indexPointer; 
        Integer listPointer; 
        public AlternateIterator(List<List<Integer>> input) {
            this.input = input;
            this.indexPointer = new ArrayList<>();
            for (int i=0; i<this.input.size(); i++) {
                this.indexPointer.add(0);
            }
            this.listPointer = 0;
        }

        public boolean hasNext() {
            int count = 0; // this will keep a track of how many times the while loop has executed. 
            // if the while loop has executed input.size() times then it means that you have exhausted all the lists inside
            // your input and now it's time to send false.

            /*
             * while (count < input.size()) {
             * 1. get the list or iterator using listpointer
             * 2. check if hasNext is true for this 
             * 3. incrmeent listpointer in cyclic manner
             * 4. increment count.
                }
             */

            while (count < input.size()) {
                List<Integer> listToBeIteratedOn = input.get(listPointer);
                int index = indexPointer.get(listPointer);
                if (index < listToBeIteratedOn.size()) {
                    return true;
                }
                listPointer = (listPointer + 1) % input.size();
                count++;
            }
            return false;
        }

        public int next() {
            List<Integer> listToBeIteratedOn = this.input.get(this.listPointer); // listPointer = 0,  listToBeIteratedOn = [0,1,2]
            Integer index = this.indexPointer.get(this.listPointer); // index = 0
            Integer returnValue = listToBeIteratedOn.get(index); // 0 
            this.indexPointer.set(this.listPointer, this.indexPointer.get(this.listPointer) + 1); // ip = [1, ]
            this.listPointer = (this.listPointer + 1) % this.input.size(); // lp = 1
            return returnValue;
        }
    }

    public class rangeIterator implements iterator { 
        // ask about if you need to handle negative numbers, negative steps
        // handle step = 0.
        // handle when step +ve but end is smaller than start 
        // handle when step is -ve but end is greater than start 
        int start, end, step, cur; 
        boolean isFirstElement;

        public rangeIterator(int start, int end, int step) throws InstantiationException {
            if (step == 0) {
                throw new InstantiationException("Step cannot be zero");
            }
            if (step > 0 && end < start) {
                throw new InstantiationException("End cannot be lesser than start if step greater than zero.");
            } 
            if (step < 0 && end > start) {
                throw new InstantiationException("End cannot be greater than start if step lesser than zero.");
            } 
            this.start = start;
            this.end = end;
            this.step = step;
            this.cur = this.start;
            this.isFirstElement = true;
        }

        public boolean hasNext() {
            if (this.step >= 0) {
                if (this.cur + this.step <= this.end) {
                    return true;
                }                    
            } else {
                if (this.cur + this.step >= this.end) {
                    return true;
                }
            }
            return false;
        }

        public int next() {
            int returnValue;
            if (this.isFirstElement) {
                returnValue = this.cur;
                this.isFirstElement = false;
                return returnValue;
            }
            returnValue = this.cur + this.step;
            this.cur = returnValue;    
            return returnValue; 
        }
    }

    public class listIterator implements iterator {
        List<Integer> input;
        int lastPos;

        public listIterator (List<Integer> input) {
            this.input = input;
            this.lastPos = 0;
        }

        public boolean hasNext() {
            if (this.lastPos < this.input.size()) {
                return true;
            }
            return false;
        }
        public int next() {
            int returnValue = this.input.get(lastPos);
            lastPos++;
            return returnValue;
        }
    }

    public static void main (String[] args) {
        coinbaseIterator coinbaseIterator = new coinbaseIterator();
        AlternateIterator alternateIterator = coinbaseIterator.new AlternateIterator(List.of(List.of(0, 1, 2), List.of(), List.of(3, 4), List.of(5)));
        // while (alternateIterator.hasNext()) {
        //     System.out.println(alternateIterator.next());
        // }

        rangeIterator rI = null;
        try {
            rI = coinbaseIterator.new rangeIterator(1, 10, 2);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (rI.hasNext()) {
            System.out.println(rI.next());
        }
        rangeIterator r2 = null;
        try {
            r2 = coinbaseIterator.new rangeIterator(0, -10, -2);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // while (r2.hasNext()) {
        //     System.out.println(r2.next());
        // }

        listIterator li = coinbaseIterator.new listIterator(List.of(0, 1, 2, 3, 4, 5));
        // while (li.hasNext()) {
        //     System.out.println(li.next());
        // }

        iteratorIterator ii = coinbaseIterator.new iteratorIterator(List.of(rI, r2, li));
        while (ii.hasNext()) {
            System.out.println(ii.next());
        }
    }
}
