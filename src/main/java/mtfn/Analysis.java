package mtfn;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class Analysis {

    public static void main(String[] args) {
        
        if (args.length < 1) {
            throw new RuntimeException("Where is the file");
        }
        
        MapOfLists result = new MapOfLists();
        
        BufferedReader reader = null;
        try {
            File file = new File(args[0]);
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                result.put(new MetaphonePtBrFrouxo(word).toString(), word);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("data/analysis.txt"));
            for (Entry<String, List<String>> entry : result) {
                writer.println(format("%s => %s", entry.getKey(), entry.getValue().toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}

class MapOfLists implements Iterable<Entry<String, List<String>>>{
    private SortedMap<String, List<String>> content = new TreeMap<String, List<String>>();
    
    public void put(String key, String value) {
        List<String> list = content.get(key);
        if (list == null) {
            list = new LinkedList<String>();
            content.put(key, list);
        }
        list.add(value);
    }

    @Override
    public Iterator<Entry<String, List<String>>> iterator() {
        TreeSet<Entry<String, List<String>>> set = new TreeSet<Entry<String, List<String>>>(new Comparator<Entry<String, List<String>>>() {
            public int compare(Entry<String, List<String>> o1, Entry<String, List<String>> o2) {
                int bySize = new Integer(o1.getValue().size()).compareTo(o2.getValue().size());
                return bySize != 0 ? bySize : o1.getKey().compareTo(o2.getKey());
            }
        });
        set.addAll(content.entrySet());
        return set.iterator();
    }
}