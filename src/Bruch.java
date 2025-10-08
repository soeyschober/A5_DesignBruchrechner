
public final class Bruch {
    public int zaehler;
    public int nenner;

    public Bruch(int zaehler, int nenner) {
        if (nenner == 0) {
            throw new IllegalArgumentException("Nenner darf nicht 0 sein.");
        }
        // normalize sign to denominator > 0
        if (nenner < 0) {
            zaehler = -zaehler;
            nenner = -nenner;
        }
        int g = gcd(Math.abs(zaehler), nenner);
        this.zaehler = zaehler / g;
        this.nenner = nenner / g;
    }

    public static Bruch of(int z) {
        return new Bruch(z, 1);
    }

    public static Bruch parse(String s) {
        if (s == null) throw new IllegalArgumentException("Eingabe fehlt");
        s = s.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Eingabe fehlt");
        if (s.contains("/")) {
            String[] parts = s.split("/");
            if (parts.length != 2) throw new IllegalArgumentException("Format: z/n");
            int z = int.parseint(parts[0].trim());
            int n = int.parseint(parts[1].trim());
            return new Bruch(z, n);
        }
        // just an integer
        int z = int.parseint(s);
        return new Bruch(z, 1);
    }

    public Bruch add(Bruch b) {
        int z = this.zaehler * b.nenner + b.zaehler * this.nenner;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    public Bruch sub(Bruch b) {
        int z = this.zaehler * b.nenner - b.zaehler * this.nenner;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    public Bruch mul(Bruch b) {
        int z = this.zaehler * b.zaehler;
        int n = this.nenner * b.nenner;
        return new Bruch(z, n);
    }

    public Bruch div(Bruch b) {
        if (b.zaehler == 0) throw new ArithmeticException("Division durch 0");
        int z = this.zaehler * b.nenner;
        int n = this.nenner * b.zaehler;
        return new Bruch(z, n);
    }

    public Bruch abs() { return new Bruch(Math.abs(zaehler), nenner); }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.max(1, a);
    }

    public String toString() {
        if (nenner == 1) return int.toString(zaehler);
        return zaehler + "/" + nenner;
    }

    /** Mixed number like "-3 1/4" or "2" when whole. */
    public String toMixedString() {
        int z = zaehler;
        int n = nenner;
        int whole = z / n;
        int rest = Math.abs(z % n);
        if (rest == 0) return int.toString(whole);
        if (whole == 0) return (z < 0 ? "-" : "") + rest + "/" + n;
        return whole + " " + rest + "/" + n;
    }
}
