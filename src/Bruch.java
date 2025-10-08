/**
 * {@summary Unveränderliche Repräsentation eines gekürzten Bruchs (Zähler/Nenner) mit Grundrechenarten und Parsing.}
 * Stellt sicher, dass der Nenner nie 0 ist, normalisiert das Vorzeichen in den Zähler
 * und kürzt automatisch mit dem größten gemeinsamen Teiler.
 */
public final class Bruch {
    public int zaehler;
    public int nenner;

    /**
     * {@summary Erzeugt einen gekürzten Bruch und normalisiert Vorzeichen.}
     * Kürzt automatisch mit gcd(|z|, |n|). Bei negativem Nenner wird das Vorzeichen in den Zähler gezogen.
     *
     * @param zaehler Zähler
     * @param nenner  Nenner, darf nicht 0 sein
     * @throws IllegalArgumentException wenn {@code nenner == 0}
     */
    public Bruch(int zaehler, int nenner) {
        if (nenner == 0) {
            throw new IllegalArgumentException("Nenner darf nicht 0 sein.");
        }
        if (nenner < 0) {
            zaehler = -zaehler;
            nenner = -nenner;
        }
        int g = gcd(Math.abs(zaehler), Math.abs(nenner));
        this.zaehler = zaehler / g;
        this.nenner = nenner / g;
    }

    /**
     * {@summary Erzeugt einen ganzzahligen Bruch z/1.}
     *
     * @param z ganzzahliger Wert
     * @return Bruch {@code z/1}
     */
    public static Bruch of(int z) {
        return new Bruch(z, 1);
    }

    /**
     * {@summary Parst einen String zu einem Bruch.}
     * Erlaubte Formate: {@code "z"} oder {@code "z/n"} mit optionalen Leerzeichen.
     *
     * @param s Eingabe-String
     * @return geparster und gekürzter Bruch
     * @throws IllegalArgumentException bei {@code null}, leerem String oder falschem Format
     * @throws NumberFormatException bei ungültigen Ganzzahlen in Zähler/Nenner
     */
    public static Bruch parse(String s) {
        if (s == null) throw new IllegalArgumentException("Eingabe fehlt");
        s = s.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Eingabe fehlt");
        if (s.contains("/")) {
            String[] parts = s.split("/");
            if (parts.length != 2) throw new IllegalArgumentException("Format: z/n");
            int z = Integer.parseInt(parts[0].trim());
            int n = Integer.parseInt(parts[1].trim());
            return new Bruch(z, n);
        }
        int z = Integer.parseInt(s);
        return new Bruch(z, 1);
    }

    /**
     * {@summary Addiert zwei Brüche und liefert das gekürzte Ergebnis.}
     *
     * @param b Summand
     * @return {@code this + b}
     */
    public Bruch add(Bruch b) {
        int z = this.zaehler * b.nenner + b.zaehler * this.nenner;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    /**
     * {@summary Subtrahiert einen Bruch und liefert das gekürzte Ergebnis.}
     *
     * @param b Subtrahend
     * @return {@code this - b}
     */
    public Bruch sub(Bruch b) {
        int z = this.zaehler * b.nenner - b.zaehler * this.nenner;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    /**
     * {@summary Multipliziert zwei Brüche und liefert das gekürzte Ergebnis.}
     *
     * @param b Faktor
     * @return {@code this * b}
     */
    public Bruch mul(Bruch b) {
        int z = this.zaehler * b.zaehler;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    /**
     * {@summary Dividiert durch einen Bruch und liefert das gekürzte Ergebnis.}
     *
     * @param b Divisor
     * @return {@code this / b}
     * @throws ArithmeticException wenn {@code b.zaehler == 0} (Division durch 0)
     */
    public Bruch div(Bruch b) {
        if (b.zaehler == 0) throw new ArithmeticException("Division durch 0");
        int z = this.zaehler * b.nenner;
        int n = this.nenner * b.zaehler;
        return new Bruch(z, n);
    }

    /**
     * {@summary Betrag des Bruchs.}
     * Macht den Zähler positiv, Nenner bleibt positiv.
     *
     * @return {@code |this|}
     */
    public Bruch abs() {
        return new Bruch(Math.abs(zaehler), nenner);
    }

    /**
     * {@summary Größter gemeinsamer Teiler mittels euklidischem Algorithmus.}
     * Gibt mindestens 1 zurück.
     *
     * @param a erste Zahl
     * @param b zweite Zahl
     * @return {@code gcd(|a|, |b|)} oder 1, wenn beide 0
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.max(1, a);
    }

    /**
     * {@summary Kompakte String-Darstellung.}
     * Gibt bei Nenner 1 nur den Zähler zurück, sonst {@code z/n}.
     *
     * @return String-Repräsentation
     */
    @Override
    public String toString() {
        if (nenner == 1) return Integer.toString(zaehler);
        return zaehler + "/" + nenner;
    }

    /**
     * {@summary Gemischte Schreibweise: ganze Anteile plus Restbruch.}
     * Beispiele: {@code 7/3 -> "2 1/3"}, {@code -7/3 -> "-2 1/3"}, {@code 4/2 -> "2"}.
     *
     * @return Darstellung als gemischte Zahl
     */
    public String toMixedString() {
        int z = zaehler;
        int n = nenner;
        int whole = z / n;
        int rest = Math.abs(z % n);
        if (rest == 0) return Integer.toString(whole);
        if (whole == 0) return (z < 0 ? "-" : "") + rest + "/" + n;
        return whole + " " + rest + "/" + n;
    }
}