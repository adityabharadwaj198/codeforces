package rippling;

public class medianOfTwoSortedArrays {
    class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1.length > nums2.length) {
                return findMedianSortedArrays(nums2, nums1);
            }
            if (nums1.length == 0) {
                if (nums2.length == 1)
                    return nums2[0];
                if (nums2.length%2 == 0) {
                    return (double) (nums2[nums2.length/2] + nums2[nums2.length/2-1])/2;
                } else {
                    return (nums2[nums2.length/2]);
                }
            }
            int k = (nums1.length + nums2.length)/2 + 1;
            if ((nums1.length + nums2.length)%2 == 0) {
                k = (nums1.length + nums2.length)/2;
            }
            int low = 0, high = nums1.length;
            int cut1 = 0, cut2 = 0, l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE, r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;
            while (low <= high) {
                cut1 = low + (high-low)/2;
                cut2 = k - cut1;
                l1 = (cut1 <= 0)?Integer.MIN_VALUE:nums1[cut1-1];
                l2 = (cut2 <= 0)?Integer.MIN_VALUE:nums2[cut2-1];
                r1 = (cut1 >= nums1.length)?Integer.MAX_VALUE:nums1[cut1];
                r2 = (cut2 >= nums2.length)?Integer.MAX_VALUE:nums2[cut2];
    
                if (l1 <= r2 && l2 <= r1) {
                    if ((nums1.length + nums2.length)%2 == 0) {
                        return (double)(Math.max(l1,l2) + Math.min(r1, r2))/2;
                    } else {
                        return Math.max(l1, l2);
                    }
                } else if (l1 > r2) {
                    high = cut1-1;
                } else {
                    low = cut1 + 1;
                }
            }
            return 0;
        }
    }
}
