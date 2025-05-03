package coinbase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class coinbaseNFT {
    
    class Trait {
        String value;
        double weight;
        public Trait(String v, double w) {
            this.value = v;
            this.weight = w;
        }
    }

    

    public class NFT {
        Map<String, String> traitMap;
        public NFT() {
            this.traitMap = new HashMap<>();
        }

        public NFT (NFT n) {
            this.traitMap = new HashMap<>(n.traitMap);
        }
    }

    public List<NFT> generateNFTs(int n, Map<String, List<Trait>> mymap ) {
        List<NFT> nfts = new ArrayList<>();
        for (int i=0; i<n; i++) {
            NFT nft = new NFT();
            for (String t: mymap.keySet()) {
                List<Trait> traits = mymap.get(t);
                int randomIndex = pickRandomWithWeight(traits);
                nft.traitMap.put(t, traits.get(randomIndex).value);
            }
            nfts.add(nft);
        }
        return nfts;
    }
    // trait a = (aa, ab, ac)
    // trat b = (ba, bb, bc)

    public List<NFT> generateNFTsUsingOtherWay(int n, Map<String, List<Trait>> mymap ) {
        List<NFT> nfts = new ArrayList<>();
        List<String> traitNames = new ArrayList<>();
        for (String i: mymap.keySet()) {
            traitNames.add(i);
        }
        NFT temp = new NFT();
        recurse(n, 0, traitNames, mymap, nfts, temp);
        return nfts;
    }

    public void recurse(int n, int index, List<String> traitNames, Map<String, List<Trait>> mymap, List<NFT> nfts, NFT temp) {
        if (index == traitNames.size()) {
            nfts.add(new NFT(temp));
            return;
        }

        String traitname = traitNames.get(index);
        for (Trait t: mymap.get(traitname)) {
            temp.traitMap.put(traitname, t.value);
            recurse(n, index+1, traitNames, mymap, nfts, temp);
        }
    }

    public int pickRandom(List<Trait> traits) {
        Random r = new Random();
        int randomIndex = r.nextInt(traits.size());
        return randomIndex;
    }

    public int pickRandomWithWeight(List<Trait> traits) {
        double totalWeight = 0;
        double cumulative = 0;
        totalWeight += traits.stream().mapToDouble(t -> t.weight).sum();
        
        double r = Math.random() * totalWeight;

        for (int i=0; i<traits.size(); i++) {
            cumulative += traits.get(i).weight;
            if (r <= cumulative) {
                return i;
            }
        }
        return -1;
    }
    public static void main (String[] args) {
        List<Trait> traits = new ArrayList<>();
        coinbaseNFT cNft = new coinbaseNFT();
        Map<String, List<Trait>> mymap = new HashMap();
        mymap.put("traitA", List.of(cNft.new Trait("aa", 0.5), cNft.new Trait("ab", 0.6), cNft.new Trait("ac", 0.1)));
        mymap.put("traitB", List.of(cNft.new Trait("ba", 0.2), cNft.new Trait("bb", 0.3), cNft.new Trait("bc", 0.5)));
        
        List<NFT> nfts = cNft.generateNFTs(5, mymap);
        for (int i=0; i<nfts.size(); i++) {
            System.out.println(nfts.get(i).traitMap.toString());
        }

        // List<NFT> nfts2 = new ArrayList<>();
        // nfts2 =cNft.generateNFTsUsingOtherWay(5, mymap);
        // for (int i=0; i<nfts2.size(); i++) {
        //     System.out.println(nfts2.get(i).traitMap.toString());
        // }
    }
}
