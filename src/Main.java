import java.io.*;
import java.util.*;

public class Main {
    static Set<String> dict = new HashSet<>();
    static Map<String, Integer> freq = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/words"))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.toLowerCase().trim();
                dict.add(word);
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }

        String input = "Al*cew*sbegninnigtoegtver*triedofsitt*ngbyh*rsitsreonhtebnakandofh*vingnothi*gtodoonc*ortw*cesh*hdapee*edintoth*boo*h*rsiste*wasr*adnigbuti*hadnopictu*esorc*nve*sati*nsinitandwhatisth*useofab**kth*ughtAlic*withou*pic*u*esorco*versa*ions";
        System.out.println(restoreText(input.toLowerCase()));
    }

    static String restoreText(String text) {
        int n = text.length();
        List<String>[] dp = new List[n + 1];
        double[] score = new double[n + 1];
        Arrays.fill(score, Double.NEGATIVE_INFINITY);
        score[0] = 0;
        dp[0] = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (score[i] == Double.NEGATIVE_INFINITY) continue;

            for (int len = 1; len <= Math.min(20, n - i); len++) {
                String frag = text.substring(i, i + len);
                String match = findMatch(frag);
                if (match != null) {
                    double s = score[i] + Math.log(freq.getOrDefault(match, 1) + 1) + len;
                    if (s > score[i + len]) {
                        score[i + len] = s;
                        dp[i + len] = new ArrayList<>(dp[i]);
                        dp[i + len].add(match);
                    }
                }
            }

            if (score[i + 1] < score[i] - 2) {
                score[i + 1] = score[i] - 2;
                dp[i + 1] = new ArrayList<>(dp[i]);
                dp[i + 1].add(String.valueOf(text.charAt(i)));
            }
        }
        return String.join(" ", dp[n] == null ? Arrays.asList("???") : dp[n]);
    }

    static String findMatch(String frag) {
        String best = null;
        double bestScore = -1000;

        for (String word : dict) {
            if (word.length() != frag.length()) continue;
            double s = Math.max(scorePattern(frag, word), scoreAnagram(frag, word));
            if (s > bestScore) {
                bestScore = s;
                best = word;
            }
        }
        return bestScore > -50 ? best : null;
    }

    static double scorePattern(String pattern, String word) {
        int match = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*') continue;
            if (pattern.charAt(i) != word.charAt(i)) return -1000;
            match++;
        }
        return match * 10;
    }

    static double scoreAnagram(String frag, String word) {
        int[] fragCount = new int[26], wordCount = new int[26];
        int wildcards = 0;

        for (char c : frag.toCharArray()) {
            if (c == '*') wildcards++;
            else fragCount[c - 'a']++;
        }
        for (char c : word.toCharArray()) wordCount[c - 'a']++;

        int needed = 0, extra = 0;
        for (int i = 0; i < 26; i++) {
            needed += Math.max(0, wordCount[i] - fragCount[i]);
            extra += Math.max(0, fragCount[i] - wordCount[i]);
        }

        return (needed <= wildcards && extra == 0) ? frag.length() * 5 - wildcards : -1000;
    }
}