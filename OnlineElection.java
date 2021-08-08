import java.util.*;

class OnlineElection {

    int[] times;
    HashMap<Integer, Integer> votesMap;
    HashMap<Integer, Integer> topVotersMap;

    // Build hashmap each time who is the leader
    // If same votes shared, then the latest voted person will become the leader
    // If the time in the middle, not the interval. We can use binary search to find
    // the place in between.
    
    // TC: O(log N) where N is length of the give times array.
    // SC: O(N) Where N is number of persons/times array. As we are using hashmap to
    // create a leader and maximum votes.

    public OnlineElection(int[] persons, int[] times) {
        this.times = times;
        votesMap = new HashMap();
        topVotersMap = new HashMap();
        int n = persons.length;

        int currentLeader = persons[0];
        for (int i = 0; i < n; i++) {
            // Top Voters
            topVotersMap.put(persons[i], topVotersMap.getOrDefault(persons[i], 0) + 1);
            if (topVotersMap.get(persons[i]) >= topVotersMap.get(currentLeader)) {
                currentLeader = persons[i];
            }
            votesMap.put(times[i], currentLeader);
        }

    }

    public int q(int t) {
        if (votesMap.containsKey(t)) {
            return votesMap.get(t);
        } else {
            int approximateTime = getVoterId(t);
            int time = times[approximateTime];
            return votesMap.get(time);
        }
    }

    private int getVoterId(int time) {
        int low = 0;
        int high = times.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (times[mid] < time) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return high;
    }
}