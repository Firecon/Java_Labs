import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Function;

public class TxtParser<T> {
    private final String delim;
    private final String breakers;
    private final Function<ArrayList<String>, T> converter;

    public TxtParser(String delim, String breakers, Function<ArrayList<String>, T> converter) {
        this.delim = delim == null ? "" : delim;
        this.breakers = breakers == null ? "" : breakers;
        this.converter = converter == null ? a -> null : converter;
    }

    public ArrayList<T> parse(File file) throws FileNotFoundException {
        var result = new ArrayList<T>();
        for (var elem : read(file)) {
            var converted = converter.apply(elem);
            if (converted == null) continue;
            result.add(converted);
        }
        return result;
    }

    public T parse(String str) {
        if (str == null) return null;
        var line = new StringTokenizer(str, delim, false);
        var tokens = new ArrayList<String>();
        while (line.hasMoreTokens()) {
            var token = line.nextToken();
            if (breakers.contains(token)) break;
            tokens.add(token);
        }
        return converter.apply(tokens);
    }

    private ArrayList<ArrayList<String>> read(File file) throws FileNotFoundException {
        var values = new ArrayList<ArrayList<String>>();
        var s = new Scanner(file);
        while (s.hasNextLine()) {
            var line = new StringTokenizer(s.nextLine(), delim, false);
            var tokens = new ArrayList<String>();
            while (line.hasMoreTokens()) {
                var token = line.nextToken();
                if (breakers.contains(token)) break;
                tokens.add(token);
            }
            values.add(tokens);
        }
        return values;
    }
}
