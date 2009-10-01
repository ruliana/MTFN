package mtfn;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public abstract class Metaphone {

    protected static final String THE_MATCH = "$0";
    protected static final String VOWEL = "[aeiouy]";
    protected static final String NON_VOWEL = "[^aeiouy]";
    private String original;
    private String transformed;
    private StringBuilder result;
    private int currentPosition;
    private boolean hadMatches = false;
    private String currentMatch;

    public Metaphone(String string) {
        this.original = string;
        this.transformed = original;
        this.result = new StringBuilder();
        this.currentPosition = 0;
    }

    private void calculate() {
        if (isBlank()) return;

        allLowerCase();
        removeAccents();
        prepare();
        addSpaceToBorders();
        
        while (isNotFullyProcessed()) {
            keep(" ");
            algorithm();
            ignoreNoMatches();
        }
    }

    protected abstract void prepare();

    protected abstract void algorithm();

    private boolean isFullyProcessed() {
        return currentPosition >= transformed.length();
    }

    private boolean isNotFullyProcessed() {
        return !isFullyProcessed();
    }

    private void ignoreNoMatches() {
        if (!hadMatches) {
            currentPosition += 1;
        }
        hadMatches = false;
    }

    protected void translate(String pattern, String subst) {
        if (isFullyProcessed() || hadMatches()) return;

        if (pattern.matches(".+\\(.*")) {
            lookBehind(pattern.substring(0, pattern.indexOf("(")));
            if (hadMatches()) lookAhead(pattern.substring(pattern.indexOf("(")));
        } else {
            lookAhead(pattern);
        }

        if (!hadMatches()) return;

        subst = THE_MATCH.equals(subst) ? currentMatch() : subst;
        consume(currentMatch());
        result.append(subst.toUpperCase());
    }

    private void consume(String match) {
        currentPosition += match.length();
    }

    protected void ignore(String pattern) {
        translate(pattern, "");
    }

    protected void keep(String... patterns) {
        translate("(" + join("|", patterns) + ")", THE_MATCH);
    }

    private String join(String separator, String ... elements) {
        if (elements == null) return "";
        if (elements.length == 0) return "";
        if (elements.length == 1) return elements[0];
        
        StringBuilder result = new StringBuilder();
        result.append(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            result.append(separator);
            result.append(elements[i]);
        }
        return result.toString();
    }

    private boolean hadMatches() {
        return hadMatches;
    }

    private String currentMatch() {
        return currentMatch;
    }

    private boolean matches(String pattern, String string) {
        Matcher matcher = compile(pattern).matcher(string);
        hadMatches = matcher.find();

        if (hadMatches) {
            currentMatch = matcher.groupCount() > 0 ? matcher.group(1) : matcher.group();
        }

        return hadMatches;
    }

    private void lookAhead(String pattern) {
        matches("^" + pattern, aheadString());
    }

    private void lookBehind(String pattern) {
        matches(pattern + "$", behindString());
    }

    private String aheadString() {
        return transformed.substring(currentPosition);
    }

    private String behindString() {
        return transformed.substring(0, currentPosition);
    }

    private boolean isBlank() {
        return transformed == null || transformed.isEmpty();
    }

    protected void removeAccents() {
        Map<Pattern, String> substs = new HashMap<Pattern, String>();
        substs.put(compile("[âãáàäÂÃÁÀÄ]"), "a");
        substs.put(compile("[éèêëÉÈÊË]"), "e");
        substs.put(compile("[íìîïÍÌÎÏ]"), "i");
        substs.put(compile("[óòôõöÓÒÔÕÖ]"), "o");
        substs.put(compile("[úùûüÚÙÛÜ]"), "u");

        for (Entry<Pattern, String> subst : substs.entrySet()) {
            Pattern accents = subst.getKey();
            String noAccent = subst.getValue();
            transformed = accents.matcher(transformed).replaceAll(noAccent);
        }
    }

    protected void removeMultiples(String... letters) {
        for (String letter : letters) {
            Matcher matcher = compile(letter + letter + "+", CASE_INSENSITIVE).matcher(transformed);
            transformed = matcher.replaceAll(letter);
        }
    }

    private void addSpaceToBorders() {
        transformed = " " + transformed + " ";
    }

    private void allLowerCase() {
        transformed = transformed.toLowerCase();
    }

    @Override
    public String toString() {
        calculate();
        return result.toString().trim();
    }
}
